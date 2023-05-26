package com.BrawlService.Entity.BrawlEntity;

import com.BrawlService.Entity.BrawlEntity.Event;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a Battle
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * An EventSlot is used for holding information regarding map rotation in Brawl Stars. (NOT particular to any BattleLog)
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventSlot {

    private String startTime,endTime;
    private int slotId;
    private Event event;

}
