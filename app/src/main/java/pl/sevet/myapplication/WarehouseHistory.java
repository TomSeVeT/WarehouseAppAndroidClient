package pl.sevet.myapplication;

import android.app.DatePickerDialog;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.tabs.TabLayout;
import pl.sevet.myapplication.model.history.HistoryItem;
import pl.sevet.myapplication.utils.SLib;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WarehouseHistory extends AppCompatActivity {

    Spinner locationSpinner;
    TabLayout tabLayout;
    ConstraintLayout layout;
    TableLayout table;
    Date historyDate;
    TextView historyDateLabel;
    Map<Long, Float> typeIdQuantity;
    Long selectedLocationId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_history);
        locationSpinner = findViewById(R.id.locationHistorySpinner);
        tabLayout = findViewById(R.id.historyTabLayout);
        layout = findViewById(R.id.historyView);
        table = findViewById(R.id.historyTable);
        historyDateLabel = findViewById(R.id.historyDateView);
        typeIdQuantity = new HashMap<>();

        historyDate = new Date();
        historyDateLabel.setText("Dane wg: "+new SimpleDateFormat("dd.MM.yyyy").format(historyDate));

        List<String> locationsNames = new ArrayList<>();
        locationsNames.add("Wszystkie lokacje");
        SLib.getLocations().forEach(location->locationsNames.add(location.getName()));

        filterTransactions();
        setHistoryTable();

        ArrayAdapter<String> dataAdapter1 =
                new ArrayAdapter<>(locationSpinner.getContext(),
                        R.layout.spinner_item, locationsNames);
        locationSpinner.setAdapter(dataAdapter1);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    selectedLocationId = null;
                else{
                    SLib.getLocations().forEach(x-> {
                        if(locationSpinner.getSelectedItem().toString().equals(x.getName()))
                            selectedLocationId = x.getId();
                    });
                }
                filterTransactions();
                setHistoryTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterTransactions();
                setHistoryTable();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setHistoryTable() {

        table.removeAllViews();
        typeIdQuantity.forEach((k,v)->{
            TableRow typeRow = new TableRow(this);
            typeRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeRow.setPadding(10,40,10,40);

            TextView nameLabel = new TextView(this);
            nameLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            nameLabel.setText(SLib.getItemTypes().stream().filter(x->x.getId().equals(k)).findFirst().get().getName());
            nameLabel.setTextSize(20);

            TextView quantityLabel = new TextView(this);
            quantityLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            quantityLabel.setText(String.format("%.0f",v));
            quantityLabel.setTextSize(20);

            typeRow.addView(nameLabel);
            typeRow.addView(quantityLabel);
            table.addView(typeRow);
        });
    }

    public void changeHistoryDate(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {
            historyDate = new GregorianCalendar(year,month,dayOfMonth).getTime();
            historyDateLabel.setText("Dane wg: "+new SimpleDateFormat("dd.MM.yyyy").format(historyDate));
            filterTransactions();
            setHistoryTable();
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dialog = new DatePickerDialog(this,
                android.R.style.Theme_DeviceDefault_Dialog,
                dateSetListener,year,month,day);
        dialog.show();
    }

    private void filterTransactions() {
        SimpleDateFormat formater;
        typeIdQuantity.clear();
        SLib.getItemTypes().forEach(x->typeIdQuantity.put(x.getId(), (float) 0));

        switch (tabLayout.getSelectedTabPosition()){
            case 0: // dzien
                formater = new SimpleDateFormat("yyyyMMdd");
                SLib.getTransactionHistory().forEach(x->{
                    x.getTransactions()
                            .stream().
                            filter(y-> formater.format(y.getTransactionDate()).equals(formater.format(historyDate))
                                    && (selectedLocationId == null || selectedLocationId.equals(y.getLocIdAfter())))
                            .forEach(y->{
                                Float quantityDiff = y.getQuantityBefore() - y.getQuantityAfter();
                                typeIdQuantity.put(x.getItemTypeId(), typeIdQuantity.get(x.getItemTypeId())+quantityDiff);
                            });
                });
                break;
            case 1: // miesiac
                formater = new SimpleDateFormat("yyyyMM");
                SLib.getTransactionHistory().forEach(x->{
                    x.getTransactions()
                            .stream().
                            filter(y-> formater.format(y.getTransactionDate()).equals(formater.format(historyDate))
                                    && (selectedLocationId == null || selectedLocationId.equals(y.getLocIdAfter())))
                            .forEach(y->{
                                Float quantityDiff = y.getQuantityBefore() - y.getQuantityAfter();
                                typeIdQuantity.put(x.getItemTypeId(), typeIdQuantity.get(x.getItemTypeId())+quantityDiff);
                            });
                });
                break;
            case 2: // rok
                formater = new SimpleDateFormat("yyyy");
                SLib.getTransactionHistory().forEach(x->{
                    x.getTransactions()
                            .stream().
                            filter(y-> formater.format(y.getTransactionDate()).equals(formater.format(historyDate))
                                    && (selectedLocationId == null || selectedLocationId.equals(y.getLocIdAfter())))
                            .forEach(y->{
                                Float quantityDiff = y.getQuantityBefore() - y.getQuantityAfter();
                                typeIdQuantity.put(x.getItemTypeId(), typeIdQuantity.get(x.getItemTypeId())+quantityDiff);
                            });
                });
                break;
            case 3: // wszystko
                SLib.getTransactionHistory().forEach(x->{
                    x.getTransactions()
                            .stream()
                            .filter(y-> selectedLocationId == null || selectedLocationId.equals(y.getLocIdAfter()))
                            .forEach(y->{
                                Float quantityDiff = y.getQuantityBefore() - y.getQuantityAfter();
                                typeIdQuantity.put(x.getItemTypeId(), typeIdQuantity.get(x.getItemTypeId())+quantityDiff);
                            });
                });
                break;
        }
    }
}