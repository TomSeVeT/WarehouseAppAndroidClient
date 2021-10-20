package pl.sevet.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import pl.sevet.myapplication.model.Transaction;
import pl.sevet.myapplication.model.TransactionType;
import pl.sevet.myapplication.model.items.Item;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocationsInventory extends AppCompatActivity {

    TableLayout locInventoryTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_inventory);
        locInventoryTable = findViewById(R.id.locItemsTable);
        setItemsTable();
    }

    private void setItemsTable() {
        SLib.getItemTypes().forEach(x->{
            TableRow typeRow = new TableRow(this);
            typeRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeRow.setPadding(10,40,10,20);
            typeRow.setClickable(true);
            typeRow.setOnClickListener(v -> {
                typeRow.setBackgroundColor(Color.CYAN);
                SLib.setSelectedItems(new ArrayList<>());
                if(locInventoryTable.getChildAt(1) instanceof TableRow || locInventoryTable.getChildAt(1) == null) {
                    TableLayout itemsTable = new TableLayout(this);
                    itemsTable.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    itemsTable.setPadding(10,40,10,20);

                    ((TextView)typeRow.getChildAt(0)).setTextSize(30);

                    locInventoryTable.removeAllViews();
                    locInventoryTable.addView(typeRow);
                    locInventoryTable.addView(itemsTable);


                    for (Item item:
                            SLib.getItems()) {
                        if(item.getTypeId() == x.getId()){
                            TableRow itemRow = new TableRow(this);
                            itemRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            itemRow.setPadding(10,40,10,20);
                            itemRow.setClickable(true);
                            itemRow.setOnClickListener(v12 -> {
                                if(SLib.getSelectedItems().contains(item)) {
                                    itemRow.setBackgroundColor(Color.TRANSPARENT);
                                    SLib.getSelectedItems().remove(item);
                                }else{
                                    itemRow.setBackgroundColor(Color.RED);
                                    SLib.getSelectedItems().add(item);
                                }
                            });

                            TextView nameLabel = new TextView(this);
                            nameLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            nameLabel.setText(x.getName());
                            nameLabel.setTextSize(20);

                            TextView quantityLabel = new TextView(this);
                            quantityLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            quantityLabel.setText( String.format("%.0f",item.getQuantity()));
                            quantityLabel.setTextSize(20);

                            TextView dateLabel = new TextView(this);
                            dateLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            if(item.getExpDate() == null)
                                dateLabel.setText("Brak daty");
                            else
                                dateLabel.setText( new SimpleDateFormat("dd.MM.yyyy").format(item.getExpDate()));
                            dateLabel.setTextSize(20);

                            itemRow.addView(nameLabel);
                            itemRow.addView(quantityLabel);
                            itemRow.addView(dateLabel);

                            locInventoryTable.addView(itemRow);

                        }
                    }

                    TableRow sendToRow = new TableRow(this);
                    sendToRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    sendToRow.setPadding(10,40,10,20);

                    Spinner locationSpinner = new Spinner(this);
                    List<String> locationsNames = new ArrayList<>();
                    SLib.getLocations().forEach(location->locationsNames.add(location.getName()));
                    ArrayAdapter<String> dataAdapter1 =
                            new ArrayAdapter<>(locationSpinner.getContext(),
                                    R.layout.spinner_item, locationsNames);
                    locationSpinner.setAdapter(dataAdapter1);

                    Button sendButton = new Button(this);
                    sendButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    sendButton.setPadding(10,40,10,20);
                    sendButton.setTextSize(15);
                    sendButton.setText("Prznieś do lokacji ->");
                    sendButton.setOnClickListener(v1 -> {
                        if(!SLib.getSelectedItems().isEmpty())
                            changeSelectedLocalisation(locationSpinner.getSelectedItemId());
                    });

                    sendToRow.addView(sendButton);
                    sendToRow.addView(locationSpinner);




                    TableRow useRow = new TableRow(this);
                    useRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    useRow.setPadding(10,40,10,20);

                    Spinner quantitySpinner = new Spinner(this);
                    quantitySpinner.setOnTouchListener((v13, event) -> {
                        List<String> quantityList = new ArrayList<>();
                        int quantity = 0;
                        for(Item item: SLib.getSelectedItems()){
                            quantity+=item.getQuantity();
                        }
                        for(int i =1; i<=quantity; i++){
                            quantityList.add("  "+i+"  ");
                        }
                        ArrayAdapter<String> dataAdapter2 =
                                new ArrayAdapter<>(quantitySpinner.getContext(),
                                        R.layout.spinner_item, quantityList);
                        quantitySpinner.setAdapter(dataAdapter2);
                        return false;
                    });


                    Button useButton = new Button(this);
                    useButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    useButton.setPadding(10,40,10,20);
                    useButton.setTextSize(15);
                    useButton.setText("Zużyj sztuki ->");
                    useButton.setOnClickListener(v1 -> useQuantity(quantitySpinner.getSelectedItem().toString()));

                    useRow.addView(useButton);
                    useRow.addView(quantitySpinner);




                    locInventoryTable.addView(sendToRow);
                    locInventoryTable.addView(useRow);

                }
                else {
                    locInventoryTable.removeAllViews();
                    setItemsTable();
                }
            });


            TextView typeNameLabel = new TextView(this);
            typeNameLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeNameLabel.setText(x.getName());
            typeNameLabel.setTextSize(20);

            TextView typeQuantity = new TextView(this);
            float itemQuantity = 0;
            for (Item item:
                    SLib.getItems()) {
                if(item.getTypeId() == x.getId())
                    itemQuantity += item.getQuantity();
            }
            typeQuantity.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeQuantity.setText(String.format("%.0f",itemQuantity));
            typeQuantity.setTextSize(20);
            if(itemQuantity != 0)
            {
                typeRow.addView(typeNameLabel);
                typeRow.addView(typeQuantity);
                locInventoryTable.addView(typeRow);
            }
        });
    }

    private void useQuantity(String quantityString) {
        float quantity = Float.parseFloat(quantityString.replaceAll(" ",""));
        List<Transaction> transaction = new ArrayList<>();
        for(int i = 0;quantity>0;i++){
            if(SLib.getSelectedItems().get(i).getQuantity() >= quantity){
                transaction.add(new Transaction(
                        TransactionType.USE,
                        SLib.getSelectedItems().get(i).getId(),
                        SLib.getSelectedItems().get(i).getTypeId(),
                        SLib.getSelectedItems().get(i).getLocationId(),
                        SLib.getSelectedItems().get(i).getLocationId(),
                        SLib.getSelectedItems().get(i).getQuantity(),
                        SLib.getSelectedItems().get(i).getQuantity()-quantity,
                        SLib.getUserDetails().getId(),
                        new Date(),
                        SLib.getSelectedItems().get(i).getExpDate()
                ));
                SLib.getItems().remove(SLib.getSelectedItems().get(i));
                SLib.getSelectedItems().get(i).setQuantity(SLib.getSelectedItems().get(i).getQuantity()-quantity);
                if(SLib.getSelectedItems().get(i).getQuantity()>0)
                    SLib.getItems().add(SLib.getSelectedItems().get(i));
                quantity = 0;
            }else{
                transaction.add(new Transaction(
                        TransactionType.USE,
                        SLib.getSelectedItems().get(i).getId(),
                        SLib.getSelectedItems().get(i).getTypeId(),
                        SLib.getSelectedItems().get(i).getLocationId(),
                        SLib.getSelectedItems().get(i).getLocationId(),
                        SLib.getSelectedItems().get(i).getQuantity(),
                        0L,
                        SLib.getUserDetails().getId(),
                        new Date(),
                        SLib.getSelectedItems().get(i).getExpDate()
                ));
                quantity -= SLib.getSelectedItems().get(i).getQuantity();
                SLib.getItems().remove(SLib.getSelectedItems().get(i));
            }
        }

        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.USE_ITEM);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(transaction);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                locInventoryTable.removeAllViews();
                setItemsTable();
            }

            @Override
            public void onError() {

            }
        });


    }

    private void changeSelectedLocalisation(Long newLocId) {

        if(SLib.getSelectedItems().get(0).getLocationId().equals(newLocId))
            return;
        List<Transaction> transactionList = new ArrayList<>();
        SLib.getSelectedItems().forEach(x->{
            SLib.getItems().remove(x);
            if(x.getLocationId().equals(newLocId))
                return;
            transactionList.add(new Transaction(
                    TransactionType.MOVE,
                    x.getId(),
                    x.getTypeId(),
                    0L,
                    newLocId,
                    x.getQuantity(),x.getQuantity(),
                    SLib.getUserDetails().getId(),
                    new Date(),
                    x.getExpDate()));
        });
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setRequestURL(Constants.MOVE);
        SLib.getRequestApi().setObjectParameter(transactionList);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                locInventoryTable.removeAllViews();
                setItemsTable();
            }

            @Override
            public void onError() {

            }
        });
    }
}