package com.BrawlService.Controller;

import com.BrawlService.Repository.BattleWinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BattleWinController {

    @Autowired
    BattleWinRepository battleWinRepository;
}
