package pl.sevet.myapplication;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import pl.sevet.myapplication.utils.SLib;

import java.util.ArrayList;
import java.util.List;

public class LocationEmployeeAccess extends AppCompatActivity {

    TableLayout accessTable;
    Spinner employeeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_emplyee_access);
        accessTable = findViewById(R.id.accessTable);
        employeeSpinner = findViewById(R.id.employeeSpinner);
        List<String> employeesNames = new ArrayList<>();
        SLib.getUsers().forEach(x->{
            if(!x.isAdmin())
                employeesNames.add(x.getName()+" "+x.getSurname());
        });
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(getBaseContext(),R.layout.spinner_item,employeesNames);
        employeeSpinner.setAdapter(namesAdapter);

        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SLib.getUsers().forEach(x->{
                    if((x.getName() + " " + x.getSurname()).equals(employeeSpinner.getSelectedItem().toString()))
                        SLib.setSelectedUser(x);
                });

                setAccessTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAccessTable() {
        accessTable.removeAllViews();
        accessTable.addView(employeeSpinner);
        if(!SLib.getUsersAccess().containsKey(SLib.getSelectedUser().getId())){
            return;
        }
        SLib.getLocations().forEach(x->{
            if(!x.getId().equals(-1L)){
                TableRow locationRow = new TableRow(this);
                locationRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                locationRow.setPadding(10,40,10,40);

                TextView locationName = new TextView(this.getBaseContext());
                locationName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                locationName.setTextSize(20);
                locationName.setText(x.getName());

                CheckBox haveAccess = new CheckBox(this);
                haveAccess.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                haveAccess.setChecked(SLib.getUsersAccess().get(SLib.getSelectedUser().getId()).contains(x.getId()));
                haveAccess.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked){
                        SLib.getUsersAccess().get(SLib.getSelectedUser().getId()).add(x.getId());

                    }else{
                        SLib.getUsersAccess().get(SLib.getSelectedUser().getId()).remove(x.getId());
                    }
                });

                locationRow.addView(locationName);
                locationRow.addView(haveAccess);

                accessTable.addView(locationRow);
            }
        });

    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }
}