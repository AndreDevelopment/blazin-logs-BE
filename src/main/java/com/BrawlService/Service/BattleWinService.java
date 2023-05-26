package com.BrawlService.Service;

import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Repository.BattleWinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("battleWinService")
public class BattleWinService {

    @Autowired
    private BattleWinRepository battleWinRepository;
    public void saveBattleHistory(List<BattleWin> battleWins){
        battleWinRepository.saveAll(battleWins);
    }

    public List<BattleWin> getBattleHistory(){
        return battleWinRepository.findAll();
    }

}
