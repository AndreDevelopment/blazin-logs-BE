package com.BrawlService.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
* Created by: Andre D'Souza
* Purpose: Responsible for creating an HTTP request to the Brawl Stars API and returning the response in the form of a JSON string.
* NOTES: Token is unique to my IP. Endpoints are sent using the Endpoint class which holds a list of accessible endpoints.
* */
public class BrawlAPIClient {

    private static String response;

    public String getRequest(String endpoint){

        HttpClient client = HttpClient.newHttpClient();

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjNhYjFlNDMyLTAxNDgtNDZhNS04NTY5LWM2NDgzNzEwNTZiNSIsImlhdCI6MTY4NTA2MzYxMiwic3ViIjoiZGV2ZWxvcGVyL2ZmNWNhODRiLTVmOTUtZjQ5Mi02ZWQ2LWRiMmVkZjhiYTMyOSIsInNjb3BlcyI6WyJicmF3bHN0YXJzIl0sImxpbWl0cyI6W3sidGllciI6ImRldmVsb3Blci9zaWx2ZXIiLCJ0eXBlIjoidGhyb3R0bGluZyJ9LHsiY2lkcnMiOlsiNzYuNjcuOTUuNjUiXSwidHlwZSI6ImNsaWVudCJ9XX0.4UIrUC7U3mNgmdIn-DH-B6diJ7Ru6chTudGelfKkdZ4Do8qtVxHxN8vVG2JL2V6yv6GYLpo_4YUnbb-266qB6Q";
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