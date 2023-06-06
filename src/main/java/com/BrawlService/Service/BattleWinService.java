package com.BrawlService.Service;

import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Repository.BattleWinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
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



        List<BattleWin> modifiedRecords = new ArrayList<>();

        for (BattleWin bw:battleWins) {
            BattleWin oldRecord = findPlayerBattleWin(bw.getPlayer().getTag());

            if(oldRecord==null){
                modifiedRecords.add(bw);
            }else {
                ArrayList<Long> wins = bw.getWins();
                bw.set_id(oldRecord.get_id());
                for (int i = 0; i < wins.size(); i++) {
                    wins.set(i, wins.get(i) + oldRecord.getWins().get(i));
                }
                modifiedRecords.add(bw);
            }
        }

        battleWinRepository.saveAll(modifiedRecords);
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
