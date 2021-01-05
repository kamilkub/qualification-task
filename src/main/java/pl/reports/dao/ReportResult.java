package pl.reports.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "results")
@Getter
@Setter
@NoArgsConstructor
public class ReportResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty("film_id")
    private String filmId;

    @JsonProperty("film_name")
    private String filmName;

    @JsonProperty("character_id")
    private String characterId;

    @JsonProperty("character_name")
    private String characterName;

    @JsonProperty("planet_id")
    private String planetId;

    @JsonProperty("planet_name")
    private String planetName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportResult that = (ReportResult) o;

        if (filmId != null ? !filmId.equals(that.filmId) : that.filmId != null) return false;
        if (filmName != null ? !filmName.equals(that.filmName) : that.filmName != null) return false;
        if (characterId != null ? !characterId.equals(that.characterId) : that.characterId != null) return false;
        if (characterName != null ? !characterName.equals(that.characterName) : that.characterName != null)
            return false;
        if (planetId != null ? !planetId.equals(that.planetId) : that.planetId != null) return false;
        return planetName != null ? planetName.equals(that.planetName) : that.planetName == null;
    }

    @Override
    public int hashCode() {
        int result = filmId != null ? filmId.hashCode() : 0;
        result = 31 * result + (filmName != null ? filmName.hashCode() : 0);
        result = 31 * result + (characterId != null ? characterId.hashCode() : 0);
        result = 31 * result + (characterName != null ? characterName.hashCode() : 0);
        result = 31 * result + (planetId != null ? planetId.hashCode() : 0);
        result = 31 * result + (planetName != null ? planetName.hashCode() : 0);
        return result;
    }
}
