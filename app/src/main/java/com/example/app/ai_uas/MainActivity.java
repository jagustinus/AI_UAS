package com.example.app.ai_uas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.ai_uas.fragment.BookFragment;
import com.example.app.ai_uas.fragment.HistoryFragment;
import com.example.app.ai_uas.fragment.HomeFragment;
import com.example.app.ai_uas.fragment.ProfileFragment;
import com.example.app.ai_uas.model.Book;
import com.example.app.ai_uas.recyclerview.BookAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.appToolBar);

        botNav = findViewById(R.id.bottom_nav_container);
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.home_menu_item_bottom_nav:
                        toolbar.setTitle("Home");
                        setSupportActionBar(toolbar);
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.book_menu_item_bottom_nav:
                        toolbar.setTitle("Book");
                        setSupportActionBar(toolbar);
                        fragment = new BookFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.history_menu_item_bottom_nav:
                        toolbar.setTitle("History");
                        setSupportActionBar(toolbar);
                        fragment = new HistoryFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.profile_menu_item_bottom_nav:
                        toolbar.setTitle("Profile");
                        setSupportActionBar(toolbar);
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_app_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        botNav.setSelectedItemId(R.id.home_menu_item_bottom_nav);
    }

}
