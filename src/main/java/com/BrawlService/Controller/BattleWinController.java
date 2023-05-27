package com.BrawlService.Controller;


import com.BrawlService.Business.FilterProgram;
import com.BrawlService.Service.BattleWinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BattleWinController {

    @Autowired
    BattleWinService service;

//    @GetMapping("/wins")
//    public void insertWins(){
//        FilterProgram fp = new FilterProgram();
//        service.saveBattleHistory(fp.getClubVictories("#VV9PVYYO"));
//    }

    @GetMapping("/fetch")
    public String showWins(){
        return service.getBattleHistory().toString();
    }


}
