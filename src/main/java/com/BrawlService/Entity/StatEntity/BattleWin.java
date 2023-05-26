package com.BrawlService.Entity.StatEntity;

import com.BrawlService.Entity.BrawlEntity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.ArrayList;
@Data
@AllArgsConstructor
public class BattleWin {

    private Player player;
    ArrayList<Long> wins;

    public BattleWin(){
        wins = new ArrayList<>();
    }

    public void addWin(long stat){
        wins.add(stat);
    }
}
