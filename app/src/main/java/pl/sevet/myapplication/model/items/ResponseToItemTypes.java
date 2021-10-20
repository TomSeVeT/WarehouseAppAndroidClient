package pl.sevet.myapplication.model.items;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseToItemTypes {
    public static ArrayList<ItemType> convert (String response){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ItemType> itemTypeList = new ArrayList<>();
        try {
            itemTypeList = (ArrayList<ItemType>)mapper.readValue(response, new TypeReference<List<ItemType>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemTypeList;
    }
}
