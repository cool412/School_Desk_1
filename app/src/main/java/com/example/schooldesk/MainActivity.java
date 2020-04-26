package com.example.schooldesk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void signInClicked(View view) {
        EditText EditUserName = findViewById(R.id.login_user);
        EditText EditPassword = findViewById(R.id.login_password);
        String UserName = EditUserName.getText().toString().trim();
        String PassWord = EditPassword.getText().toString().trim();

        Login lg = new Login(this);
        lg.execute(UserName, PassWord);
        // This will finish the current activity and make sure that this activity does not run in background.
        finish();
    }
}
