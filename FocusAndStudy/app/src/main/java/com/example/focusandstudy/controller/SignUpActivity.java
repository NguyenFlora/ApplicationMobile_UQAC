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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText mSignUp_EditText_Nom;
    private EditText mSignUp_EditText_Email;
    private EditText mSignUp_EditText_MotDePasse;
    private Button mSignUp_Button_Sinscrire;
    private static final String ACCOUNT_CREATED = "ACCOUNT_CREATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUp_EditText_Nom = (EditText) findViewById(R.id.SignUp_EditText_Nom);
        mSignUp_EditText_Email = (EditText) findViewById(R.id.SignUp_EditText_Email);
        mSignUp_EditText_MotDePasse = (EditText) findViewById(R.id.SignUp_EditText_MotDePasse);
        mSignUp_Button_Sinscrire = (Button) findViewById(R.id.SignUp_Button_Sinscrire);

        mSignUp_Button_Sinscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mSignUp_EditText_Nom.getText().toString();
                String email = mSignUp_EditText_Email.getText().toString();
                String password = mSignUp_EditText_MotDePasse.getText().toString();

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
                    User mUser = new User(username, email, password);
                    UserDB userDB = new UserDB(SignUpActivity.this);
                    boolean success = userDB.updateDB(mUser);
                    System.out.println(mUser);
                    System.out.println(success);
                    if(success) goToFirstScreenActivity();
                }
            //}
        });
    }

    private void goToFirstScreenActivity() {
        Intent firstScreenActivity = new Intent(SignUpActivity.this, FirstScreenActivity.class);
        firstScreenActivity.putExtra(ACCOUNT_CREATED, true);
        startActivity(firstScreenActivity);
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
