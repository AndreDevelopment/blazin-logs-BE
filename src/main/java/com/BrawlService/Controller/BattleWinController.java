package com.BrawlService.Controller;


import com.BrawlService.Business.FilterProgram;
import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Service.BattleWinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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
        return service.getBattleHistory().toString();
    }

    @GetMapping("/find/{playerTag}")
    public BattleWin getPlayerHistory(@PathVariable final String playerTag){
        System.out.println(playerTag);
        return  service.findPlayerBattleWin(playerTag);
    }


}
