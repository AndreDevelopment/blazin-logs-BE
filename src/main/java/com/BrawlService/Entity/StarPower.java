package com.helper;

/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a StarPower (Owned by a Brawler)
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * A StarPower is a passive ability that gives a unique perk to the brawl throughout the entire duration of the match.
 * Each Brawler has 2 StarPowers currently
 * */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarPower {
    private String name;
    private long id;

}
