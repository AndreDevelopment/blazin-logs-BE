package com.services;

import com.business.JSONParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.helper.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Function;

/*
 * Created by: Andre D'Souza
 * Purpose: Encapsulate the logic for making every type of request to the Brawl API.
 * NOTES: Simply put these methods will only ask for a tag or id and then return the java object of
 * whichever entity/entities the user is looking for. Ex:
 *
 * User sends tag: #R2Y08YC8 -> getPlayer(). getPlayer() returns a Player object with appropriate fields filled out.
 * */
public class BrawlRequest {
    private final BrawlAPIClient client;
    private final Function<String,String> convertTag = tag -> {
        if (tag.length()<=0)
            return "";
        return tag.charAt(0)=='#'? "%23"+tag.substring(1) : "%23"+tag;
    };

    public BrawlRequest(){
        client = new BrawlAPIClient();
    }

    //PLAYER RELATED REQUESTS
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
    public Club getClub(String tag)  {
        try {
            return JSONParser.parseEntity(client.getRequest("clubs/" + convertTag.apply(tag)), Club.class);
        }  catch (Exception e){
            System.out.println("Club cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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
    public Brawler getBrawler(String tag)  {
        try {
            return JSONParser.parseEntity(client.getRequest("brawlers/" + tag), Brawler.class);
        }  catch (Exception e){
            System.out.println("Brawler cannot be parsed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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
