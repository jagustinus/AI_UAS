package com.example.app.ai_uas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.app.ai_uas.fragment.BookFragment;
import com.example.app.ai_uas.fragment.HistoryFragment;
import com.example.app.ai_uas.fragment.HomeFragment;
import com.example.app.ai_uas.fragment.ProfileFragment;
import com.example.app.ai_uas.model.Book;
import com.example.app.ai_uas.recyclerview.BookAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView botNav;

    Button btnSearchByScan;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private List<Book> mdata;

    //Firebase
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.appToolBar);

        initBooks();
        initMDataBooks();
        setupBookAdapter();

        btnSearchByScan = findViewById(R.id.home_btn_scan);
//        btnSearchByScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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

    private void dispatchTakePictureIntent(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE); // Error?
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//    }

    private void setupBookAdapter(){
        bookAdapter = new BookAdapter(mdata);
        rvBooks.setAdapter(bookAdapter);
    }

    private void initMDataBooks(){
        mdata = new ArrayList<>();

//        fstore = FirebaseFirestore.getInstance();
//        DocumentReference documentReference = fstore.collection("books").document();
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//
//                Book book = new Book(documentSnapshot.getString("title"), documentSnapshot.getString("isbn"),
//                        documentSnapshot.getString("shortDescription"), documentSnapshot.getString("longDescription"),
//                        documentSnapshot.getString("thumbnailUrl"), ,
//                        Integer.parseInt(documentSnapshot.getString("pageCount")), documentSnapshot.getString(""))
//            }
//        });
//
//        mdata.add();
    }


    private void initBooks(){
        rvBooks = findViewById(R.id.main_rv_books);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        rvBooks.setHasFixedSize(true);
    }
}
