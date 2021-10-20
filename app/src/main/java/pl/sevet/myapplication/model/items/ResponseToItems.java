package pl.sevet.myapplication.model.items;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseToItems {
    public static ArrayList<Item> convert (String response){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Item> itemList = new ArrayList<>();
        try {
            itemList = (ArrayList<Item>)mapper.readValue(response, new TypeReference<List<Item>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}
