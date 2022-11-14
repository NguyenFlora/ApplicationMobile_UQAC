package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.focusandstudy.R;
import java.util.Collections;


public class FirstScreenActivity extends AppCompatActivity {
    private Button mFirstScreen_Button_Inscription;
    private Button mFirstScreen_Button_Connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        mFirstScreen_Button_Inscription = (Button) findViewById(R.id.FirstScreen_Button_Inscription);
        mFirstScreen_Button_Connexion = (Button) findViewById(R.id.FirstScreen_Button_Connexion);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mFirstScreen_Button_Inscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent signUpActivity = new Intent(FirstScreenActivity.this, SignUpActivity.class);
                //setResult(Activity.RESULT_CANCELED,mainActivity);
                startActivity(signUpActivity);
                finish();
                System.out.println("incriotion");
            }
        });

        mFirstScreen_Button_Connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent loginActivity = new Intent(FirstScreenActivity.this, LoginActivity.class);
                //setResult(Activity.RESULT_CANCELED,loginActivity);
                startActivity(loginActivity);
                finish();
            }
        });
    }
}