package com.example.focusandstudy.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.focusandstudy.R;
import com.example.focusandstudy.model.database.DBHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText m_sign_up_edit_text_name;
    private EditText m_sign_up_edit_text_email;
    private EditText m_sign_up_edit_text_password;
    private Button m_sign_up_button;
    private ImageView m_sign_up_close;
    private TextView m_sign_up_text_display;
    private static final String ACCOUNT_CREATED = "ACCOUNT_CREATED";

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        m_sign_up_edit_text_name = (EditText) findViewById(R.id.sign_up_edit_text_name);
        m_sign_up_edit_text_email = (EditText) findViewById(R.id.sign_up_edit_text_email);
        m_sign_up_edit_text_password = (EditText) findViewById(R.id.sign_up_edit_text_password);
        m_sign_up_button = (Button) findViewById(R.id.sign_up_button);
        m_sign_up_close = (ImageView) findViewById(R.id.sign_up_close);
        m_sign_up_text_display = (TextView) findViewById(R.id.sign_up_text_display);

        dbHandler = new DBHandler(SignUpActivity.this);

        m_sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = m_sign_up_edit_text_name.getText().toString();
                String email = m_sign_up_edit_text_email.getText().toString();
                String password = m_sign_up_edit_text_password.getText().toString();

                /*if (username.length() == 0 || email.length() == 0 || password.length() == 0) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.ensureInput), Toast.LENGTH_SHORT).show();
                } else if (!checkMailRequirements(email)) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.ensureMailRequirements), Toast.LENGTH_SHORT).show();
                } else if (password.length() < 4 || password.length() > 30) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.ensurePasswordLength), Toast.LENGTH_SHORT).show();
                } else if (!checkPassWordRequirements(password)) {
                    Toast.makeText(SignUpActivity.this, getString(R.string.ensurePasswordRequirements), Toast.LENGTH_SHORT).show();
                } else {*/
                    // Create user account with infos
                    boolean ifAccountCreated = dbHandler.addNewUser(username, email, password);
                    System.out.println(ifAccountCreated);
                    Toast.makeText(SignUpActivity.this, getString(R.string.accountCreatedToast), Toast.LENGTH_SHORT).show();
                    if(ifAccountCreated) goToLoginActivity();
                }
            //}
        });

        m_sign_up_close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent signUpActivity = new Intent(SignUpActivity.this, LogInActivity.class);
                //setResult(Activity.RESULT_CANCELED,mainActivity);
                startActivity(signUpActivity);
                finish();
            }
        });

        m_sign_up_text_display.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                m_sign_up_edit_text_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        m_sign_up_edit_text_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }, 3000);   //3 seconds
            }
        });
    }

    private void goToLoginActivity() {
        Intent loginActivity = new Intent(SignUpActivity.this, LogInActivity.class);
        loginActivity.putExtra(ACCOUNT_CREATED, true);
        startActivity(loginActivity);
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
