package pl.sevet.myapplication;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import pl.sevet.myapplication.model.Transaction;
import pl.sevet.myapplication.model.TransactionType;
import pl.sevet.myapplication.model.items.Item;
import pl.sevet.myapplication.model.items.ItemType;
import pl.sevet.myapplication.model.items.ResponseToItems;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserLocation extends AppCompatActivity {
    TableLayout userLocationItemsTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_location);
        userLocationItemsTable = findViewById(R.id.locationItemsTable);

        loadLocationItems();
    }

    private void loadLocationItems() {
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.LOCATION_ITEMS+ SLib.getSelectedLocation().getId());
        SLib.getRequestApi().setMethod(Request.Method.GET);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.setItems(ResponseToItems.convert(SLib.getRequestApi().getResponseString()));
                setItemsTable();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void setItemsTable() {
        SLib.getItemTypes().forEach(x->{
            TableRow typeRow = new TableRow(this);
            typeRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeRow.setPadding(10,40,10,20);

            TextView typeNameLabel = new TextView(this);
            typeNameLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeNameLabel.setText(x.getName());
            typeNameLabel.setTextSize(30);

            TextView typeQuantity = new TextView(this);
            float itemQuantity = 0;
            for (Item item:
                    SLib.getItems()) {
                if(item.getTypeId().equals(x.getId()))
                    itemQuantity += item.getQuantity();
            }
            typeQuantity.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeQuantity.setText(String.format("%.0f",itemQuantity));
            typeQuantity.setTextSize(30);


            Spinner quantitySpinner = new Spinner(this);
            List<String> numbers = new ArrayList<>();
            for (int i=1; i<=itemQuantity; i++){
                numbers.add("   "+i+"   ");
            }
            ArrayAdapter<String> dataAdapter =
                    new ArrayAdapter<>(this,
                            R.layout.spinner_item, numbers);
            quantitySpinner.setAdapter(dataAdapter);

            Button useButton = new Button(this);
            useButton.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            useButton.setPadding(10,20,0,20);
            useButton.setTextSize(25);
            useButton.setText("Użyj->");
            useButton.setOnClickListener(v -> useQuantity(quantitySpinner.getSelectedItem().toString(), x));

            if(itemQuantity != 0)
            {
                typeRow.addView(typeQuantity);
                typeRow.addView(typeNameLabel);
                typeRow.addView(useButton);
                typeRow.addView(quantitySpinner);
                userLocationItemsTable.addView(typeRow);
            }
        });
    }


    private void useQuantity(String quantityString, ItemType itemType) {
        float quantity = Float.parseFloat(quantityString.replaceAll(" ",""));
        List<Transaction> transactionList = new ArrayList<>();
        boolean itemWithExpDate = false;
        if(SLib.getItems().stream().filter(x-> x.getTypeId().equals(itemType.getId()))
                .findFirst().get().getExpDate() != null) itemWithExpDate = true;
        List<Item> itemsOfType;

        while(quantity!=0){
            itemsOfType = SLib.getItems().stream().filter(x-> x.getTypeId().equals(itemType.getId())).collect(Collectors.toList());
            Item itemToUse;
            if(itemWithExpDate)
                itemToUse= itemsOfType.stream().min(Comparator.comparing(Item::getExpDate)).get();
            else
                itemToUse = itemsOfType.stream().min(Comparator.comparing(Item::getQuantity)).get();
            SLib.getItems().remove(itemToUse);
            quantity = addTransaction(quantity, transactionList, itemToUse);
        }

        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.USE_ITEM);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(transactionList);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                userLocationItemsTable.removeAllViews();
                setItemsTable();
            }

            @Override
            public void onError() {

            }
        });
    }

    private float addTransaction(float quantity, List<Transaction> transactionList, Item itemToUse) {
        if(itemToUse.getQuantity()>= quantity){
            transactionList.add(new Transaction(
                    TransactionType.USE,
                    itemToUse.getId(),
                    itemToUse.getTypeId(),
                    itemToUse.getLocationId(),
                    itemToUse.getLocationId(),
                    itemToUse.getQuantity(),
                    itemToUse.getQuantity()- quantity,
                    SLib.getUserDetails().getId(),
                    new Date(),
                    itemToUse.getExpDate()
            ));
            itemToUse.setQuantity(itemToUse.getQuantity()- quantity);
            quantity = 0;
            if(itemToUse.getQuantity()>0)
                SLib.getItems().add(itemToUse);
        }else {
            transactionList.add(new Transaction(
                    TransactionType.USE,
                    itemToUse.getId(),
                    itemToUse.getTypeId(),
                    itemToUse.getLocationId(),
                    itemToUse.getLocationId(),
                    itemToUse.getQuantity(),
                    0L,
                    SLib.getUserDetails().getId(),
                    new Date(),
                    itemToUse.getExpDate()
            ));
            quantity = quantity - itemToUse.getQuantity();
        }
        return quantity;
    }
}