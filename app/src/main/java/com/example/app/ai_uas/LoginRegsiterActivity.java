package com.example.app.ai_uas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class LoginRegsiterActivity extends AppCompatActivity {

    Button loginbutton, registerbutton;
    //login
    EditText usernamelogin, passwordlogin;
    //register
    EditText firstnamereg, middlenamereg, lastnamereg, facultyreg, yearreg, usernamereg, passwordreg;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBarlogin, progressBarregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_regsiter);

        loginbutton = findViewById(R.id.loginbutton);
        registerbutton = findViewById(R.id.registerbutton);
        //login
        usernamelogin = findViewById(R.id.loginusername);
        passwordlogin = findViewById(R.id.loginpassword);
        //register
        firstnamereg = findViewById(R.id.regfirstname);
        middlenamereg = findViewById(R.id.regmidname);
        lastnamereg = findViewById(R.id.reglastname);
        facultyreg = findViewById(R.id.regfik);
        yearreg = findViewById(R.id.regtahun);
        usernamereg = findViewById(R.id.regusername);
        passwordreg = findViewById(R.id.regpassword);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBarlogin = findViewById(R.id.loginprogressBar);
        progressBarregister = findViewById(R.id.registerprogressBar);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = firstnamereg.getText().toString().trim();
                
            }
        });
    }
}
