package com.BrawlService.Entity.BrawlEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a Brawler
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * A Brawler is a playable character in game
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brawler {


    private String name;
    private long id;
    private int power,rank,trophies,highestTrophies,trophyChange;
    private ArrayList<Gadget> gadgets = new ArrayList<>();
    private  ArrayList<StarPower> starPowers = new ArrayList<>();
    private ArrayList<Gear> gears = new ArrayList<>();
    public void addGadget(Gadget g){
        gadgets.add(g);
    }
    public void addStarPower(StarPower s){
        starPowers.add(s);
    }
    public void addGear(Gear g){
        gears.add(g);
    }


}
