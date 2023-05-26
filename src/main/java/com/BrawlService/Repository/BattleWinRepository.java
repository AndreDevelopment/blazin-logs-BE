package com.BrawlService.Repository;

import com.BrawlService.Entity.StatEntity.BattleWin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository("battleWinRepository")
public interface BattleWinRepository extends MongoRepository <BattleWin,String>{
}
