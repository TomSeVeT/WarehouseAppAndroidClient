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
import pl.sevet.myapplication.model.items.ItemType;
import pl.sevet.myapplication.utils.SLib;

public class ItemsDictionary extends AppCompatActivity {
    TableLayout tableLayout;
    TableRow headerRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_dicionary);
        tableLayout = findViewById(R.id.locDictionaryTable);
        headerRow = findViewById(R.id.headerRowDictionary);
        addItemTypesToTable();
    }

    private void addItemTypesToTable() {
        for (ItemType itemType :
                SLib.getItemTypes()){
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
                    SLib.setSelectedItemType(itemType);
                }
                else if(tableLayout.getChildCount()==2){
                    if(SLib.getSelectedItemType() == null){
                        tableLayout.removeAllViews();
                        tableLayout.addView(headerRow);
                        tableLayout.addView(row);
                        SLib.setSelectedItemType(itemType);
                    }else{
                        tableLayout.removeAllViews();
                        tableLayout.addView(headerRow);
                        SLib.setSelectedItemType(null);
                        addItemTypesToTable();
                    }
                }
                else{
                    tableLayout.removeAllViews();
                    tableLayout.addView(headerRow);
                    SLib.setSelectedItemType(null);
                    addItemTypesToTable();
                }
            });

            TextView typeName = new TextView(this);
            typeName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeName.setText(itemType.getName());
            typeName.setTextSize(20);

            TextView typeDesc = new TextView(this);
            typeDesc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            typeDesc.setText(itemType.getDescription());
            typeDesc.setTextSize(20);


            row.addView(typeName);
            row.addView(typeDesc);
            tableLayout.addView(row);
        }
    }

    public void newItemTypeClick(View view) {
        SLib.setMode(Mode.NEW);
        Intent intent = new Intent(this,NewItemType.class);
        startActivityForResult(intent,1);
    }

    public void editItemTypeClick(View view) {
        if(SLib.getSelectedItemType()==null)
            return;
        SLib.setMode(Mode.EDIT);
        Intent intent = new Intent(this,NewItemType.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        tableLayout.removeAllViews();
        tableLayout.addView(headerRow);
        addItemTypesToTable();
        SLib.setSelectedItemType(null)
        ;
    }

    public void deleteItemType(View view) {
        if(SLib.getSelectedItemType()==null)
            return;
        else
            return;

    }
}