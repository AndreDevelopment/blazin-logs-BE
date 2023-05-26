package com.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a Season
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * A Season is a 2-month period in game
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Season {

    private String id;
    private String startTime,endTime;


}
