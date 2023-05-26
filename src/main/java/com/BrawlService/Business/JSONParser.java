package com.BrawlService.Business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class JSONParser {


    /*Takes in the JSON and the Java Object you wish to parse it to. Will return that Java object after conversion from JSON.
    * */
    public static <T>T parseEntity(String responseBody, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(responseBody, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /*Takes in the JSON and the Java List of intended Object you wish to parse it to. Will return that Java object after conversion from JSON.
     * */
    public static <T>ArrayList<T> parseEntityList(String responseBody, TypeReference<ArrayList<T>> clazz){
        try {
            return new ObjectMapper().readValue(responseBody, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
