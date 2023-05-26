package com.BrawlService.Repository;

import com.BrawlService.Entity.StatEntity.BattleWin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BattleWinRepository extends MongoRepository <BattleWin,String>{
}
