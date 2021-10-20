package pl.sevet.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import pl.sevet.myapplication.model.history.ResponseToHistoryItems;
import pl.sevet.myapplication.model.items.ResponseToItems;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

public class WarehouseMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_menu);
        SLib.setRequestApi(new RequestApi(this));

        loadWarehouseHistory();
        loadWarehouseItems();
    }

    private void loadWarehouseItems() {
        SLib.getRequestApi().setRequestURL(Constants.WH_ITEMS);
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.setItems(ResponseToItems.convert(SLib.getRequestApi().getResponseString()));
            }

            @Override
            public void onError() {

            }
        });
    }

    private void loadWarehouseHistory() {
        //
    }


    public void buttonDeliveryClick(View view) {
        Intent intent = new Intent(this,Delivery.class);
        startActivityForResult(intent,0);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        loadWarehouseItems();
    }

    public void buttonShowInventoryClick(View view) {
        Intent intent = new Intent(this,WarehouseInventory.class);
        startActivityForResult(intent,0);
    }

    public void buttonWarehouseHistoryClick(View view) {
        loadHistory();
    }

    private void loadHistory() {
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.HISTORY_USAGE);
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.setTransactionHistory(ResponseToHistoryItems.convert(SLib.getRequestApi().getResponseString()));
                Intent intent = new Intent(getBaseContext(), WarehouseHistory.class);
                startActivity(intent);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void buttonShowDictionaryClick(View view) {
        Intent intent = new Intent(this, ItemsDictionary.class);
        startActivity(intent);
    }

    public void buttonShowGroupDictionaryClick(View view) {
        Intent intent = new Intent(this, ItemGroupDictionary.class);
        startActivity(intent);
    }
}