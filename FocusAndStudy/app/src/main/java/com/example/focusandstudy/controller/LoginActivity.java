package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.UserDB;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText mLogIn_EditText_Email;
    private EditText mLogIn_EditText_MotDePasse;
    private Button mLogIn_Button_SeConnecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
            startActivity(new Intent(LoginActivity.this, FirstScreenActivity.class));
        }

        setContentView(R.layout.activity_login);

        mLogIn_EditText_Email = (EditText) findViewById(R.id.LogIn_EditText_Email);
        mLogIn_EditText_MotDePasse = (EditText) findViewById(R.id.LogIn_EditText_MotDePasse);
        mLogIn_Button_SeConnecter = (Button) findViewById(R.id.LogIn_Button_SeConnecter);

        UserDB userDB = new UserDB(LoginActivity.this);

        mLogIn_Button_SeConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mLogIn_EditText_Email.getText().toString();
                String password = mLogIn_EditText_MotDePasse.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, getString(R.string.ensureInput), Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (!checkMailRequirements(email)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.ensureMailRequirements), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!checkPassWordRequirements(password)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.ensurePasswordRequirements), Toast.LENGTH_SHORT).show();
                    return;
                }*/

                else {
                    System.out.println("vla");
                    ArrayList<String> allMails = userDB.getMails();
                    System.out.println(allMails);
                    if (allMails.contains(email) && userDB.getPasswordForEmail(email).equals(password)){
                        System.out.println("connexion");
                        goToMainActivity();
                    }
                }
            }
        });
    }

    private void goToMainActivity() {
        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        //mainActivity.putExtra(ACCOUNT_CREATED, true);
        startActivity(mainActivity);
        finish();
    }
    private Boolean checkPassWordRequirements(String password) {
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,20}$");
        Matcher m = p.matcher(password);
        return m.find();
    }

    private Boolean checkMailRequirements(String mail) {
        Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mail);
        return m.find();
    }
}
