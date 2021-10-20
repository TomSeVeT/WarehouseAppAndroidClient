package pl.sevet.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import pl.sevet.myapplication.model.Mode;
import pl.sevet.myapplication.model.items.ItemGroup;
import pl.sevet.myapplication.utils.SLib;

public class ItemGroupDictionary extends AppCompatActivity {

    TableLayout tableLayout;
    TableRow headerRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_group_dictionary);
        tableLayout = findViewById(R.id.itemGroupDictionaryTable);
        headerRow = findViewById(R.id.headerRowGroupDictionary);
        addItemGroupsToTable();
    }

    private void addItemGroupsToTable() {
        for (ItemGroup itemGroup :
                SLib.getItemGroups()){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.setPadding(10,40,10,20);
            row.setOnClickListener(v -> {
                for(int i=0; i< tableLayout.getChildCount(); i++)
                    tableLayout.getChildAt(i).setBackgroundColor(0x00000000);
                row.setBackgroundColor(Color.CYAN);
                if(tableLayout.getChildCount()>2){
                    tableLayout.removeAllViews();
                    tableLayout.addView(headerRow);
                    tableLayout.addView(row);
                    SLib.setSelectedItemGroup(itemGroup);
                }
                else if(tableLayout.getChildCount()==2){
                    if(SLib.getSelectedItemGroup() == null){
                        tableLayout.removeAllViews();
                        tableLayout.addView(headerRow);
                        tableLayout.addView(row);
                        SLib.setSelectedItemGroup(itemGroup);
                    }else{
                        tableLayout.removeAllViews();
                        tableLayout.addView(headerRow);
                        SLib.setSelectedItemGroup(null);
                        addItemGroupsToTable();
                    }
                }
                else{
                    tableLayout.removeAllViews();
                    tableLayout.addView(headerRow);
                    SLib.setSelectedItemGroup(null);
                    addItemGroupsToTable();
                }
            });

            TextView groupName = new TextView(this);
            groupName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            groupName.setText(itemGroup.getName());
            groupName.setTextSize(20);

            TextView groupDesc = new TextView(this);
            groupDesc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            groupDesc.setText("wymyslic co tu dac");
            groupDesc.setTextSize(20);

            row.addView(groupName);
            row.addView(groupDesc);
            tableLayout.addView(row);
        }
    }

    public void newItemGroupClick(View view) {
        SLib.setMode(Mode.NEW);
        Intent intent = new Intent(this, NewItemGroup.class);
        startActivityForResult(intent,1);
    }

    public void editItemGroupClick(View view) {
        if(SLib.getSelectedItemGroup() == null)
            return;
        SLib.setMode(Mode.EDIT);
        Intent intent = new Intent(this, NewItemGroup.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        tableLayout.removeAllViews();
        tableLayout.addView(headerRow);
        addItemGroupsToTable();
        SLib.setSelectedItemType(null)
        ;
    }

    public void deleteItemGroup(View view) {
        if(SLib.getSelectedItemGroup() == null)
            return;
        else
            return;
    }
}