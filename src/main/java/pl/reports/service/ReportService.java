package pl.reports.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.reports.dao.Report;
import pl.reports.dao.ReportResult;
import pl.reports.dto.PutRequest;
import pl.reports.dto.third_api.Character;
import pl.reports.dto.third_api.Planet;
import pl.reports.dto.third_api.films.Film;
import pl.reports.dto.third_api.films.FilmsResponse;
import pl.reports.repository.ReportRepository;
import pl.reports.repository.ReportResultRepository;

import java.util.*;

@Service
public class ReportService {


    @Value("${starwars.films.url}")
    private String STAR_WARS_FILMS_URL;

    @Value("${starwars.people.url}")
    private String STAR_WARS_PEOPLE_URL;

    @Value("${starwars.planets.url}")
    private String STAR_WARS_PLANETS_URL;

    private ReportRepository reportRepository;
    private ReportResultRepository reportResultRepository;

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();


    public ReportService(ReportRepository reportRepository, ReportResultRepository reportResultRepository) {
        this.reportRepository = reportRepository;
        this.reportResultRepository = reportResultRepository;
    }

    public void deleteAllReports() {
        reportRepository.deleteAll();
    }

    public void deleteReportById(Long id) {
        if (exists(id))
            reportRepository.deleteById(id);
    }

    private void saveReport(Report report) {
        reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    public boolean exists(Long id) {
        return reportRepository.findById(id).isPresent();
    }


    public void generateRaport(Long id, PutRequest putRequest) {
        Optional<Report> report = reportRepository.findById(id);

        if (report.isPresent()) {
            Report updateReport = report.get();

            queryFilms(putRequest, updateReport.getResults());

            shutDownThreadsAndSave(updateReport);

        } else {
            Report createReport = new Report();
            Set<ReportResult> results = new HashSet<>();

            queryFilms(putRequest, results);
            createReport.setResults(results);

            shutDownThreadsAndSave(createReport);
        }

    }

    private void shutDownThreadsAndSave(Report report) {
        saveReport(report);

    }

    private void queryFilms(PutRequest putRequest, Set<ReportResult> reportResultSet) {
        ResponseEntity<FilmsResponse> films = REST_TEMPLATE.getForEntity(STAR_WARS_FILMS_URL, FilmsResponse.class);


        if (Objects.requireNonNull(films.getBody()).getCount() > 0) {
            films.getBody().getResults().forEach(film ->
                    findCharacterPhraseAndPlanet(film, putRequest, reportResultSet));

        }
    }

    private void findCharacterPhraseAndPlanet(Film film, PutRequest putRequest, Set<ReportResult> reportResultSet) {

        film.getCharacters().forEach(characterUrl -> {
            ResponseEntity<Character> character = REST_TEMPLATE.getForEntity(characterUrl, Character.class);
            String characterName = character.getBody().getName();
            String planetUrl = character.getBody().getHomeworld();

            if (characterName.contains(putRequest.getQueryCriteriaCharacterPhrase())) {
                ReportResult result = new ReportResult();
                result.setCharacterName(characterName);
                result.setFilmName(film.getTitle());
                result.setFilmId(film.getEpisode_id());

                if (findCharacterPlanetAndDoesItMatchCriteria(planetUrl, putRequest, result)) {
                    reportResultRepository.save(result);
                    reportResultSet.add(result);
                }
            }
        });
    }

    private boolean findCharacterPlanetAndDoesItMatchCriteria(String planetUrl, PutRequest putRequest, ReportResult result) {
        ResponseEntity<Planet> planet = REST_TEMPLATE.getForEntity(planetUrl, Planet.class);
        String planetName = planet.getBody().getName();

        if (planetName.equalsIgnoreCase(putRequest.getQueryCriteriaPlanetName())) {
            result.setPlanetName(planetName);
            int lastSlash = planetUrl.lastIndexOf("/");
            result.setPlanetId(planetUrl.substring(lastSlash - 2));
            return true;
        }

        return false;
    }


}
