package com.company.automation.utils;

import com.company.automation.data.BaseData;
import com.company.automation.config.ConfigurationManager;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Zeeshan
 */
public final class JsonDataProviderUtils {
    private JsonDataProviderUtils() {}

    private static Object[][] convert(ArrayList<? extends BaseData> data) {
        int noOfRows = data.size();
        Object[][] dataArray = new Object[noOfRows][1];

        for (int i = 0; i < noOfRows; i++) {
            dataArray[i][0] = data.get(i);
        }
        return dataArray;
    }

    public static Object[][] processJson(
            Class<? extends BaseData> clazz, String jsonFilePath, String testCaseId) throws JsonProcessingException {

        String jsonFileContent;
        try {
            jsonFileContent =
                    new String(
                            Files.readAllBytes(
                                    Paths.get(
                                            ConfigurationManager.configuration()
                                                    .baseTestDataPath()
                                                    + jsonFilePath)),
                            StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return new Object[0][0];
        }

        ArrayList<BaseData> parsedData = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JSONArray jsonArray = new JSONArray(jsonFileContent);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;

            // Use Jackson's object mapper to convert the JSON object to the specified class
            BaseData baseData = mapper.readValue(jsonObject.toString(), clazz);

            if (baseData.getTestCaseId().equals(testCaseId)) {
                parsedData.add(baseData);
            }
        }

        return convert(parsedData);
    }

}