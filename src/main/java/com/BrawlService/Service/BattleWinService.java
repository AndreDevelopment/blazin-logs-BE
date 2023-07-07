package com.BrawlService.Service;

import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Entity.StatEntity.GameModeWin;
import com.BrawlService.Repository.BattleWinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.function.Function;

/*
 * Created by: Andre D'Souza
 * Purpose: Responsible for managing transactions between sever & MongoDB
 * NOTES:
 * */

@Service("battleWinService")
public class BattleWinService {

    @Autowired
    private BattleWinRepository battleWinRepository;

    /*
     * Params: List of BattleWins
     * Returns: <nothing> (Saves the List to MongoDB, updating existing documents)
     * */
    public void saveBattleHistory(List<BattleWin> battleWins){
        battleWinRepository.saveAll(battleWins);
    }

    /*
     * Params: <none>
     * Returns: List of BattleWin documents stored in MongoDB
     * */
    public List<BattleWin> getBattleHistory(){
        return battleWinRepository.findAll();
    }

    /*
     * Params: List of BattleWins
     * Returns: <nothing> (Updates each document, adding in new wins)
     * */
    public void updateBattleHistory(List<BattleWin> battleWins){

        List<BattleWin> modifiedRecords = new ArrayList<>();

        for (BattleWin bw:battleWins) {
            BattleWin oldRecord = findPlayerBattleWin(bw.getPlayer().getTag());

            if(oldRecord!=null){
                Map<String, GameModeWin> wins = bw.getWins();
                bw.set_id(oldRecord.get_id());
                for (String key: wins.keySet()) {
                    //Updating GameMode specific W/R, victories and total battles
                    GameModeWin temp = bw.getWins().get(key);
                    temp.addVictories(oldRecord.getWins().get(key).getTotalVictories());
                    temp.addTotalBattle(oldRecord.getWins().get(key).getTotalBattles());
                    temp.setWinRate((double)temp.getTotalVictories()/temp.getTotalBattles());

                }
            }
            bw.setWinRate((double)bw.getTotalVictories()/bw.getTotalBattles());
            modifiedRecords.add(bw);
        }

        battleWinRepository.saveAll(modifiedRecords);
    }

    /*
     * Params: tag - A player tag
     * Returns: BattleWin associated with that Player
     * */
    public BattleWin findPlayerBattleWin(String tag){
        return battleWinRepository.findByTag(addHashtag.apply(tag));
    }

    /*
     * Params: tag - A player tag
     * Returns: the tag with the '#' added to the front
     * */
    private final Function<String,String> addHashtag = tag -> {
        if (tag.charAt(0)=='#')
            return tag;
        return tag.charAt(0)=='%'? "#"+ tag.substring(3): "#" +tag;
    };
    /*
     * Params: List of win stats
     * Returns: winRate = total victories / total battles played
     * */
    private double calculateWinRate(ArrayList<Long> wins){

        return (double)(wins.get(0)+wins.get(1) + wins.get(2)+ wins.get(3)) / wins.get(4);
    }


}
