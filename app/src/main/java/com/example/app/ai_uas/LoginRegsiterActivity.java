package com.example.app.ai_uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app.ai_uas.fragment.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginRegsiterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button loginbutton, registerbutton;
    //login
    EditText usernamelogin, passwordlogin;
    //register
    EditText firstnamereg, middlenamereg, lastnamereg, facultyreg, yearreg, usernamereg, passwordreg;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore database;
    ProgressBar progressBarlogin, progressBarregister;
    String userID;
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
        database = FirebaseFirestore.getInstance();

        database = FirebaseFirestore.getInstance();

        progressBarlogin = findViewById(R.id.loginprogressBar);
        progressBarregister = findViewById(R.id.registerprogressBar);


        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), ProfileFragment.class));
        }

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
                    passwordlogin.setError("PasswordRequired");
                    return;
                }

                progressBarlogin.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(loginusername, passwordusername).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginRegsiterActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), ProfileFragment.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginRegsiterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                });





            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //register
                final String firstname = firstnamereg.getText().toString().trim();
                final String middlename = middlenamereg.getText().toString().trim();
                final String lastname = lastnamereg.getText().toString().trim();
                final String faculty = facultyreg.getText().toString().trim();
                final String year = yearreg.getText().toString().trim();
                final String addusername = usernamereg.getText().toString().trim();
                final String addpassword = passwordreg.getText().toString().trim();

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

                firebaseAuth.createUserWithEmailAndPassword(addusername, addpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginRegsiterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            //userID = firebaseAuth.getCurrentUser().getUid();
                            //DocumentReference documentReference = database.collection("users").document(userID);
                            //Map<String, Object> user = new HashMap<>();
                            //user.put("first", firstname);
                            //user.put("middle", middlename);
                            //user.put("last", lastname);
                            //user.put("FIT", faculty);
                            //user.put("angkatan", year);
                            //user.put("username", addusername);
                            //user.put("password", addpassword);
                            //documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                //@Override
                                //public void onSuccess(Void aVoid) {
                                    //Log.d(TAG, "onSuccess: " + userID);
                                //}
                            //}).addOnFailureListener(new OnFailureListener() {
                                //@Override
                                //public void onFailure(@NonNull Exception e) {
                                    //Log.d(TAG, "onFailure: " + e.toString());
                                //}
                            //});
                            Intent intent = new Intent(getApplicationContext(), ProfileFragment.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(LoginRegsiterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                });

                //Refrensi : Youtube Small Academy




            }
        });
    }
}
