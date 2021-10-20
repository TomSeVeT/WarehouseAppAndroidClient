package pl.sevet.myapplication.utils;

import android.app.Activity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestApi {

    private RequestQueue queue;
    private Object objectParameter;
    private String requestURL;
    private String responseString;
    private String volleyResponseError;
    private int method;
    private final Activity activity;

    public RequestApi(Activity activity) {
        this.activity = activity;
    }

    public void requestWithObject(final VolleyCallBack callBack) {
        queue = Volley.newRequestQueue(activity);
        StringRequest stringRequest = new StringRequest(method, requestURL,
                response -> {
                    responseString = response;
                    volleyResponseError ="";
                    callBack.onSuccess();
                }, error -> {
                    volleyResponseError = error.toString();
                    callBack.onError();
                }) {
            @Override
            public byte[] getBody() {
                String your_string_json = null;
                try {
                    your_string_json = new ObjectMapper().writeValueAsString(objectParameter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return your_string_json.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(stringRequest);
        queue.start();
    }

    public void request(final VolleyCallBack callBack){
        StringRequest stringRequest = new StringRequest(method, requestURL,
                response -> {
                    responseString = response;
                    volleyResponseError ="";
                    callBack.onSuccess();
                }, error -> {
                    volleyResponseError = error.toString();
                    callBack.onError();
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(stringRequest);
        queue.start();
    }

    public Object getObjectParameter() {
        return objectParameter;
    }

    public void setObjectParameter(Object objectParameter) {
        this.objectParameter = objectParameter;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }



    public String getResponseString() {
        return responseString;
    }

    public String getVolleyResponseError() {
        return volleyResponseError;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }
}
