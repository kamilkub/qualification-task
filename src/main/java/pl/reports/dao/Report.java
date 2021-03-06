package pl.reports.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("report_id")
    private Long reportId;

    @JsonProperty("query_criteria_character_phrase")
    private String queryCriteriaCharacterPhrase;

    @JsonProperty("query_criteria_planet_name")
    private String queryCriteriaPlanetName;

    @ManyToMany
    @JsonProperty("result")
    Set<ReportResult> results;


}
