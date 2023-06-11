package com.BrawlService.Business;


import com.BrawlService.Entity.BrawlEntity.Brawler;
import com.BrawlService.Entity.BrawlEntity.Log;
import com.BrawlService.Entity.BrawlEntity.Player;
import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Service.BrawlRequest;


import java.text.DecimalFormat;
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
        getClubBattleLog(playerLogs,clubList);

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



    /*
     * Params: clubBattleLogs - map of Player name : BattleLog
     *         clubList - List of Players belonging to a club
     * Returns: <nothing> clubBattleLogs will be populated appropriately
     * */
    private void getClubBattleLog(HashMap<String, ArrayList<Log>> clubBattleLogs, ArrayList<Player> clubList){

        ExecutorService executor = Executors.newFixedThreadPool(15);

        clubList.forEach(player->
                executor.submit(() -> {
                    synchronized (this) {
                        clubBattleLogs.put(player.getName(),new BrawlRequest().getBattleLog(player.getTag()) );
                    }
                })
        );

        shutdownAndAwaitTermination(executor);

    }

    /*
     * Params: clubTag - tag of the club
     *         oldWinList - Data from the DB facilitate updating information
     * Returns: List of updated BattleWins
     * */
    public  ArrayList<BattleWin> getClubVictories(String clubTag,List<BattleWin> oldWinList)  {


        BrawlRequest req = new BrawlRequest();
        HashMap<String,ArrayList<Log>> playerLogs = new HashMap<>();
        ArrayList<Player> clubList = req.getClubMembers(clubTag);
        long startTime = System.currentTimeMillis();
        getClubBattleLog(playerLogs,clubList);

        //FILTERING WINS
        long endTime = System.currentTimeMillis();
        System.out.println(Colour.ANSI_RESET+(endTime-startTime)/1000+"s");
       return filterWins(playerLogs,clubList,oldWinList);


    }

    /*
     * Params: playerLogs - map of Player name : BattleLog
     *         clubList - List of Players belonging to a club
     *         oldWinList - Data from the DB facilitate updating information
     * Returns: BattleWin list with new info filtered from recent battle logs
     * */
    private ArrayList<BattleWin> filterWins(HashMap<String,ArrayList<Log>> playerLogs, ArrayList<Player> clubList,List<BattleWin>oldWinList){
       ArrayList<BattleWin>playerVictories = new ArrayList<>();

       //Loop through each players log, count up their wins and store those stats
        for (Player p: clubList) {

            long wins3v3 =0, wins1v1=0,winsSolo=0,totalBattles=0,winsDuo=0;

            //Find the first player that matches the player P, fetch their most recent battle time (Could be null if player has not been previous saved to DB)
            String battleTime = oldWinList.stream()
                    .filter(battleWin -> battleWin.getPlayer().getTag().equals(p.getTag()))
                    .findFirst().orElse(new BattleWin()).getBattleTime();

            for (Log l : playerLogs.get(p.getName())){

                if(l.getBattleTime().equals(battleTime))
                    break;

                if (l.getBattle().getResult()!=null&& l.getBattle().getResult().equals("victory")){
                    if (l.getBattle().getMode().equals("duels")){
                        wins1v1 ++;
                    }else {
                        wins3v3++;
                    }
                }
                if (l.getBattle().getRank() < 5 &&l.getBattle().getMode().equals("soloShowdown")){
                    winsSolo++;
                }
                if (l.getBattle().getRank() < 3 &&l.getBattle().getMode().equals("duoShowdown")){
                    winsDuo++;
                }
                totalBattles++;
            }

            battleTime = playerLogs.get(p.getName()).get(0).getBattleTime();

            //An ArrayList containing different win stats
            playerVictories.add(new BattleWin(p, new ArrayList<>(List.of(wins3v3, winsSolo, wins1v1,winsDuo,totalBattles)),battleTime));

        }
        return  playerVictories;
    }

    /*
     * Params: playerVictories - contains recent win information
     * Returns: Printed information in the console
     * */
    public  void printVictoryResults(ArrayList<BattleWin> playerVictories){
        //25 is the max amount of battles pulled per Player
        DecimalFormat format = new DecimalFormat("#.##");
        playerVictories.forEach((win)->{

            System.out.println(Colour.ANSI_BLUE + win.getPlayer().getName()
                    + Colour.ANSI_PURPLE + " | 3v3 victories: " + win.getWins().get(0)
                    + Colour.ANSI_GREEN + " | Solo victories: " + win.getWins().get(1)
                    + Colour.ANSI_CYAN + " | Duels: " + win.getWins().get(2)
                    + Colour.ANSI_RESET+ " | Duos: " + win.getWins().get(3)
                    + Colour.ANSI_RED + " | Total Battles: " + win.getWins().get(4)
                    + Colour.ANSI_YELLOW + " | W/R: " + format.format(win.getWinRate()));
        });

        //System.out.println(Colour.ANSI_RED + "Total battles played (per player): " +25+Colour.ANSI_RESET);
    }
    /*
     * Params: executorService - contains threads to be run
     * Returns: Shutdown of all dead threads
     * */
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
}
