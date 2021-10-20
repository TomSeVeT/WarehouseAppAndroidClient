package pl.sevet.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import pl.sevet.myapplication.model.items.ResponseToItemTypes;
import pl.sevet.myapplication.model.locations.ResponseToLocationList;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.util.ArrayList;
import java.util.List;

public class UserMainMenu extends AppCompatActivity {

    Spinner userLocationsSpinner;
    Button goToButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_menu);
        loadUserLocations();
        userLocationsSpinner = findViewById(R.id.userLocationsSpinner);
        goToButton = findViewById(R.id.goToArrow);
    }

    private void loadUserLocations() {
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().setRequestURL(Constants.USER_LOCATIONS+SLib.getUserDetails().getId());
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                if(SLib.getRequestApi().getResponseString().equals("[]")) {
                    String message = "Użytkownik nie ma uprawnień do żadnej lokacji!\nSkontaktuj się z Kasią/Szymonem lub zadzwoń 531625322";
                    Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    SLib.setLocations(ResponseToLocationList.convert(SLib.getRequestApi().getResponseString()));
                    List<String> locationNames = new ArrayList<>();
                    SLib.getLocations().forEach(x->locationNames.add(x.getName()));
                    ArrayAdapter<String> locationNamesAdapter =
                            new ArrayAdapter<>(getBaseContext(), R.layout.spinner_item, locationNames);
                    userLocationsSpinner.setAdapter(locationNamesAdapter);
                    userLocationsSpinner.setVisibility(View.VISIBLE);
                    goToButton.setVisibility(View.VISIBLE);
                    loadItemTypes();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    public void goToUserLocationClick(View view) {
        SLib.getLocations().forEach(x->{
            if(userLocationsSpinner.getSelectedItem().toString().equals(x.getName()))
                SLib.setSelectedLocation(x);
        });
        Intent intent = new Intent(this,UserLocation.class);
        startActivity(intent);
    }

    private void loadItemTypes() {
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.ITEM_TYPES);
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.setItemTypes(ResponseToItemTypes.convert(SLib.getRequestApi().getResponseString()));
            }
            @Override
            public void onError() {

            }
        });
    }
}