package com.zillion.service.extractor.impl;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.zillion.service.extractor.JsonPathDataExtractor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantin on 4/28/16.
 */

@Component
public class JsonPathDataExtractorImpl implements JsonPathDataExtractor {


    @Override
    public List<String> extractInventorFullName(String jsonString) throws IOException {
        List<String> inventorNameList = new ArrayList<>();

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);

        List<String> firstNameList = JsonPath.read(document, "$.results[*].innovator[*].fname");
        List<String> lastNameList = JsonPath.read(document, "$.results[*].innovator[*].lname");

        for (int i = 0; i < firstNameList.size(); i ++) {
            inventorNameList.add(firstNameList.get(i) + " " + lastNameList.get(i));
        }


        System.out.println(inventorNameList);

        return inventorNameList;
    }
}
