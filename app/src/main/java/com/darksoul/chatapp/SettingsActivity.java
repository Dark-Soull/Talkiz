package com.darksoul.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout Account,Chats,Notifications,Storage,Help,Invite,SProfile;
    private ImageView Back;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        //view init........
        Account=findViewById(R.id.account_setting);
        Chats=findViewById(R.id.chat_setting);
        Notifications=findViewById(R.id.notification_setting);
        Storage=findViewById(R.id.storage_setting);
        Help=findViewById(R.id.help_setting);
        Invite=findViewById(R.id.invite);
        SProfile=findViewById(R.id.Setting_profile);
        Back=findViewById(R.id.settings_back);
        
        
        //all linear layout click events..............................
        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, Account_Settings_Activity.class);
                startActivity(i);
            }
        });

        Notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, Notifications_Settings_Activity.class);
                startActivity(i);
            }
        });

        Chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, Chat_Settings_Activity.class);
                startActivity(i);
            }
        });

        Storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, Storage_settings_Activity.class);
                startActivity(i);
            }
        });
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, Help_Activity.class);
                startActivity(i);
            }
        });

        Invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Invite key", Toast.LENGTH_SHORT).show();
            }
        });
        
        SProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Profile Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}