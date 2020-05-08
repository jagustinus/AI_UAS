package com.example.app.ai_uas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView botNav;

    Button btnSearchByScan;
    Button btnDetectText;
    private TextView textTranslate;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private List<Book> mdata;
    private Bitmap imageBitmap;
    private ImageView imageView;

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
        btnDetectText = findViewById( R.id.home_btn_scan );
        btnSearchByScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        btnDetectText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectTxt();
            }
        } );


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get( "data" );
            imageView.setImageBitmap( imageBitmap );
        }
    }

    private void setupBookAdapter(){
        bookAdapter = new BookAdapter(mdata);
        rvBooks.setAdapter(bookAdapter);
    }

    private void detectTxt() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer Detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        Detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processTxt(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void processTxt(FirebaseVisionText text) {
        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(MainActivity.this, "No Text :(", Toast.LENGTH_LONG).show();
            return;
        }
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            String txt = block.getText();
//            IKI GAWE NGEMUNCULNO TULISAN E
            textTranslate.setTextSize(18);
            textTranslate.setText(txt);
        }
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
