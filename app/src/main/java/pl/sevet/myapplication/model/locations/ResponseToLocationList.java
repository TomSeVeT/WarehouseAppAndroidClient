package pl.sevet.myapplication.model.locations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseToLocationList {
    public static ArrayList<Location> convert (String response){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Location> locationList = new ArrayList<>();
        try {
            locationList = (ArrayList<Location>)mapper.readValue(response, new TypeReference<List<Location>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locationList;
    }
}
