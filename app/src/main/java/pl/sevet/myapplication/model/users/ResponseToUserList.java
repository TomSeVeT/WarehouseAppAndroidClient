package pl.sevet.myapplication.model.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseToUserList {
    public static ArrayList<User> convert (String response){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> userList = new ArrayList<>();
        try {
            userList = (ArrayList<User>)mapper.readValue(response, new TypeReference<List<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
