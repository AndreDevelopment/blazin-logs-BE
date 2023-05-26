package com.BrawlService.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.util.ArrayList;


/*
 * Created by: Andre D'Souza
 * Purpose: Template to store information regarding a Player
 * NOTES: Some fields may be unused due to return value of the JSON file. Their values will be null or 0 in this case.
 * A Player refers to the person/people playing the game i.e. us the collective users
 *
 * */
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    private String name,tag,nameColor,role;
    private int trophies,highestTrophies,expPoints,rank;


    private Club club;
    private Brawler brawler;

    private ArrayList<Brawler> brawlers = new ArrayList<>();

    public Player(String name){
        this.name = name;
    }

    public Player(){}


    public Brawler findABrawler(String name){

        for (Brawler b: brawlers) {
            if(b.getName().equalsIgnoreCase(name)){
                return b;
            }
        }
        return null;
    }

    public void addBrawler(Brawler b) {
        brawlers.add(b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return tag.equals(player.tag);
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

}
