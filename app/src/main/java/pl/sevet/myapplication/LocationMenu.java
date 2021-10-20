package pl.sevet.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.volley.Request;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.sevet.myapplication.model.items.ResponseToItems;
import pl.sevet.myapplication.model.users.UserAccess;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationMenu extends AppCompatActivity {

    ConstraintLayout locationMenuLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_menu);
        locationMenuLayout = findViewById(R.id.locationMenuLayout);
        loadUsersAccesses();
    }

    private void loadUsersAccesses() {
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.ALL_ACCESSES);
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.setUsersAccess(new HashMap<>());
                List<UserAccess> userAccessList = null;
                try {
                    userAccessList = new ObjectMapper().readValue(SLib.getRequestApi().getResponseString(), new TypeReference<List<UserAccess>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert userAccessList != null;
                userAccessList.forEach(x->{
                    if(!SLib.getUsersAccess().containsKey(x.getUserId()))
                        SLib.getUsersAccess().put(x.getUserId(),new ArrayList<>());

                    SLib.getUsersAccess().get(x.getUserId()).add(x.getLocationId());
                });
                SLib.getUsers().forEach(x->{
                    if(!SLib.getUsersAccess().containsKey(x.getId()))
                        SLib.getUsersAccess().put(x.getId(), new ArrayList<>());
                });
            }

            @Override
            public void onError() {

            }
        });

    }

    public void buttonShowDictionaryClick(View view) {
        Intent intent = new Intent(locationMenuLayout.getContext(),LocationsDictionary.class);
        startActivity(intent);
    }

    public void buttonShowInventoryClick(View view) {
        chooseLocation("inventory");
    }

    private void chooseLocation(String open) {
        List<String> locationList = new ArrayList<>();
        Spinner locationSelection = new Spinner(this);
        locationSelection.setPadding(20,20,20,20);
        for(int i=1; i< SLib.getLocations().size(); i++){
            locationList.add(SLib.getLocations().get(i).getName());
        }
        ArrayAdapter<String> dataAdapter1 =
                new ArrayAdapter<>(this,
                        R.layout.spinner_item, locationList);
        locationSelection.setAdapter(dataAdapter1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wybierz lokację");
        builder.setView(locationSelection);
        builder.setPositiveButton("Wybierz", (dialog, which) -> {
            SLib.setSelectedLocation(
                    SLib.getLocations().stream().filter(x-> x.getName().equals(locationSelection.getSelectedItem().toString())).findFirst().get()
            );
            if(open.equals("inventory")){
                loadAndStartLocationInventory();
            }else if(open.equals("history")){

            }
            dialog.dismiss();
        });
        builder.setNegativeButton("Anuluj", (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadAndStartLocationInventory() {
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.LOCATION_ITEMS+SLib.getSelectedLocation().getId());
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                if(SLib.getItems()!=null)
                    SLib.getItems().clear();
                SLib.setItems(ResponseToItems.convert(SLib.getRequestApi().getResponseString()));
                Intent intent = new Intent(locationMenuLayout.getContext(),LocationsInventory.class);
                startActivity(intent);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void buttonLocationHistoryClick(View view) {
    }

    public void buttonEmployeesAccess(View view) {
        try {
            String mapAsJsonString = new ObjectMapper().writeValueAsString(SLib.getUsersAccess());
            SLib.setUsersAccessCopy(new ObjectMapper().readValue(mapAsJsonString,new TypeReference<Map<Long,List<Long>>>(){}));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(locationMenuLayout.getContext(),LocationEmployeeAccess.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        checkForChanges();
    }

    private void checkForChanges() {
        deleteOldAccess();
        createNewAccess();
    }

    private void createNewAccess() {
        List<UserAccess> accessToAdd = new ArrayList<>();
        SLib.getUsersAccess().forEach((id,userAccesses)-> userAccesses.forEach(x->{
            if(!SLib.getUsersAccessCopy().get(id).contains(x)){
                accessToAdd.add(new UserAccess(id,x));
            }
        }));
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setObjectParameter(accessToAdd);
        SLib.getRequestApi().setRequestURL(Constants.ACCESS_CREATE);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }

    private void deleteOldAccess() {
        List<UserAccess> accessToDelete = new ArrayList<>();
        SLib.getUsersAccessCopy().forEach((id,userAccesses)-> userAccesses.forEach(x->{
            if(!SLib.getUsersAccess().get(id).contains(x)){
                accessToDelete.add(new UserAccess(id,x));
            }
        }));
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setObjectParameter(accessToDelete);
        SLib.getRequestApi().setRequestURL(Constants.ACCESS_DELETE);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }

}