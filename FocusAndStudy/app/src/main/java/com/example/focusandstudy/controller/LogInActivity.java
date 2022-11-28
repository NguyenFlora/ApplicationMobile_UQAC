package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    private EditText m_log_in_edit_text_email;
    private EditText m_log_in_edit_text_password;
    private Button m_log_in_button;
    private TextView m_log_in_text_display;
    private TextView m_log_in_text_password_forgotten;
    private TextView m_registration_link;

    DBHandler dbHandler;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_log_in_edit_text_email = (EditText) findViewById(R.id.log_in_edit_text_email);
        m_log_in_edit_text_password = (EditText) findViewById(R.id.log_in_edit_text_password);
        m_log_in_button = (Button) findViewById(R.id.log_in_button);
        m_log_in_text_display = (TextView) findViewById(R.id.log_in_text_display);
        m_log_in_text_password_forgotten = (TextView) findViewById(R.id.log_in_text_password_forgotten);
        m_registration_link = (TextView) findViewById(R.id.registration_link);

        dbHandler = new DBHandler(LogInActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        m_log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = m_log_in_edit_text_email.getText().toString();
                String password = m_log_in_edit_text_password.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogInActivity.this, getString(R.string.ensureInput), Toast.LENGTH_SHORT).show();
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
                    currentUser = dbHandler.login(email, password);
                    if(currentUser == null){
                        Toast.makeText(getApplicationContext(), R.string.invalidCredentials,Toast.LENGTH_SHORT).show();
                    }else {
                        addLogInToSharedPreferences();
                        System.out.println("connexion");
                        goToMainActivity();
                    }
                }
            }
        });

        m_log_in_text_display.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                m_log_in_edit_text_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        m_log_in_edit_text_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }, 3000);   //3 seconds
            }
        });

        /*m_log_in_text_password_forgotten.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {


            }
        });
*/
        m_registration_link.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent signUpActivity = new Intent(LogInActivity.this, SignUpActivity.class);
                //setResult(Activity.RESULT_CANCELED,mainActivity);
                startActivity(signUpActivity);
                finish();
                System.out.println("incription");
            }
        });
    }
    private void addLogInToSharedPreferences(){
        SharedPreferences sharedPref = getSharedPreferences("UserManagement", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("logged_user_id", currentUser.getId());
        editor.putString("logged_username",currentUser.getUsername());
        editor.putBoolean("is_logged_in", true);
        editor.apply();
    }
    private void goToMainActivity() {
        Intent mainActivity = new Intent(LogInActivity.this, MainActivity.class);
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
