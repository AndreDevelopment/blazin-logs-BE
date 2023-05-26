package com.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a single Log/Match
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * Logs give access to the outcome of a match that has been played by nay Player
 *
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Log {

    private String battleTime;
    private Event event;
    private Battle battle;

}
