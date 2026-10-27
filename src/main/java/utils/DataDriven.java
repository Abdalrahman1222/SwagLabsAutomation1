package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    public static List<String> jsonReaderList(String listName){
        try (FileReader reader = new FileReader(filePath)){
            JsonObject data = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray array = data.getAsJsonArray(listName);
            List<String> items = new ArrayList<>();
            for (JsonElement element : array){
                items.add(element.getAsString());
            }
            return items;
        } catch (IOException e){
            throw new RuntimeException("Could not read the file: " + filePath, e);
        }
    }
}