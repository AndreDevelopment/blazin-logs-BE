package com.BrawlService.Controller;


import com.BrawlService.Business.FilterProgram;
import com.BrawlService.Entity.BrawlEntity.Player;
import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Service.BattleWinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BattleWinController {

    @Autowired
    BattleWinService service;

//    @GetMapping("/wins")
//    public void insertWins(){
//        FilterProgram fp = new FilterProgram();
//        service.saveBattleHistory(fp.getClubVictories("#VV9PVYYO",service.getBattleHistory()));
//    }

    @GetMapping("/fetch")
    public String showWins(){
        return service.getBattleHistory().toString();
    }

    @GetMapping("/find/{playerTag}")
    public BattleWin getPlayerHistory(@PathVariable final String playerTag){
        System.out.println(playerTag);
        return  service.findPlayerBattleWin(playerTag);
    }


}
