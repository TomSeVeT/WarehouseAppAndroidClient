package pl.sevet.myapplication;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import pl.sevet.myapplication.model.Mode;
import pl.sevet.myapplication.model.items.ItemGroup;
import pl.sevet.myapplication.model.items.ItemType;
import pl.sevet.myapplication.model.items.Measurement;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NewItemGroup extends AppCompatActivity {

    EditText name;
    Spinner measurement;
    CheckBox haveExpDate;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_group);
        name = findViewById(R.id.itemGroupName);
        measurement = findViewById(R.id.measurementSpinner);
        haveExpDate = findViewById(R.id.haveExpDateCb);
        saveButton = findViewById(R.id.saveGroupBtn);

        List<String> measurementList = Stream.of(Measurement.values())
                                            .map(Measurement::name)
                                            .collect(Collectors.toList());
        ArrayAdapter<String> groupListAdapter =
                new ArrayAdapter<>(this, R.layout.spinner_item, measurementList);
        measurement.setAdapter(groupListAdapter);

        if(SLib.getMode() == Mode.EDIT){
            name.setText(SLib.getSelectedItemGroup().getName());
            measurement.setSelection(SLib.getSelectedItemGroup().getMeasurement().getValue());
            haveExpDate.setChecked(SLib.getSelectedItemGroup().isExpDate());
        }
    }

    public void createItemGroup(View view) {
        saveButton.setEnabled(false);
        Measurement tmpMsrmnt;
        if(measurement.getSelectedItem().toString().equals(Measurement.PIECES.toString()))
            tmpMsrmnt = Measurement.PIECES;
        else
            tmpMsrmnt = Measurement.AMOUNT;

        ItemGroup itemGroup = new ItemGroup(name.getText().toString(), tmpMsrmnt, haveExpDate.isChecked());
        if(SLib.getMode() == Mode.EDIT){
            itemGroup.setId(SLib.getSelectedItemGroup().getId());
            SLib.getItemGroups().remove(SLib.getSelectedItemGroup());
        }
        createOrUpdateItemGroup(itemGroup);
    }

    public void createOrUpdateItemGroup(ItemGroup itemGroup){
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.CREATE_ITEM_GROUP);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(itemGroup);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                if(SLib.getMode() == Mode.NEW){
                    itemGroup.setId(Long.parseLong(SLib.getRequestApi().getResponseString()));
                }else{
                    SLib.getItemGroups().removeIf(x-> x.getId().equals(itemGroup.getId()));
                }
                SLib.getItemGroups().add(itemGroup);
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