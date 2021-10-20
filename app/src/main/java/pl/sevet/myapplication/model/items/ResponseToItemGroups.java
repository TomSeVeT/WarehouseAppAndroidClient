package pl.sevet.myapplication.model.items;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseToItemGroups {
    public static ArrayList<ItemGroup> convert (String response){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ItemGroup> itemGroupList = new ArrayList<>();
        try {
            itemGroupList = (ArrayList<ItemGroup>)mapper.readValue(response, new TypeReference<List<ItemGroup>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemGroupList;
    }
}
