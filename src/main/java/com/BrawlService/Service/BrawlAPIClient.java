package com.BrawlService.Service;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
* Created by: Andre D'Souza
* Purpose: Responsible for creating an HTTP request to the Brawl Stars API and returning the response in the form of a JSON string.
* NOTES: Token is unique to my IP. Please create your own env variable with your unique key
* */
public class BrawlAPIClient {

    private static String response;

    /*
    * Params: endpoint - The endpoint of the Brawl API trying to be reached
    * Returns: Response body of the request
    * */
    public String getRequest(String endpoint){

        HttpClient client = HttpClient.newHttpClient();

        Dotenv dotenv = Dotenv.configure().load();
        String token = dotenv.get("BRAWL_KEY");
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.brawlstars.com/v1/"+endpoint))

                .header("accept", "application/json")
                .header("authorization", "Bearer "+ token)
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(BrawlAPIClient::setResponse)
                .join();

        return response;
    }


    private static void setResponse(String responseBody){
        response = responseBody;
    }





}
