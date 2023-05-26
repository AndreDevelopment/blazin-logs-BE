package com.BrawlService.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding an Event
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * An Event simply describes the map & it's mode
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    private long id;
    private String map,mode;
}
