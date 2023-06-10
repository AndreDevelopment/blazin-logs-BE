package com.BrawlService.Entity.StatEntity;

import com.BrawlService.Entity.BrawlEntity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;

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

    private String battleTime;
    private Player player;
    ArrayList<Long> wins;

    private double winRate;

    public BattleWin(){
        wins = new ArrayList<>();
    }

    public  BattleWin(Player player, ArrayList<Long> wins,String battleTime) {
        this.player = player;
        this.wins = wins;
        this.battleTime = battleTime;

    }

    public void addWin(long stat){
        wins.add(stat);
    }
}
