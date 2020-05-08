package com.example.app.ai_uas.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.ai_uas.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    Button Signoutbutton;
    TextView Usernametv, FirstNametv, MiddleNametv, LastNametv, Facultytv, Yeartv;

    public ProfileFragment() {
        // Required empty public constructor
        String Username, FirstName, MiddleName, LastName, Faculty, Year;
        Usernametv = FindViewById
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
