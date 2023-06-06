package com.BrawlService.Controller;


import com.BrawlService.Business.Colour;
import com.BrawlService.Business.FilterProgram;
import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Service.BattleWinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class BattleWinController {

    @Autowired
    BattleWinService service;



    @GetMapping("/update/wins")
    public String updateWins(){
        FilterProgram fp = new FilterProgram();
        service.updateBattleHistory(fp.getClubVictories("#VV9PVYYO",service.getBattleHistory()));
        return "update successful";
    }

    @GetMapping("/fetch")
    public String showWins(){

        StringBuilder data = new StringBuilder();

        List<BattleWin> wins = service.getBattleHistory();

        for (BattleWin win: wins){
            double winRate = (double) (win.getWins().get(0) + win.getWins().get(1) + win.getWins().get(2)) / win.getWins().get(4);
            data    .append(win.getPlayer().getName())
                    .append(" | 3v3 victories: ").append(win.getWins().get(0))
                    .append(" | Solo victories: ").append(win.getWins().get(1))
                    .append(" | Duels: ").append(win.getWins().get(2))
                   .append(" | Duos: ").append(win.getWins().get(3))
                    .append(" | W/R: ").append(winRate).append("\n");
        }

        new FilterProgram().printVictoryResults((ArrayList<BattleWin>) wins);

        return data.toString();
    }

    @GetMapping("/find/{playerTag}")
    public BattleWin getPlayerHistory(@PathVariable final String playerTag){
        System.out.println(playerTag);
        return  service.findPlayerBattleWin(playerTag);
    }


}
