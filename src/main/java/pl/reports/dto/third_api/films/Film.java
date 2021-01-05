package pl.reports.dto.third_api.films;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Film {

    public String title;
    public String episode_id;
    public String opening_crawl;
    public String director;
    public String producer;
    public String release_date;
    public List<String> characters;
    public List<String> planets;
    public List<String> starships;
    public List<String> vehicles;
    public List<String> species;
    public Date created;
    public Date edited;
    public String url;
}
