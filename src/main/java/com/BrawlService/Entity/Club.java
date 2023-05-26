package com.BrawlService.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;

/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a Club
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * A Club is a grouping of players into an organized alliance. Simply put they are a clan.
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Club {

    private String name,tag,description,type;
    private long badgeId,requiredTrophies,trophies;
    private int rank,memberCount;
    private ArrayList<Player> members = new ArrayList<>();


    //TOP PLAYER -> CLUB
    public Club(String name) {
        this.name = name;
    }
    public void addMember(Player p){
        members.add(p);
    }

}
