package com.BrawlService.Business;


import com.BrawlService.Entity.BrawlEntity.Brawler;
import com.BrawlService.Entity.BrawlEntity.Log;
import com.BrawlService.Entity.BrawlEntity.Player;
import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Service.BrawlRequest;


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FilterProgram {



    public void getMostUsedBrawler(String clubTag){

        long startTime = System.currentTimeMillis();
        BrawlRequest req = new BrawlRequest();
        ArrayList<String> brawlerNames = new ArrayList<>();
        HashMap<String,ArrayList<Log>> playerLogs = new HashMap<>();
        ArrayList<Player> clubList = req.getClubMembers(clubTag);

        //Fetching names of all brawlers
        req.getBrawlerList().forEach(brawler -> brawlerNames.add(brawler.getName()));


        //Getting the battleLog of every club member
        getClubBattleLog(playerLogs,clubList,req);

        HashMap<String, Integer> brawlerPickRate =new HashMap<>();
        //Initialize each brawler count to 0
        brawlerNames.forEach(name->brawlerPickRate.put(name,0));
        filterBrawlerCount(playerLogs,clubList,brawlerPickRate);


        brawlerPickRate.forEach((key,object)->
            System.out.println(Colour.ANSI_BLUE + "Name: "+key+Colour.ANSI_RESET+" | "+Colour.ANSI_GREEN +"Count:"+object)
        );
        System.out.println(Colour.ANSI_RESET+"Total battles is: "+clubList.size()*25+Colour.ANSI_RESET);

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken " +((endTime-startTime)/1000)+"s");
    }



    private void getClubBattleLog(HashMap<String, ArrayList<Log>> clubBattleLogs, ArrayList<Player> clubList, BrawlRequest req){

        ExecutorService executor = Executors.newFixedThreadPool(15);
        clubList.forEach(player->
             executor.submit(new Thread(() -> clubBattleLogs.put(player.getName(),req.getBattleLog(player.getTag()))))
            );

        shutdownAndAwaitTermination(executor);

    }

    public  ArrayList<BattleWin> getClubVictories(String clubTag)  {


        BrawlRequest req = new BrawlRequest();
        HashMap<String,ArrayList<Log>> playerLogs = new HashMap<>();
        ArrayList<Player> clubList = req.getClubMembers(clubTag);

        getClubBattleLog(playerLogs,clubList,req);


        ArrayList<BattleWin> winners = filterWins(playerLogs,clubList);
        printVictoryResults(winners);
        //FILTERING WINS
       return winners;


    }

    private void filterBrawlerCount(HashMap<String,ArrayList<Log>> playerLogs,ArrayList<Player> clubList, HashMap<String, Integer> brawlerPickRate){

        playerLogs.forEach((key,list)->{

            Player p =  clubList.stream().filter(player -> player.getName().equals(key)).findFirst().orElse(new Player("no player"));
            Brawler b;

            for (Log l : list) {

                //3v3 Battles : Identifying which brawler the intended club member played
                if (!l.getBattle().getTeams().isEmpty()){
                    /*
                    * 1. Find which team contains intended player
                    * 2. Pull out the Player object from the team containing the Player (p)
                    * 3. Set b equal to the return value of which Brawler the player opted for
                    * */
                    b =  l.getBattle().getTeams()
                            .stream().filter(team->team.contains(p)).findFirst().orElse(new ArrayList<>())
                            .stream().filter(player -> player.equals(p)).findFirst().orElse(new Player("no player"))
                            .getBrawler();

                }
                //soloShowdown or bossFight : Identifying which brawler the intended club member played
                else if (!l.getBattle().getPlayers().isEmpty()&& !l.getBattle().getMode().equals("duels")){
                    /*
                    * 1. Pull out the Player p that belongs to the clubMemberList
                    * 2. Identify their Brawler selection and set b.
                    * */
                    b = l.getBattle().getPlayers().stream()
                            .filter(player -> player.equals(p)).findFirst().orElse(new Player("no player"))
                            .getBrawler();

                }
                //Duels : Identifying which brawler the intended club member played
                else {
                    /*
                     * 1. Pull out the Player p that belongs to the clubMemberList
                     * 2. Since duels contains a selection of 3 brawlers, I will increase the count of each one
                     * */
                    l.getBattle().getPlayers()
                            .stream().filter(player -> player.equals(p)).findFirst().orElse(new Player("no player"))
                            .getBrawlers()
                            .forEach(brawler -> brawlerPickRate.put(brawler.getName(), brawlerPickRate.get(brawler.getName()) + 1));
                    b=null;
                }

                if(b!=null)
                    brawlerPickRate.put(b.getName(),brawlerPickRate.get(b.getName())+1);

            }//Log

        });
    }
    private ArrayList<BattleWin> filterWins(HashMap<String,ArrayList<Log>> playerLogs, ArrayList<Player> clubList){
       ArrayList<BattleWin>playerVictories = new ArrayList<>();

        for (Player p: clubList) {
            long wins3v3 = playerLogs.get(p.getName()).stream().filter(log -> log.getBattle().getResult() != null
                    && log.getBattle().getResult().equals("victory")
                    && !log.getBattle().getMode().equals("duels")).count();
            long winsSolo = playerLogs.get(p.getName()).stream().filter(log -> log.getBattle().getRank() != 0
                    && log.getBattle().getRank() < 5).count();
            long wins1v1 = playerLogs.get(p.getName()).stream().filter(log -> log.getBattle().getResult() != null
                    && log.getBattle().getResult().equals("victory")
                    && log.getBattle().getMode().equals("duels")).count();

            //An ArrayList containing different win stats
            playerVictories.add(new BattleWin(p, new ArrayList<>(List.of(wins3v3, winsSolo, wins1v1))));

        }
        return  playerVictories;
    }

    private  void printVictoryResults(ArrayList<BattleWin> playerVictories){
        //25 is the max amount of battles pulled per Player
        playerVictories.forEach((win)->{
            double winRate = (double) (win.getWins().get(0) + win.getWins().get(1) + win.getWins().get(2)) / 25;
            System.out.println(Colour.ANSI_BLUE + win.getPlayer().getName()
                    + Colour.ANSI_PURPLE + " | 3v3 victories: " + win.getWins().get(0)
                    + Colour.ANSI_GREEN + " | Solo victories: " + win.getWins().get(1)
                    + Colour.ANSI_CYAN + " | Duels: " + win.getWins().get(2)
                    + Colour.ANSI_YELLOW + " | W/R: " + winRate);
        });

        System.out.println(Colour.ANSI_RED + "Total battles played (per player): " +25+Colour.ANSI_RESET);
    }

    private void shutdownAndAwaitTermination(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(15, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
