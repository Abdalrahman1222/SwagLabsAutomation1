package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class DataDriven {
    private static final String filePath = "testData/testData.json";


    public static String jsonReader(String userType, String key){
        try (FileReader reader = new FileReader(filePath)){
            JsonObject data = JsonParser.parseReader(reader).getAsJsonObject();
            return data.getAsJsonObject(userType).get(key).getAsString();
        } catch (IOException e){
            throw new RuntimeException("Could not read the file: " + filePath, e);
        }
    }
}