package com.BrawlService.Business;



import com.BrawlService.Entity.BrawlEntity.Log;
import com.BrawlService.Entity.BrawlEntity.Player;
import com.BrawlService.Entity.StatEntity.BattleWin;
import com.BrawlService.Entity.StatEntity.GameModes;
import com.BrawlService.Service.BrawlRequest;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FilterProgram {






    /*
     * Params: clubBattleLogs - map of Player name : BattleLog
     *         clubList - List of Players belonging to a club
     * Returns: <nothing> clubBattleLogs will be populated appropriately
     * */
    private void getClubBattleLog(ConcurrentHashMap<String, ArrayList<Log>> clubBattleLogs, ArrayList<Player> clubList){

        ExecutorService executor = Executors.newFixedThreadPool(15);

        clubList.forEach(player->
                executor.submit(() -> {
                    clubBattleLogs.put(player.getName(),new BrawlRequest().getBattleLog(player.getTag()) );
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

        ConcurrentHashMap<String,ArrayList<Log>> playerLogs = new ConcurrentHashMap<>();
        ArrayList<Player> clubList = new BrawlRequest().getClubMembers(clubTag);
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
    private ArrayList<BattleWin> filterWins(ConcurrentHashMap<String,ArrayList<Log>> playerLogs, ArrayList<Player> clubList,List<BattleWin>oldWinList){
       ArrayList<BattleWin>playerVictories = new ArrayList<>();

       //Loop through each players log, count up their wins and store those stats
        for (Player p: clubList) {

            BattleWin temp = new BattleWin();
            long totalBattles =0 , totalVictories =0;
            //Find the first player that matches the player P, fetch their most recent battle time (Could be null if player has not been previous saved to DB)
            String battleTime = oldWinList.stream()
                    .filter(battleWin -> battleWin.getPlayer().getTag().equals(p.getTag()))
                    .findFirst().orElse(new BattleWin()).getBattleTime();

            for (Log l : playerLogs.get(p.getName())){

                if(l.getBattleTime().equals(battleTime))
                    break;
                System.out.println("Mode: " + l.getBattle().getMode() + " Player: "+p.getName());
                //If condition is plainly looking for victories whether it be 3v3 or solo, duo etc.
                if (l.getBattle().getResult()!=null&& l.getBattle().getResult().equals("victory") ||
                        l.getBattle().getRank() < 5 &&  !l.getBattle().getMode().equals("duoShowdown") ||
                        l.getBattle().getRank() < 3 &&l.getBattle().getMode().equals("duoShowdown")
                ){

                    //The gameMode map looks like [ mode : gameModeWin ]. Over here I update the wins
                    if (!GameModes.ignoreModes.contains(l.getBattle().getMode())) {
                        temp.getWins().get(l.getBattle().getMode()).incrementVictories();
                        totalVictories++;
                    }
                }
                //Here I am updated the total count

                if (!GameModes.ignoreModes.contains(l.getBattle().getMode())) {
                    temp.getWins().get(l.getBattle().getMode()).incrementTotalBattles();
                    totalBattles++;
                }
            }// end of for


            temp.setPlayer(p);
            temp.setBattleTime(playerLogs.get(p.getName()).get(0).getBattleTime());
            temp.setTotalBattles(totalBattles);
            temp.setTotalVictories(totalVictories);
            //An ArrayList containing different win stats
            playerVictories.add(temp);

        }
        return  playerVictories;
    }

    /*
     * Params: playerVictories - contains recent win information
     * Returns: Printed information in the console
     * */
//    public  void printVictoryResults(ArrayList<BattleWin> playerVictories){
//        //25 is the max amount of battles pulled per Player
//        DecimalFormat format = new DecimalFormat("#.##");
//        playerVictories.forEach((win)-> System.out.println(Colour.ANSI_BLUE + win.getPlayer().getName()
//                + Colour.ANSI_PURPLE + " | 3v3 victories: " + win.getWins().get(0)
//                + Colour.ANSI_GREEN + " | Solo victories: " + win.getWins().get(1)
//                + Colour.ANSI_CYAN + " | Duels: " + win.getWins().get(2)
//                + Colour.ANSI_RESET+ " | Duos: " + win.getWins().get(3)
//                + Colour.ANSI_RED + " | Total Battles: " + win.getWins().get(4)
//                + Colour.ANSI_YELLOW + " | W/R: " + format.format(win.getWinRate())));
//
//        //System.out.println(Colour.ANSI_RED + "Total battles played (per player): " +25+Colour.ANSI_RESET);
//    }
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



}
