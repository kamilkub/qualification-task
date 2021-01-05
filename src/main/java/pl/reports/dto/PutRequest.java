package pl.reports.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PutRequest {
    @JsonProperty("query_criteria_character_phrase")
    private String queryCriteriaCharacterPhrase;

    @JsonProperty("query_criteria_planet_name")
    private String queryCriteriaPlanetName;

}
