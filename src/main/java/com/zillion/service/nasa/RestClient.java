package com.zillion.service.nasa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;

/**
 * Created by Konstantin on 4/24/16.
 */

@Component
public class RestClient {

    @Autowired
    Environment environment;

    @Autowired
    RestTemplate restTemplate;


    @PostConstruct
    public void init() {

    }


    public  String getNasaPatents(String search, String limit) {


        //https://api.nasa.gov/patents/content?query=temperature&limit=5&api_key=DEMO_KEY
        //String nasaWebserviceUrl = "https://api.nasa.gov/patents/content";
        String nasaWebserviceUrl = environment.getRequiredProperty("nasa.webservice.url");
        nasaWebserviceUrl = nasaWebserviceUrl + "?query=" + search + "&limit=" + limit + "&api_key=DEMO_KEY";

        System.out.println("WILL CALL NASA...");

        String jsonString = restTemplate.getForObject(nasaWebserviceUrl, String.class);

        System.out.println("DONE WITH NASA");

        return jsonString;
    }


}
