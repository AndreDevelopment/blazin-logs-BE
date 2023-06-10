package com.BrawlService.Service;

import com.BrawlService.Business.JSONParser;
import com.BrawlService.Entity.BrawlEntity.*;
import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Function;

/*
 * Created by: Andre D'Souza
 * Purpose: Encapsulate the logic for making a request to the Brawl API and convert the JSON response into a Java Bean
 * NOTES: Each method in this class encapsulates the logic for a different type of request. Please see method documentation
 *
  */
public class BrawlRequest {
    private final BrawlAPIClient client;

    /*
    * Params: tag - A player tag or club tag that needs to be converted
    * Returns: the tag with the '#' replaced with '%23"
    * */
    private final Function<String,String> convertTag = tag -> {
        if (tag.length()<=0)
            return "";
        return tag.charAt(0)=='#'? "%23"+tag.substring(1) : "%23"+tag;
    };


    public BrawlRequest(){
        client = new BrawlAPIClient();
    }

    //PLAYER RELATED REQUESTS

    /*
    * Params: tag of the Player
    * Returns: Java Player Bean containing appropriate data
    * */
    public Player getPlayer(String tag) {
        try{
            return JSONParser.parseEntity(client.getRequest("players/" + convertTag.apply(tag)),Player.class);
        }
        catch (Exception e){
            System.out.println("Player cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    /*
     * Params: tag of the Player
     * Returns: List of Logs (Beans)
     */
    public ArrayList<Log> getBattleLog(String tag)  {
        try {
            return JSONParser.parseEntityList(
                    new JSONObject(client.getRequest("players/" + convertTag.apply(tag) + "/battlelog"))
                            .getJSONArray("items").toString()
                    , new TypeReference<>() {
                    });
        } catch (Exception e){
            System.out.println("Battle log cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    //CLUB RELATED REQUESTS

    /*
     * Params: tag of the Club
     * Returns: Java Club Bean containing appropriate data
     * */
    public Club getClub(String tag)  {
        try {
            return JSONParser.parseEntity(client.getRequest("clubs/" + convertTag.apply(tag)), Club.class);
        }  catch (Exception e){
            System.out.println("Club cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /*
     * Params: tag of the Player
     * Returns: List of Player (Beans) of the specified club
     * */
    public ArrayList<Player> getClubMembers(String tag) {
        try {
            return JSONParser.parseEntityList(new JSONObject(
                            client.getRequest("clubs/" + convertTag.apply(tag) + "/members")).getJSONArray("items").toString()
                    , new TypeReference<>() {
                    });
        }  catch (Exception e){
            System.out.println("Club member list cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    //BRAWLER RELATED REQUESTS

    /*
     * Params: tag of the Brawler
     * Returns: Java Brawler Bean containing appropriate data
     * */
    public Brawler getBrawler(String tag)  {
        try {
            return JSONParser.parseEntity(client.getRequest("brawlers/" + tag), Brawler.class);
        }  catch (Exception e){
            System.out.println("Brawler cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /*
     * Params: <none>
     * Returns: List of all available brawlers in game
     * */
    public ArrayList<Brawler> getBrawlerList()  {
        try {
            return JSONParser.parseEntityList(
                    new JSONObject(client.getRequest("brawlers")).getJSONArray("items").toString(), new TypeReference<>() {
                    });
        }  catch (Exception e){
            System.out.println("Brawler list cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //RANKING METHODS

    /*
     * Params: code - two-letter Country code or' global'
     * Returns: List of Seasons containing start and end time of each season
     * */
    public ArrayList<Season> getRankingPowerPlaySeasons(String code)  {
        try {
            return JSONParser.parseEntityList(new JSONObject(client.getRequest("rankings/" + code + "/powerplay/seasons"))
                    .getJSONArray("items").toString(), new TypeReference<>() {
            });
        } catch (Exception e){
            System.out.println("Season list cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /*
     * Params: code - two-letter Country code or 'global' , id - of the season
     * Returns: List of Players who were on the leaderboards that season
     * */
    public ArrayList<Season> getRankingPowerPlaySeasonsById(String code,String id)  {
        try {
            return JSONParser.parseEntityList(new JSONObject(client.getRequest("rankings/" + code + "/powerplay/seasons/" + id))
                    .getJSONArray("items").toString(), new TypeReference<>() {
            });
        }catch (Exception e){
            System.out.println("Season list cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /*
     * Params: code - two-letter Country code or 'global'
     * Returns: List of Clubs that are on the leaderboards
     * */
    public ArrayList<Club> getRankingClubs(String code)  {
        try {
            return JSONParser.parseEntityList(client.getRequest("rankings/" + code + "/clubs"), new TypeReference<>() {
            });
        }catch (Exception e){
            System.out.println("Club ranking cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /*
     * Params: code - two-letter Country code or 'global'
     * Returns: List of Players that are on the leaderboards
     * */
    public ArrayList<Player> getRankingPlayers(String code)  {
     try {
        return JSONParser.parseEntityList(new JSONObject(client.getRequest("rankings/" + code + "/players"))
                .getJSONArray("items").toString(), new TypeReference<>() {
        });
         }catch (Exception e){
        System.out.println("Player list cannot be parsed");
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
    /*
     * Params: code - two-letter Country code or 'global'
     * Returns: List of Players that are on the leaderboards for specified Brawler
     * */
    public ArrayList<Player> getRankingBrawler(String code,String id)  {
        try {
            return JSONParser.parseEntityList(new JSONObject(client.getRequest("rankings/" + code + "/brawlers/" + id))
                    .getJSONArray("items").toString(), new TypeReference<>() {
            });
        }catch (Exception e){
            System.out.println("Brawler list cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    /*
     * Params: <none>
     * Returns: List of Events currently in game
     * */
    public ArrayList<EventSlot> getEventRotation()  {
        try {
            return JSONParser.parseEntityList(new JSONArray(
                    client.getRequest("events/rotation")).toString(), new TypeReference<>() {
            });
        }catch (Exception e){
            System.out.println("Event rotation list cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
