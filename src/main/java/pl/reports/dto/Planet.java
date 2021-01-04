package pl.reports.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Planet {

    private String planetId;
    private String planetName;

    public Planet(String planetId, String planetName) {
        this.planetId = planetId;
        this.planetName = planetName;
    }
}
