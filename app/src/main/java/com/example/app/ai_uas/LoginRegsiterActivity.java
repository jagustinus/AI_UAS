package com.example.app.ai_uas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

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
                String loginusername = usernamelogin.getText().toString().trim();
                String passwordusername = passwordlogin.getText().toString().trim();

                if(TextUtils.isEmpty(loginusername)){
                    usernamelogin.setError("Username required");
                    return;
                }
                if(TextUtils.isEmpty(passwordusername)){
                    passwordlogin.setError("PasswordRequired")
                    return;
                }

                progressBarlogin.setVisibility(View.VISIBLE);
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //register
                String firstname = firstnamereg.getText().toString().trim();
                String middlename = middlenamereg.getText().toString().trim();
                String lastname = lastnamereg.getText().toString().trim();
                String faculty = facultyreg.getText().toString().trim();
                String year = yearreg.getText().toString().trim();
                String addusername = usernamereg.getText().toString().trim();
                String addpassword = passwordreg.getText().toString().trim();

                if(TextUtils.isEmpty(firstname)){
                    firstnamereg.setError("First Name Required");
                    return;
                }
                if(TextUtils.isEmpty(faculty)) {
                    facultyreg.setError("Faculty Required");
                    return;
                }
                if(TextUtils.isEmpty(year)){
                    facultyreg.setError("Year Required");
                    return;
                }
                if(TextUtils.isEmpty(addusername)){
                    usernamereg.setError("Username Required");
                    return;
                }
                if(TextUtils.isEmpty(addpassword)){
                    passwordreg.setError("Password Required");
                    return;
                }

                progressBarregister.setVisibility(View.VISIBLE);

                Map<String, Object> user = new HashMap<>();
                user.put("firstname", firstname);
                user.put("middlename", middlename);
                user.put("lastname", lastname);
                user.put("faculty", faculty);
                user.put("angkatan", lastname);


            }
        });
    }
}
