package com.darksoul.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.darksoul.chatapp.SessionManager.Sessions;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    private TextView Name,Country,Gender,Status,Email,Phone;
    private String stat;
    private LinearLayout Profile_Buttons,Profile_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        stat=getIntent().getStringExtra("stat");
        //view init.......
        initialization();

    }

    private void initialization() {
        //views init...........
        Name=findViewById(R.id.profile_name);
        Country=findViewById(R.id.profile_country);
        Gender=findViewById(R.id.profile_gender);
        Status=findViewById(R.id.profile_status);
        Email=findViewById(R.id.profile_email);
        Phone=findViewById(R.id.profile_phone);
        Profile_Buttons=findViewById(R.id.profile_buttons);
        Profile_edit=findViewById(R.id.profile_buttons2);

        if(stat.equals("0")){
            Profile_Buttons.setVisibility(View.GONE);
            Profile_edit.setVisibility(View.VISIBLE);
        }else{
            Profile_Buttons.setVisibility(View.VISIBLE);
            Profile_edit.setVisibility(View.GONE);
        }

        //getting data from shareprep..........
        Sessions sessions=new Sessions(ProfileActivity.this,Sessions.SESSION_USER);
        HashMap<String,String> data=sessions.getUserDataFromSession();
        String PHONE="+"+data.get(Sessions.KEY_COUNTRYCODE) + "-" + data.get(Sessions.KEY_PHONE);
        Name.setText(data.get(Sessions.KEY_FULLNAME));
        Email.setText(data.get(Sessions.KEY_EMAIL));
        Phone.setText(PHONE);
        Country.setText(data.get(Sessions.KEY_COUNTRY));
        Gender.setText(data.get(Sessions.KEY_GENDER));
        Status.setText(data.get(Sessions.KEY_STATUS));
    }

}