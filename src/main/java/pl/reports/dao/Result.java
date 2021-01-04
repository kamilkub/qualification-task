package pl.reports.dao;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "result")
@Setter
@Getter
@NoArgsConstructor
public class Result {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filmId;
    private String filmName;
    private String characterId;
    private String characterName;
    private String planetId;
    private String planetName;


}


