package pl.reports.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.reports.dao.Report;
import pl.reports.dao.Result;
import pl.reports.dto.Planet;
import pl.reports.dto.PutReportRequest;
import pl.reports.repository.ReportRepository;
import pl.reports.repository.ResultRepository;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReportService {

    private ReportRepository reportRepository;
    private ResultRepository resultRepository;

    @Value("${starwars.base.url}")
    private String starWarsAPI;

    public ReportService(ReportRepository reportRepository, ResultRepository resultRepository) {
        this.reportRepository = reportRepository;
        this.resultRepository = resultRepository;
    }

    public void deleteAllReports(){
        reportRepository.deleteAll();
    }

    public void deleteReportById(Long id){
        if(exists(id))
            reportRepository.deleteById(id);
    }

    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }

    public Report getReportById(Long id){
        return reportRepository.getOne(id);
    }

    public boolean exists(Long id){
       return reportRepository.findById(id).isPresent();
    }

    public void createOrUpdateReport(Long id, PutReportRequest reportRequest){
        Optional<Report> reportOptional = reportRepository.findById(id);

        int peopleCount = getPeopleCountFromAPI();
        Result result = Objects.requireNonNull(findDataByCriteria(reportRequest, peopleCount));


        if(reportOptional.isPresent()){
            Report updateReport = reportOptional.get();

            updateReport.setQueryCriteriaCharacterPhrase(reportRequest.getQuery_criteria_character_phrase());
            updateReport.setQueryCriteriaPlanetName(reportRequest.getQuery_criteria_planet_name());

            updateReport.setResult(List.of(result));

            resultRepository.save(result);
            reportRepository.save(updateReport);

        } else {
            Report report = new Report();

            report.setQueryCriteriaCharacterPhrase(reportRequest.getQuery_criteria_character_phrase());
            report.setQueryCriteriaPlanetName(reportRequest.getQuery_criteria_planet_name());

            report.setResult(List.of(result));

            resultRepository.save(result);
            reportRepository.save(report);

        }



    }


    private int getPeopleCountFromAPI()  {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(this.starWarsAPI + "people", String.class);

        try {
            JsonNode node = new ObjectMapper().readTree(response.getBody());
            return node.path("count").asInt();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }

    }

    private Result findDataByCriteria(PutReportRequest putReportRequest, int peopleCount){
        RestTemplate restTemplate = new RestTemplate();
        int count = 1;

        while(count <= peopleCount){
            String url = this.starWarsAPI + "people/" + count;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            try {
                JsonNode node = new ObjectMapper().readTree(response.getBody());
                String characterName = node.path("name").asText();

                if(characterName.equalsIgnoreCase(putReportRequest.getQuery_criteria_character_phrase())){

                    Planet planet = getCharacterHomeWorld(node, restTemplate, putReportRequest);
                    int lastSlash = url.lastIndexOf("/");
                    String characterId = url.substring(lastSlash + 1, lastSlash + 2);


                    Result result = new Result();
                    result.setCharacterId(characterId);
                    result.setCharacterName(characterName);
                    result.setPlanetId(planet.getPlanetId());
                    result.setPlanetName(planet.getPlanetName());

                    return result;

                }


            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            count++;
        }

        return null;
    }

    private Planet getCharacterHomeWorld(JsonNode jsonDataBody, RestTemplate restTemplate, PutReportRequest putReportRequest){
        String homeWorldUrl = jsonDataBody.path("homeworld").asText();

        ResponseEntity<String> response = restTemplate.getForEntity(homeWorldUrl, String.class);

        try {
            JsonNode node = new ObjectMapper().readTree(response.getBody());
            String planetName = node.path("name").asText();

            if(planetName.equalsIgnoreCase(putReportRequest.getQuery_criteria_planet_name())){
                int lastSlash = homeWorldUrl.lastIndexOf("/");
                String planetId = homeWorldUrl.substring(lastSlash - 1, lastSlash);

               return new Planet(planetId,planetName);
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;

    }



}
