package com.BrawlService.Entity.StatEntity;

import com.BrawlService.Entity.BrawlEntity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
@Data
@AllArgsConstructor
@Document(collection = "ClubBattleWin")
public class BattleWin {
    @Id
    private String _id;

    private Player player;
    ArrayList<Long> wins;

    public BattleWin(){
        wins = new ArrayList<>();
    }

    public  BattleWin(Player player, ArrayList<Long> wins) {
        this.player = player;
        this.wins = wins;
    }

    public void addWin(long stat){
        wins.add(stat);
    }
}
