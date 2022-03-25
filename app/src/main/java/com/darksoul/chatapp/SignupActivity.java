package com.darksoul.chatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.darksoul.chatapp.Network.Api.UserAuthApi;
import com.darksoul.chatapp.Network.RetrofitClient;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Observable;
import java.util.function.Consumer;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {

    private TextView viewLogin;
    private AppCompatEditText FullName, Email, Password,Phone,cPassword;
    private Spinner Gender,Status;
    private Button Register;
    private UserAuthApi ApiAuth;
    private CountryCodePicker countryCodePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //init Api
        Retrofit retrofit = RetrofitClient.getInstance();
        ApiAuth = retrofit.create(UserAuthApi.class);

        //view init................
        viewLogin = findViewById(R.id.reg_signin);
        FullName = findViewById(R.id.regis_name);
        Email = findViewById(R.id.regis_email);
        Phone=findViewById(R.id.regis_phone);
        Password = findViewById(R.id.regis_password);
        cPassword=findViewById(R.id.regis_cpassword);
        Gender=findViewById(R.id.gender);
        Status=findViewById(R.id.status_spinner);
        countryCodePicker=findViewById(R.id.country_code);

        //setup status spinner...............
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this,R.array.Status, R.layout.spinner_layout);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Status.setAdapter(adapterStatus);

        //setup gender spinner...............
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Gender, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);


        Register = findViewById(R.id.button_register);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = FullName.getText().toString();
                String email = Email.getText().toString();
                String pass = Password.getText().toString();
                String phone=Phone.getText().toString();
                String gender=Gender.getSelectedItem().toString();
                String status=Status.getSelectedItem().toString();
                String countryCode=countryCodePicker.getSelectedCountryCode().toString();
                String country=countryCodePicker.getSelectedCountryName().toString();
                RegisterUser(email, name, pass, phone, gender, status, country, countryCode);
            }
        });


        viewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    private void RegisterUser(String email, String name, String pass, String phone, String gender, String statuss, String country, String countryCode) {

        if(Validation()){
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("name", name);
            map.put("password", pass);
            map.put("phone", phone);
            map.put("gender",gender);
            map.put("statuss",statuss);
            map.put("country",country);
            map.put("countryCode",countryCode);
            Call<String> call = ApiAuth.registerUser(map);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String result = response.body();
                    Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                    Log.e("retrofit", result);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private boolean Validation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.]+\\.+[a-z]+";
        if(FullName.length()==0 || !FullName.getText().toString().matches("^[A-Za-z]+\\ [A-Za-z]+$")){
            FullName.setError("Please Enter FirstName & LastName");
            FullName.requestFocus();
            return false;
        }else if(Email.length()==0 || !Email.getText().toString().matches(emailPattern)){
            Email.setError("Please Enter Email");
            Email.requestFocus();
            return false;
        }else if(Password.length()<=6){
            Password.setError("Password must contain more than 6 characters");
            Password.requestFocus();
            return false;
        }else if(!Password.getText().toString().equals(cPassword.getText().toString())){
            cPassword.setError("Password Didn't Matched");
            cPassword.requestFocus();
            return false;
        }else if(Phone.length()!=10){
            Phone.setError("Please Enter Correct Phone Number");
            Phone.requestFocus();
            return false;
        }else{
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}