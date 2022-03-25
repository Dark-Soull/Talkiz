package com.darksoul.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.darksoul.chatapp.Adapters.FragmentAdapter;
import com.darksoul.chatapp.SessionManager.Sessions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle toggleMenu;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabs;

    private FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //view init.............
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.tool);
        mdrawerLayout = findViewById(R.id.mdrawer);
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tab_layout);


        adapterViewPager = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        tabs.setupWithViewPager(viewPager);

        //toolbar work...........
        setSupportActionBar(toolbar);
        toggleMenu = new ActionBarDrawerToggle(this, mdrawerLayout, toolbar, R.string.toggle_open, R.string.toggle_close);
        mdrawerLayout.addDrawerListener(toggleMenu);
        toggleMenu.syncState();

        //menu event listener....
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                mdrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_profile:
                mdrawerLayout.closeDrawer(GravityCompat.START);
                Intent inttnt = new Intent(MainActivity.this, ProfileActivity.class);
                inttnt.putExtra("stat","0");
                startActivity(inttnt);
                break;
            case R.id.nav_settings:
                mdrawerLayout.closeDrawer(GravityCompat.START);
                Intent iN = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(iN);
                finish();
                break;
            case R.id.nav_logout:
                mdrawerLayout.closeDrawer(GravityCompat.START);
                Sessions sessions=new Sessions(MainActivity.this,Sessions.SESSION_USER);
                sessions.logOutUserSession();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.nav_donation:
                mdrawerLayout.closeDrawer(GravityCompat.START);
                Intent inttn = new Intent(MainActivity.this, DonationActivity.class);
                startActivity(inttn);
                finish();
                break;
            case R.id.nav_share:
                mdrawerLayout.closeDrawer(GravityCompat.START);
                Intent intt = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intt);
                finish();
                break;
            case R.id.nav_about:
                mdrawerLayout.closeDrawer(GravityCompat.START);
                Intent in = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(in);
                finish();
                break;
            case R.id.nav_rate:
                mdrawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                break;
        }
        return true;
    }
}