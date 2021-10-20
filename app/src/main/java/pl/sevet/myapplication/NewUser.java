package pl.sevet.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import pl.sevet.myapplication.model.Mode;
import pl.sevet.myapplication.model.users.User;
import pl.sevet.myapplication.model.users.UserParams;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.util.ArrayList;
import java.util.List;

public class NewUser extends AppCompatActivity {
    EditText login;
    EditText password;
    EditText name;
    EditText surname;
    CheckBox isAdmin;
    TableLayout userTable;
    final List<TableRow> loginRows = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        isAdmin = findViewById(R.id.isAdmin);
        userTable = findViewById(R.id.userTable);
        loginRows.add(findViewById(R.id.logRow1));
        loginRows.add(findViewById(R.id.logRow2));
        loginRows.add(findViewById(R.id.logRow3));
        loginRows.add(findViewById(R.id.logRow4));
        SLib.setRequestApi(new RequestApi(this));
        if(SLib.getMode()==Mode.EDIT){
            loginRows.forEach(x->userTable.removeView(x));
            name.setText(SLib.getSelectedUser().getName());
            surname.setText(SLib.getSelectedUser().getSurname());
            isAdmin.setChecked(SLib.getSelectedUser().isAdmin());
        }
    }

    public void createAcc(View view) {
        UserParams params = null;

        User details = new User(name.getText().toString(),surname.getText().toString(),isAdmin.isChecked());
        if(SLib.getMode() == Mode.NEW) {
            params = new UserParams(login.getText().toString(), password.getText().toString());
            createAcc(params, details);
        }else if(SLib.getMode() == Mode.EDIT) {
            details.setId(SLib.getSelectedUser().getId());
            updateAcc(details);
            SLib.getUsers().remove(SLib.getSelectedUser());
        }


    }

    private void updateAcc(User details){
        SLib.getRequestApi().setRequestURL(Constants.UPDATE_USER_DESC);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(details);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                SLib.getUsers().add(details);
                setResult(RESULT_OK);
                finish();
            }
            @Override
            public void onError() {
                String x =SLib.getRequestApi().getVolleyResponseError();
            }
        });

    }

    private void createAcc(UserParams params, User details) {
        SLib.getRequestApi().setRequestURL(Constants.CREATE_USER);
        SLib.getRequestApi().setMethod(Request.Method.POST);
        SLib.getRequestApi().setObjectParameter(params);
        SLib.getRequestApi().requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                details.setId(Long.parseLong(SLib.getRequestApi().getResponseString()));
                updateAcc(details);
            }
            @Override
            public void onError() {

            }
        });
    }
}