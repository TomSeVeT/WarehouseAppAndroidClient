package pl.sevet.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import pl.sevet.myapplication.model.items.ResponseToItemGroups;
import pl.sevet.myapplication.model.items.ResponseToItemTypes;
import pl.sevet.myapplication.model.locations.ResponseToLocationList;
import pl.sevet.myapplication.model.users.ResponseToUserList;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

public class MainAdminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_menu);
        SLib.setRequestApi(new RequestApi(this));
        try {
            initialize();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initialize() throws InterruptedException {
        Thread.sleep(100);
        loadUsers();
        loadLocations();
        loadItemTypes();
        loadItemGroups();
    }


    private void loadUsers() {
        SLib.getRequestApi().setRequestURL(Constants.ALL_USERS);
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.setUsers(ResponseToUserList.convert(SLib.getRequestApi().getResponseString()));
            }
            @Override
            public void onError() {

            }
        });
    }

    private void loadLocations() {
        SLib.getRequestApi().setRequestURL(Constants.ALL_LOCATIONS);
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.setLocations(ResponseToLocationList.convert(SLib.getRequestApi().getResponseString()));
            }
            @Override
            public void onError() {

            }
        });
    }


    public void buttonUsersClick(View view) {
        Intent intent = new Intent(this,Users.class);
        startActivity(intent);
    }

    public void buttonWarehouseClick(View view) {
        Intent intent = new Intent(this,WarehouseMenu.class);
        startActivity(intent);
    }

    public void buttonLocationsClick(View view) {
        Intent intent = new Intent(this,LocationMenu.class);
        startActivity(intent);
    }

    private void loadItemTypes() {
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

    private void loadItemGroups() {
        SLib.getRequestApi().setRequestURL(Constants.ITEM_GROUPS);
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.setItemGroups(ResponseToItemGroups.convert(SLib.getRequestApi().getResponseString()));
            }
            @Override
            public void onError() {

            }
        });
    }

}