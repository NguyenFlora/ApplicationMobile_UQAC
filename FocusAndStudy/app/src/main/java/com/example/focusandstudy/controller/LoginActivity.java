package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.User;
import com.example.focusandstudy.model.database.DBHandler;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText m_log_in_edit_text_email;
    private EditText m_log_in_edit_text_password;
    private Button m_log_in_button;
    private TextView m_registration_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_log_in_edit_text_email = (EditText) findViewById(R.id.log_in_edit_text_email);
        m_log_in_edit_text_password = (EditText) findViewById(R.id.log_in_edit_text_password);
        m_log_in_button = (Button) findViewById(R.id.log_in_button);
        m_registration_link = (TextView) findViewById(R.id.registration_link);
    }

    @Override
    protected void onStart() {
        super.onStart();

        DBHandler dbHandler = new DBHandler(LoginActivity.this);

        m_log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = m_log_in_edit_text_email.getText().toString();
                String password = m_log_in_edit_text_password.getText().toString();

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
                    ArrayList<String> allMails = dbHandler.getMails();
                    System.out.println(allMails);
                    if (allMails.contains(email) && dbHandler.getPasswordForEmail(email).equals(password)){
                        System.out.println("connexion");
                        goToMainActivity();
                    }
                }
            }
        });

        m_registration_link.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent signUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
                //setResult(Activity.RESULT_CANCELED,mainActivity);
                startActivity(signUpActivity);
                finish();
                System.out.println("incriotion");
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
