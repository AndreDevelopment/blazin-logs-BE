package com.BrawlService.Controller;



import com.BrawlService.Business.FilterProgram;
import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Service.BattleWinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class BattleWinController {

    @Autowired
    BattleWinService service;

    //Endpoint for updating player wins
    @GetMapping("/update/wins")
    public ResponseEntity<String> updateWins(){
        FilterProgram fp = new FilterProgram();
        service.updateBattleHistory(fp.getClubVictories("#VV9PVYYO",service.getBattleHistory()));
        return new ResponseEntity<>("Success",HttpStatus.ACCEPTED);
    }


    //Endpoint for fetching all player win info
    @GetMapping("/fetch")
    public ResponseEntity<List<BattleWin>>getClubWinHistory(){
        return new ResponseEntity<>(service.getBattleHistory(), HttpStatus.ACCEPTED);
    }

    //Endpoint for finding a specific players win history
    @GetMapping("/find/{playerTag}")
    public BattleWin getPlayerHistory(@PathVariable final String playerTag){
        return  service.findPlayerBattleWin(playerTag);
    }


}
