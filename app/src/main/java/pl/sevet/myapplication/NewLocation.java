package pl.sevet.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import pl.sevet.myapplication.model.Mode;
import pl.sevet.myapplication.model.locations.Location;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

public class NewLocation extends AppCompatActivity {


    EditText name;
    EditText descritpion;
    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);
        name = findViewById(R.id.locName);
        descritpion = findViewById(R.id.locDescription);
        saveButton = findViewById(R.id.saveLocationBtn);
        if(SLib.getMode() == Mode.EDIT){
            name.setText(SLib.getSelectedLocation().getName());
            descritpion.setText(SLib.getSelectedLocation().getDescription());
        }
    }

    public void createLocation(View view) {
        saveButton.setEnabled(false);
        Location location =  new Location();
        location.setName(name.getText().toString());
        location.setDescription(descritpion.getText().toString());
        if(SLib.getMode() ==  Mode.EDIT){
            location.setId(SLib.getSelectedLocation().getId());
        }

        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(location);
        SLib.getRequestApi().setRequestURL(Constants.CREATE_LOCATION);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                if(SLib.getMode() == Mode.NEW){
                    location.setId(Long.parseLong(SLib.getRequestApi().getResponseString()));
                }else{
                    SLib.getLocations().removeIf(x-> x.getId().equals(location.getId()));
                }
                SLib.getLocations().add(location);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError() {
                saveButton.setEnabled(true);
            }
        });
    }
}