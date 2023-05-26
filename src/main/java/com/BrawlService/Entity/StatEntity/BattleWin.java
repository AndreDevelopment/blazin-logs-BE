package com.BrawlService.Entity.StatEntity;

import com.BrawlService.Entity.BrawlEntity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.ArrayList;
@Data
@AllArgsConstructor
public class BattleWin {

    private Player p;
    ArrayList<Integer> wins;

    public BattleWin(){
        wins = new ArrayList<>();
    }

    public void addWin(int stat){
        wins.add(stat);
    }
}
