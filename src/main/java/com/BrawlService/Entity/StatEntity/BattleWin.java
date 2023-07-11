package com.BrawlService.Entity.StatEntity;

import com.BrawlService.Entity.BrawlEntity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*
* Data Structure used to store information regarding players win statistics
* */
@Data
@AllArgsConstructor
@Document(collection = "ClubBattleWin")
public class BattleWin {
    @Id
    private String _id;

    private long totalBattles,totalVictories;

    private String battleTime;
    private Player player;
    Map<String, GameModeWin> wins;

    private double winRate;

    public BattleWin(){
    wins = new HashMap<>();

    for(String mode: GameModes.gameModes){
        wins.put(mode, new GameModeWin());
    }
    }

    public  BattleWin(Player player, HashMap<String,GameModeWin> wins,String battleTime,long totalBattles,long totalVictories) {
        this.player = player;
        this.wins = wins;
        this.battleTime = battleTime;
        this.totalBattles = totalBattles;
        this.totalVictories = totalVictories;

    }

    public void addWin(String mode, GameModeWin results){
        wins.put(mode,results);
    }
}
