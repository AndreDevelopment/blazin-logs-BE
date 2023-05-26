package com.BrawlService.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a Gadget (Owned by a Brawler)
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * Gadgets are abilities that can be activated by a Brawler up to three times during a single match
 * Each Brawler currently has 2 unique gadgets in their set
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gadget {

    private String name;
    private long id;

}
