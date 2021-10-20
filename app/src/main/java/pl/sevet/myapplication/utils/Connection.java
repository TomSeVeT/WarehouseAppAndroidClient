package pl.sevet.myapplication.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.sevet.myapplication.model.BaseModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Connection {
    public static void openConnection(String URL){
        try {
            SLib.getConnection().disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
        URL endpointUrl = null;
        HttpURLConnection conn = null;
        try {
            endpointUrl = new URL(URL);
            conn = (HttpURLConnection) endpointUrl.openConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        SLib.setConnection(conn);
    }

    public static void setBody(Object o){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String oAsJsonString = mapper.writeValueAsString(o);
            OutputStream os = SLib.getConnection().getOutputStream();
            byte[] input = oAsJsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setAsPost(String URL) {
        openConnection(URL);
        try {
            SLib.getConnection().setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }
    public static <T extends BaseModel>  T exec(Class<T> klasa){
        try{
            if(SLib.getConnection().getResponseCode()==200){
                ObjectMapper mapper = new ObjectMapper();
                BufferedReader br = new BufferedReader(new InputStreamReader((SLib.getConnection().getInputStream())));
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                output = sb.toString();
                if(output!=""){
                    T object = mapper.readValue(output, klasa);
                    return object;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect(){
        SLib.getConnection().disconnect();
    }
}
