package pl.reports.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PutReportRequest {

    private String query_criteria_character_phrase;
    private String query_criteria_planet_name;

}
