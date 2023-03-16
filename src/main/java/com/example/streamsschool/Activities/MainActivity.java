package com.example.streamsschool.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.streamsschool.R;
import com.example.streamsschool.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String userName, password;
    boolean isSignUp = false;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.signup.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        //Listener for Login button,  Calling ActivityHome
        activityMainBinding.login.setOnClickListener(view -> {
            userName = activityMainBinding.userId.getText().toString();
            password = activityMainBinding.userPwd.getText().toString();

            SharedPreferences s = getSharedPreferences( "demo",  MODE_PRIVATE);
            String val1 = s.getString("id", null);
            String val2 = s.getString("pwd",null);

            if( !isSignUp){ //Login
                if( userName.equals(val1) && password.equals(val2) )
                    startActivity(new Intent(MainActivity.this, ActivityHome.class));
                else
                    Toast.makeText(this, "Incorrect details", Toast.LENGTH_SHORT).show();
            }
            else {   //Signup
                SharedPreferences sharedPreferences = getSharedPreferences( "demo" ,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("id", userName);
                editor.putString("pwd", password);
                editor.apply();
            }
        });

        //Listener for signup text
        activityMainBinding.signup.setOnClickListener(view -> {
            if (!isSignUp) {  //login
                isSignUp = true;
                activityMainBinding.login.setText(R.string.sign_up);
                activityMainBinding.signup.setText(R.string.login);
            } else {  //signup
                isSignUp = false;
                activityMainBinding.login.setText(R.string.login);
                activityMainBinding.signup.setText(R.string.sign_up);
            }
        });
    }
}