package pl.sevet.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import pl.sevet.myapplication.model.Mode;
import pl.sevet.myapplication.model.locations.Location;
import pl.sevet.myapplication.utils.SLib;

public class LocationsDictionary extends AppCompatActivity {

    TableLayout tableLayout;
    TableRow headerRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_dictionary);
        tableLayout = findViewById(R.id.locDictionaryTable);
        headerRow = findViewById(R.id.headerRowDictionary);
        addLocationsToTable();
    }

    private void addLocationsToTable() {
        for (Location location :
                SLib.getLocations()){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.setPadding(10,40,10,20);
            row.setOnClickListener(v -> {
                for(int i =0; i< tableLayout.getChildCount();i++){
                    tableLayout.getChildAt(i).setBackgroundColor(0x00000000);
                }
                row.setBackgroundColor(Color.CYAN);
                if(tableLayout.getChildCount()>2){
                    tableLayout.removeAllViews();
                    tableLayout.addView(headerRow);
                    tableLayout.addView(row);
                    SLib.setSelectedLocation(location);
                }
                else if(tableLayout.getChildCount()==2){
                    if(SLib.getSelectedLocation() == null){
                        tableLayout.removeAllViews();
                        tableLayout.addView(headerRow);
                        tableLayout.addView(row);
                        SLib.setSelectedLocation(location);
                    }else{
                        tableLayout.removeAllViews();
                        tableLayout.addView(headerRow);
                        SLib.setSelectedLocation(null);
                        addLocationsToTable();
                    }
                }
                else{
                    tableLayout.removeAllViews();
                    tableLayout.addView(headerRow);
                    SLib.setSelectedLocation(null);
                    addLocationsToTable();
                }
            });

            TextView locName = new TextView(this);
            locName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            locName.setText(location.getName());
            locName.setTextSize(20);

            TextView locDesc = new TextView(this);
            locDesc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            locDesc.setText(location.getDescription());
            locDesc.setTextSize(20);


            row.addView(locName);
            row.addView(locDesc);
            tableLayout.addView(row);
        }
    }


    public void newLocationClick(View view) {
        SLib.setMode(Mode.NEW);
        Intent intent = new Intent(this,NewLocation.class);
        startActivityForResult(intent,1);
    }

    public void editLocationClick(View view) {
        if(SLib.getSelectedLocation()==null)
            return;
        SLib.setMode(Mode.EDIT);
        Intent intent = new Intent(this,NewLocation.class);
        startActivityForResult(intent,1);
    }


    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        tableLayout.removeAllViews();
        tableLayout.addView(headerRow);
        addLocationsToTable();
        SLib.setSelectedLocation(null);
    }
}