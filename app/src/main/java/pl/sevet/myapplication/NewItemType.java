package pl.sevet.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import pl.sevet.myapplication.model.Mode;
import pl.sevet.myapplication.model.items.ItemGroup;
import pl.sevet.myapplication.model.items.ItemType;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.util.ArrayList;
import java.util.List;

public class NewItemType extends AppCompatActivity {

    EditText name;
    EditText description;
    Spinner group;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item_type);
        name = findViewById(R.id.itemTypeName);
        description = findViewById(R.id.itemTypeDescription);
        group = findViewById(R.id.itemGroupSpinner);
        saveButton = findViewById(R.id.saveTypeButton);
        List<String> groupList = new ArrayList<>();
        SLib.getItemGroups().forEach(x-> groupList.add(x.getName()));
        ArrayAdapter<String> groupListAdapter =
                new ArrayAdapter<>(this, R.layout.spinner_item, groupList);
        group.setAdapter(groupListAdapter);

        if(SLib.getMode() == Mode.EDIT){
            name.setText(SLib.getSelectedItemType().getName());
            description.setText(SLib.getSelectedItemType().getDescription());
            String groupName = null;
            for (ItemGroup itemGroup:
                    SLib.getItemGroups()){
                if(SLib.getSelectedItemType().getGroupId().equals(itemGroup.getId()))
                    groupName = itemGroup.getName();
            }
            for(int i=0; i< groupList.size(); i++)
                if(group.getItemAtPosition(i).toString().equals(groupName))
                    group.setSelection(i);
        }
    }

    public void createItemType(View view) {
        saveButton.setEnabled(false);
        Long groupId = SLib.getItemGroups().stream()
                .filter(x-> x.getName().equals(group.getSelectedItem().toString())).findFirst().get().getId();
        ItemType itemType = new ItemType( groupId, name.getText().toString(), description.getText().toString() );
        if(SLib.getMode() == Mode.EDIT){
            itemType.setId(SLib.getSelectedItemType().getId());
            SLib.getItemTypes().remove(SLib.getSelectedItemType());
        }
        createOrUpdateType(itemType);
    }

    private void createOrUpdateType(ItemType itemType) {
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.CREATE_TYPE);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(itemType);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                if(SLib.getMode() == Mode.NEW){
                    itemType.setId(Long.parseLong(SLib.getRequestApi().getResponseString()));

                }else{
                    SLib.getItemTypes().removeIf(x-> x.getId().equals(itemType.getId()));
                }
                if(itemType.getDescription()== null || itemType.getDescription().equals("")
                || itemType.getDescription().substring(0,7).equals("Grupa: ")){
                    itemType.setDescription(
                            "Grupa: "+
                                    SLib.getItemGroups()
                                            .stream()
                                            .filter(x->x.getId().equals(itemType.getGroupId()))
                                            .findFirst()
                                            .get()
                                            .getName());
                }
                SLib.getItemTypes().add(itemType);
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