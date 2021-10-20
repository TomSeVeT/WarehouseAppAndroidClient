package pl.sevet.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import pl.sevet.myapplication.model.Mode;
import pl.sevet.myapplication.model.users.User;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

public class Users extends AppCompatActivity {
    TableLayout tableLayout;
    TableRow headerRow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        tableLayout = (TableLayout) findViewById(R.id.usersTable);
        headerRow = findViewById(R.id.headerRow);
        addUsersToTable();
    }

    private void addUsersToTable() {
        for (User user :
                SLib.getUsers()) {
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
                    SLib.setSelectedUser(user);
                }
                else{
                    tableLayout.removeAllViews();
                    tableLayout.addView(headerRow);
                    SLib.setSelectedUser(null);
                    addUsersToTable();
                }
            });

            TextView lastName = new TextView(this);
            lastName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            lastName.setText(user.getSurname());
            lastName.setTextSize(20);

            TextView name = new TextView(this);
            name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            name.setText(user.getName());
            name.setTextSize(20);

            TextView role = new TextView(this);
            role.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            if(user.isAdmin())
                role.setText("Admin");
            else
                role.setText("Pracownik");
            role.setTextSize(20);

            row.addView(lastName);
            row.addView(name);
            row.addView(role);
            tableLayout.addView(row);
        }
    }



    public void newUserClick(View view) {
        SLib.setMode(Mode.NEW);
        Intent intent = new Intent(this,NewUser.class);
        startActivityForResult(intent,1);
    }

    public void editUserClick(View view) {
        if(SLib.getSelectedUser()==null)
            return;
        SLib.setMode(Mode.EDIT);
        Intent intent = new Intent(this,NewUser.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        tableLayout.removeAllViews();
        tableLayout.addView(headerRow);
        addUsersToTable();
    }

    public void deleteUser(View view) {
        if(SLib.getSelectedUser()== null)
            return;
        SLib.setRequestApi(new RequestApi(this));
        SLib.getRequestApi().setRequestURL(Constants.DELETE_USER);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(SLib.getSelectedUser());
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                tableLayout.removeAllViews();
                SLib.getUsers().remove(SLib.getSelectedUser());
                addUsersToTable();
            }
            @Override
            public void onError() {

            }
        });

    }
}