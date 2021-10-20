package pl.sevet.myapplication.model.history;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseToHistoryItems {
    public static ArrayList<HistoryItem> convert (String response){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<HistoryItem> itemList = new ArrayList<>();
        try {
            itemList = (ArrayList<HistoryItem>)mapper.readValue(response, new TypeReference<List<HistoryItem>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}
