package com.BrawlService.Service;

import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Repository.BattleWinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

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

    public void updateBattleHistory(List<BattleWin> battleWins){

    }
    public BattleWin findPlayerBattleWin(String tag){
        return battleWinRepository.findByTag(addHashtag.apply(tag));
    }

    private final Function<String,String> addHashtag = tag -> {
        if (tag.charAt(0)=='#')
            return tag;
        return tag.charAt(0)=='%'? "#"+ tag.substring(3): "#" +tag;
    };



}
