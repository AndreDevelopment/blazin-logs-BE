package com.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a Gear (Owned by a Brawler)
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 *
 * A Gear is a modification that enables a small enchantment to a Brawlers capabilities. (Ex. faster health recovery, increased damage
 * or faster walking speed)
 *
 * Gears are activated passively throughout the match due to situational circumstances. (Ex. health < 50% = increase damage,
 * Hidden in bushes -> increase speed)
 *
 * Gears also have rarity but this will not be demonstrated by the JSON return value
 * Each brawler has at LEAST 5 gears and can have up to N gears
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gear {

    private String name;
    private long id;

}
