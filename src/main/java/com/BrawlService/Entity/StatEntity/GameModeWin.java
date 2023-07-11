package com.BrawlService.Entity.StatEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameModeWin {
    private long totalVictories,totalBattles;
    private  double winRate;

    public void incrementVictories(){
        totalVictories++;
    }
    public void incrementTotalBattles(){
        totalBattles++;
    }

    public void addVictories(long num){
        totalVictories+=num;
    }

    public void addTotalBattle(long num){
        totalBattles+=num;
    }

}
