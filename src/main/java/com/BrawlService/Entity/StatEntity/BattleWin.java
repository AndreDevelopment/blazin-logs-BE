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
@Document(collection = "ClubBattleWinV2")
public class BattleWin {
    @Id
    private String _id;

    private String battleTime;
    private Player player;
    Map<String, ArrayList<Long>> wins;

    private double winRate;

    public BattleWin(){
    wins = new HashMap<>();

    for(String mode: GameModes.gameModes){
        wins.put(mode, ( ArrayList<Long>)List.of( 0L,0L));
    }
    }

    public  BattleWin(Player player, HashMap<String,ArrayList<Long>> wins,String battleTime) {
        this.player = player;
        this.wins = wins;
        this.battleTime = battleTime;

    }

    public void addWin(String mode, ArrayList<Long> results){
        wins.put(mode,results);
    }
}
