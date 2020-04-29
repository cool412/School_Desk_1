package com.example.schooldesk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class DashboardActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity mActivity;
    private Context mContext;
    // We have defined Object of SharedPreClass class.
    // Which will help to read all the preferences we have stored during the session.
    @SuppressLint("StaticFieldLeak")
    private static SharedPrefClass sharedPrefClass;

    // Declare the DrawerLayout object as global.
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext = getApplicationContext();
        mActivity = DashboardActivity.this;
        // Initialize the object of SharedPrefClass.
        sharedPrefClass = new SharedPrefClass(this);

        // We have already removed toolbar from the activity.
        // so, we have to all extra toolbar for activity.
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // We will populate the navigation view.
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initialize the drawer layout.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawable);
        // Here in below R.string argument is for blind people.
        // Those strings are not going to display anywhere but will help blind people for navigation.
        mActionBarDrawerToggle = new ActionBarDrawerToggle(DashboardActivity.this,
                mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        // Here if statement is required because the code inside if statement will load dashboard fragment in all the cases
        // whenever the fragment is destroyed. But it used to destroy in case of memory leak, screen rotate.
        // So, avoid UI level bug we have put if statement.
        if (savedInstanceState == null){
            //Below code is to display dashboard fragment by default.
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DashboardFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DashboardFragment()).commit();
                break;
            case R.id.nav_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventsFragment()).commit();
                break;
            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ScheduleFragment()).commit();
                break;
            case R.id.nav_exam:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ExamFragment()).commit();
                break;
            case R.id.nav_result:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ResultFragment()).commit();
                break;
            case R.id.menu_message:
                Toast.makeText(this,"SMS!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                userLogOut();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void userLogOut(){
        // When you logout from the application, it will reset all the sharedpreferences to default.
        // Here I have considered "User" as dafault user.
        // Session Id, I would like keep it blank whenever it is not used.
        sharedPrefClass.writeUserName("User");
        sharedPrefClass.writeLoginStatus(false);
        sharedPrefClass.writeSessionId("");
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Here if statement will stop to destroy the activity when drawer is open and back button is pressed.
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // the below else statement is required to exit the application right from this activity,
            // but it will ask user for confirmation in form of dialog box before killing application.
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("Please confirm");
            builder.setMessage("Are you want to exit the app?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Do something when user want to exit the app
                    // Let allow the system to handle the event, such as exit the app
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Do something when want to stay in the app
                    Toast.makeText(mContext, "thank you", Toast.LENGTH_LONG).show();
                }
            });
            // Create the alert dialog using alert dialog builder
            AlertDialog dialog = builder.create();
            // Finally, display the dialog when user press back button
            dialog.show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
