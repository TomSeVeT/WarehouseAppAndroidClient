package pl.sevet.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.sevet.myapplication.model.Role;
import pl.sevet.myapplication.model.users.User;
import pl.sevet.myapplication.model.users.UserParams;
import pl.sevet.myapplication.utils.Constants;
import pl.sevet.myapplication.utils.RequestApi;
import pl.sevet.myapplication.utils.SLib;
import pl.sevet.myapplication.utils.VolleyCallBack;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    private UserParams userParams;
    private RequestApi requestApi;

    EditText loginField;
    EditText passwordField;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Crazy Bubble Magazyn");
        loginField = findViewById(R.id.editTextLogin);
        passwordField = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        requestApi = new RequestApi(this);
    }

    public void buttonOnClick(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        progressBar.setVisibility(View.VISIBLE);
        userParams = new UserParams(loginField.getText().toString(),passwordField.getText().toString());
        requestApi.setRequestURL(Constants.LOGIN);
        requestApi.setObjectParameter(userParams);
        requestApi.setMethod(Request.Method.POST);
        requestApi.requestWithObject(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                try {
                    SLib.setUserDetails(new ObjectMapper().readValue(requestApi.getResponseString(),User.class));
                    SLib.setUserParams(userParams);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
                loginSuccess();
            }
            @Override
            public void onError() {
                progressBar.setVisibility(View.INVISIBLE);
                loginFailed();
            }
        });

    }
    public void loginSuccess(){
        String message = "Witaj "+SLib.getUserDetails().getName()+" "+SLib.getUserDetails().getSurname();
        Toast toast = Toast.makeText(this, message,Toast.LENGTH_SHORT);
        toast.show();
        Intent intent;
        if(SLib.getRole()== Role.ADMIN)
            intent = new Intent(this,MainAdminMenu.class);
        else
            intent = new Intent(this,UserMainMenu.class);
        startActivity(intent);

    }
    public void loginFailed(){
        Toast toast = Toast.makeText(this, "Nie udało się zalogować",Toast.LENGTH_SHORT);
        toast.show();
    }
}