package com.example.schooldesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("login", MODE_PRIVATE);

        if (sp.getBoolean("login",false)){
            // TO DO...
        }else {
            // TO DO...
           // Intent intent = new Intent (this, LoginActivity.class);
           // startActivity(intent);
        }
    }
}
