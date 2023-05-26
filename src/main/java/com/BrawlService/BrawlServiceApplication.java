package com.BrawlService;

import com.BrawlService.Business.FilterProgram;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
*
* Player tag: #R2Y08YC8
* Club tag: #VV9PVYYO
*TODO: Return List of battlewins (in  FilterPrograms) so I can store them in Mongo
* */
@SpringBootApplication
public class BrawlServiceApplication {

	public static void main(String[] args) {
		FilterProgram fp = new FilterProgram();
		fp.getClubVictories("#VV9PVYYO");
		SpringApplication.run(BrawlServiceApplication.class, args);
	}

}
