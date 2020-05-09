package com.example.app.ai_uas.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.ai_uas.LoginRegsiterActivity;
import com.example.app.ai_uas.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    Button Signoutbutton;
    TextView Usernametv, Firstnametv, Middlenametv, Lastnametv, Facultytv, Yeartv;
    FirebaseFirestore database;
    FirebaseAuth firebaseAuth;
    /*String userID;

     */
    /*
    private String Username;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String Faculty;
    private String Year;

     */

    public ProfileFragment() {
        // Required empty public constructor

    }


    /*
    public String getUsername(){
        return Username;
    }
    public String getFirstName(){
        return FirstName;
    }
    public String getMiddleName(){
        return MiddleName;
    }
    public String getLastName(){
        return LastName;
    }
    public String getFaculty(){
        return Faculty;
    }
    public String getYear(){
        return Year;
    }

     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Usernametv = view.findViewById(R.id.myusername);
        Firstnametv = view.findViewById(R.id.myfirstname);
        Middlenametv = view.findViewById(R.id.mymiddlename);
        Lastnametv = view.findViewById(R.id.mylastname);
        Facultytv = view.findViewById(R.id.myfaculty);
        Yeartv = view.findViewById(R.id.tahunangkatan);
/*
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = database.collection("users").document(userID);

        documentReference.addSnapshotListener((Executor) this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Usernametv.setText(documentSnapshot.getString("username"));
                Firstnametv.setText(documentSnapshot.getString("first"));
                Middlenametv.setText(documentSnapshot.getString("middle"));

            }
        });
*/
    Signoutbutton = view.findViewById(R.id.logoutbutton);

        Signoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginRegsiterActivity.class);
                startActivity(intent);
            }
        });

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


}
