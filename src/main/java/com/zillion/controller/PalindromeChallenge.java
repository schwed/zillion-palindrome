package com.zillion.controller;

import com.zillion.domain.Inventor;
import com.zillion.service.nasa.RestClient;
import com.zillion.service.extractor.JsonPathDataExtractor;
import com.zillion.service.palindrome.PalindromaticStringsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Konstantin on 4/24/16.
 */
@RestController
public class PalindromeChallenge {

    @Autowired
    RestClient restClient;

    @Autowired
    JsonPathDataExtractor jsonPathDataExtractor;

    @Autowired
    PalindromaticStringsCalculator palindromaticStringsCalculator;


    /**
     * GET This is a “view” operation.
     *  This should be repeatable and should never change the data it views
     *  HTTP Status Codes:
     *  200 - OK This means everything went fine.
     *  400 - Bad Request that technically is working b
     *  ut something in the request data is not matching what this service expects.
     */
    @RequestMapping(value = "/palindromes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Inventor> getPatentInventors(@RequestParam(name = "search") String search, @RequestParam(name = "limit", required = false, defaultValue = "1") int limit) {

        System.out.println("search: " + search + ",limit: " + limit);

        if (limit > 5) {
            System.out.println("limit is exceeded" + limit);
            return null;
        }

        // call nasa webservice
        String jsonStringPatents = restClient.getNasaPatents(search, String.valueOf(limit));

        System.out.println(jsonStringPatents);

        List<Inventor> inventorsList = new ArrayList<>();

        try {
            // extract inventor name
            List<String> inventorNamesList = jsonPathDataExtractor.extractInventorFullName(jsonStringPatents);

            // calculate palindromes
            for (String inventorName : inventorNamesList) {

                int count = palindromaticStringsCalculator.calculatePalindromes(inventorName) ;

                inventorsList.add(new Inventor(inventorName, count));
            }

        } catch (IOException e) {

        }

        Collections.sort(inventorsList);

        return inventorsList;
    }



}
