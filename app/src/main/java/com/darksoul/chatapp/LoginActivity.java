package com.darksoul.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.darksoul.chatapp.Models.UserModel;
import com.darksoul.chatapp.Network.Api.UserAuthApi;
import com.darksoul.chatapp.Network.RetrofitClient;
import com.darksoul.chatapp.SessionManager.Sessions;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private TextView viewSignup;
    private Button loginBtn;
    private AppCompatEditText Email, Password;
    private CheckBox remember;
    private UserAuthApi ApiAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init Api
        Retrofit retrofit = RetrofitClient.getInstance();
        ApiAuth = retrofit.create(UserAuthApi.class);


        //view init...........
        viewSignup = findViewById(R.id.login_signup);
        loginBtn = findViewById(R.id.btn_login);
        Email = findViewById(R.id.login_email);
        Password = findViewById(R.id.login_password);
        remember= findViewById(R.id.cb_rememberme);


        //remember me works.......
        Sessions sessions=new Sessions(LoginActivity.this,Sessions.SESSION_REMEMBER);
        if(sessions.checkRememberMe()){
         HashMap<String,String>data=sessions.getRememberMeFromSession();
         Email.setText(data.get(Sessions.KEY_USERNAME));
         Password.setText(data.get(Sessions.KEY_PASSWORD));
        }


        //login button click listener.................
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String pass = Password.getText().toString();
                if(remember.isChecked()){
                    RememberME(email,pass);
                    UserAuthantication(email, pass);
                }else{
                    UserAuthantication(email, pass);
                }

            }
        });
        //signup text view listener........
        viewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void UserAuthantication(String email, String pass) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", pass);
        Call<UserModel> call = ApiAuth.loginUser(map);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.code()==200) {
                    UserModel data = response.body();
                    Sessions sessions=new Sessions(LoginActivity.this,Sessions.SESSION_USER);
                    sessions.createLoginSession(data.getName(),data.getEmail(),data.getPhone(),data.getUserID(),data.getCountry(),data.getCountryCode(),data.getGender(),data.getStatus());
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else if(response.code()==400){
                    Toast.makeText(getApplicationContext(), "Wrong Password !!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "User Not Exists !!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void RememberME(String email, String pass) {
        Sessions sessions=new Sessions(LoginActivity.this,Sessions.SESSION_REMEMBER);
        sessions.createRememberMeSession(email,pass);
    }
}