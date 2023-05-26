package com.BrawlService.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;

/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a Battle
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * A Battle hosts statistics & details regarding players involved, the setting & outcome of a match
 * */

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Battle {

    private String mode, type, result;
    private Player starPlayer;
    private int duration, trophyChange,rank;
    private Level level;
    private ArrayList<ArrayList<Player>> teams;
    private  ArrayList<Player> players;

    private Player bigBrawler;

    public Battle() {
        teams = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public void addTeam(ArrayList<Player> team) {
        teams.add(team);
    }
}