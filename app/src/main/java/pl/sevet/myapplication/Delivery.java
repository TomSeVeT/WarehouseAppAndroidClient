package pl.sevet.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import pl.sevet.myapplication.model.Transaction;
import pl.sevet.myapplication.model.TransactionType;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.util.*;

public class Delivery extends AppCompatActivity {

    TableLayout typesTable;
    Map<Integer,Map<Integer,Transaction>> transactionMap;
    int index;
    Map<Integer,Integer> indexMap;
    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        typesTable = findViewById(R.id.typesTable);
        saveButton = findViewById(R.id.saveDeliveryBtn);
        transactionMap = new HashMap<>();
        index= 0;
        indexMap = new HashMap<>();
    }

    public void addNewType(View view) {
        index++;
        indexMap.put(index,0);
        transactionMap.put(index, new HashMap<>());
        TableLayout positionLayout = new TableLayout(this);
        positionLayout.setId(index);
        positionLayout.setPadding(10,0,0,20);
        positionLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));
        positionLayout.setColumnStretchable(0,true);
        positionLayout.setColumnStretchable(1,false);

        TableRow typeRow = new TableRow(this);
        typeRow.setPadding(10,40,10,40);
        Spinner typeSpinner = new Spinner(this);
        typeSpinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        //TODO Move declarations here
        CheckBox dateQChkBox = new CheckBox(this);
        CheckBox sizeQChkBox = new CheckBox(this);

        List<String> typesList = new ArrayList<>();
        SLib.getItemTypes().forEach(x-> typesList.add(x.getName()));
        ArrayAdapter<String> typesListAdapter =
                new ArrayAdapter<>(this, R.layout.spinner_item, typesList);
        typeSpinner.setAdapter(typesListAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SLib.getItemTypes().forEach(x->{
                    if(x.getName().equals(parent.getSelectedItem().toString()))
                        if(transactionMap.get(positionLayout.getId())!=null)
                            transactionMap.get(( (View) typeSpinner.getParent().getParent() ).getId())
                                .forEach((k,v)->{
                                    v.setItemTypeId(x.getId());
                                    Long groupId = SLib.getItemTypes()
                                                        .stream().filter(z-> x.getId().equals(z.getId())).findFirst()
                                                        .get().getGroupId();
                                    boolean haveDate = SLib.getItemGroups().stream().filter(z-> groupId.equals(z.getId())).findFirst().get().isExpDate();
                                    dateQChkBox.setChecked(haveDate);
                                    });
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button deleteButton = new Button(this);
        deleteButton.setText("-");
        deleteButton.setTextSize(20);
        deleteButton.setOnClickListener(v -> {
            typesTable.removeView(positionLayout);
            transactionMap.remove(positionLayout.getId());
        });
        typeRow.addView(typeSpinner);
        typeRow.addView(deleteButton);

        TableRow quantityRow = new TableRow(this);
        quantityRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        quantityRow.setPadding(10,40,10,40);

        TextView quantityLabel = new TextView(this);
        quantityLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        quantityLabel.setText("Ilość");
        quantityLabel.setTextSize(25);

        Spinner quantitySpinner = new Spinner(this);
        List<String> numbers = new ArrayList<>();
        for (int i=1; i<=20; i++){
            numbers.add("   "+i+"   ");
        }
        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<>(this,
                        R.layout.spinner_item, numbers);
        quantitySpinner.setAdapter(dataAdapter);
        quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int size = transactionMap.get(positionLayout.getId()).size();
                int newSize = Integer.parseInt(quantitySpinner.getSelectedItem().toString().replaceAll("\\s+",""));
                int diff = newSize - size;
                if( diff > 0 )
                    for(int i =0; i<diff; i++){
                        indexMap.put(positionLayout.getId(),indexMap.get(positionLayout.getId()) + 1);
                        transactionMap.get(positionLayout.getId()).put(indexMap.get(positionLayout.getId()),new Transaction());

                        TableRow detailsRow = new TableRow(quantitySpinner.getContext());
                        detailsRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                        detailsRow.setPadding(10,40,10,40);
                        typeSpinner.setSelection((int) typeSpinner.getSelectedItemId());
                        transactionMap.get(positionLayout.getId()).forEach((k,v)->{
                            v.setItemTypeId(typeSpinner.getSelectedItemId()+1   );
                            transactionMap.get(positionLayout.getId()).put(k,v);
                        });


                        positionLayout.addView(detailsRow);
                    }
                else if(diff < 0) {
                    diff = -diff;
                    indexMap.put(positionLayout.getId(),indexMap.get(positionLayout.getId()) - diff);
                    transactionMap.get(positionLayout.getId()).entrySet().removeIf(entry-> entry.getKey()>indexMap.get(positionLayout.getId()));

                    positionLayout.removeViews(3 + newSize, diff);

                }
                dateQChkBox.setChecked(!dateQChkBox.isChecked());
                dateQChkBox.setChecked(!dateQChkBox.isChecked());
                dateQChkBox.callOnClick();

                sizeQChkBox.setChecked(!sizeQChkBox.isChecked());
                sizeQChkBox.setChecked(!sizeQChkBox.isChecked());
                sizeQChkBox.callOnClick();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        quantityRow.addView(quantityLabel);
        quantityRow.addView(quantitySpinner);

        TableRow sizeQRow = new TableRow(this);
        sizeQRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        sizeQRow.setPadding(10,40,10,40);

        TextView sizeQLabel = new TextView(this);
        sizeQLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        sizeQLabel.setText("Rozmiary pozycji?");
        sizeQLabel.setTextSize(20);


        sizeQChkBox.setChecked(false);
        sizeQChkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                for(int j=4; j< positionLayout.getChildCount(); j++){
                    Spinner quantitySpinner1 = new Spinner(positionLayout.getContext());
                    List<String> numbers1 = new ArrayList<>();
                    for (int i=1; i<=200; i++){
                        numbers1.add("   "+i+"   ");
                    }
                    ArrayAdapter<String> dataAdapter1 =
                            new ArrayAdapter<>(positionLayout.getContext(),
                                    R.layout.spinner_item, numbers1);
                    quantitySpinner1.setAdapter(dataAdapter1);
                    int finalJ = j;
                    quantitySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            transactionMap.get(positionLayout.getId()).get(finalJ -3).setAfterQuantity(
                                    Float.parseFloat(quantitySpinner1.getSelectedItem().toString().replaceAll("\\s+","")));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    ((TableRow)positionLayout.getChildAt(j)).addView(quantitySpinner1);
                }
            else
                for(int j = 4; j< positionLayout.getChildCount(); j++){

                    if(((TableRow)positionLayout.getChildAt(j)).getChildAt(0) instanceof Spinner)
                        ((TableRow)positionLayout.getChildAt(j)).removeViews(0,1);
                    if(((TableRow)positionLayout.getChildAt(j)).getChildCount()>1)
                        if(((TableRow)positionLayout.getChildAt(j)).getChildAt(1) instanceof Spinner)
                            ((TableRow)positionLayout.getChildAt(j)).removeViews(1,1);
                }
        });

        sizeQRow.addView(sizeQLabel);
        sizeQRow.addView(sizeQChkBox);


        TableRow dateQRow = new TableRow(this);
        dateQRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        dateQRow.setPadding(10,40,10,40);

        TextView dateQLabel = new TextView(this);
        dateQLabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        dateQLabel.setText("Daty przydatności?");
        dateQLabel.setTextSize(20);


        dateQChkBox.setChecked(false);
        dateQChkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                for(int j=4; j< positionLayout.getChildCount(); j++){
                    TextView datePicker = new TextView(positionLayout.getContext());
                    datePicker.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    datePicker.setTextSize(20);
                    datePicker.setText("Wybierz kolejną datę");

                    int finalJ = j;
                    DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, month, dayOfMonth) -> {

                        datePicker.setText(dayOfMonth+"."+(month+1)+"."+year);
                        transactionMap.get(positionLayout.getId()).get(finalJ -3).setExpDate(new GregorianCalendar(year,month,dayOfMonth).getTime());
                    };
                    datePicker.setOnClickListener(v -> {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);


                        DatePickerDialog dialog = new DatePickerDialog(positionLayout.getContext(),
                                                            android.R.style.Theme_DeviceDefault_Dialog,
                                                            dateSetListener,year,month,day);
                        dialog.show();
                    });


                    ((TableRow)positionLayout.getChildAt(j)).addView(datePicker);
                }
            else
                for(int j = 4; j< positionLayout.getChildCount(); j++){

                    if(((TableRow)positionLayout.getChildAt(j)).getChildAt(0) instanceof TextView)
                        ((TableRow)positionLayout.getChildAt(j)).removeViews(0,1);
                    if(((TableRow)positionLayout.getChildAt(j)).getChildCount()>1)
                        if(((TableRow)positionLayout.getChildAt(j)).getChildAt(1) instanceof TextView)
                            ((TableRow)positionLayout.getChildAt(j)).removeViews(1,1);
                }
        });

        dateQRow.addView(dateQLabel);
        dateQRow.addView(dateQChkBox);
        positionLayout.addView(typeRow);
        positionLayout.addView(quantityRow);
        positionLayout.addView(sizeQRow);
        positionLayout.addView(dateQRow);
        quantitySpinner.setSelection((int) quantitySpinner.getSelectedItemId());
        typesTable.addView(positionLayout);

    }

    public void sendTransaction(View view) {
        saveButton.setEnabled(false);
        if(transactionMap.isEmpty())
            return;
        List<Transaction> transactionList = new ArrayList<>();
        prepareTransactionList(transactionList);

        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.DELIVERY);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(transactionList);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError() {
                saveButton.setEnabled(true);
            }
        });

    }

    private void prepareTransactionList(List<Transaction> transactionList) {
        transactionMap.forEach((k,v)-> v.forEach((k2,v2)-> transactionList.add(v2)));
        Date lastDate = null;
        Long prevTypeId = -1L;
        for(Transaction transaction
                : transactionList){
            transaction.setTransactionType(TransactionType.CREATE);
            transaction.setBeforeLocationId(-1L);
            transaction.setAfterLocationId(0L);
            transaction.setBeforeQuantity(-1F);
            if(transaction.getAfterQuantity() == 0F) transaction.setAfterQuantity(1F);
            transaction.setUserId(SLib.getUserDetails().getId());
            transaction.setTransactionDate(Calendar.getInstance().getTime());
            if(transaction.getItemTypeId() == null)
                transaction.setItemTypeId(1L);

            if(transaction.getItemTypeId() == prevTypeId && transaction.getExpDate() == null)
                transaction.setExpDate(lastDate);
            lastDate = transaction.getExpDate();
            prevTypeId= transaction.getItemTypeId();
        }
    }
}