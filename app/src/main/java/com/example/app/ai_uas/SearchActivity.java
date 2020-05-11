package com.example.app.ai_uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ai_uas.model.Book;
import com.example.app.ai_uas.recyclerview.BookAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class SearchActivity extends AppCompatActivity {
    private String txt;
    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private List<Book> mdata;
    private FirebaseFirestore db;
    private MaterialButton btnSearchByScan;
    private MaterialButton btnDetectText;
    private TextView textTranslate;
    private Bitmap imageBitmap;
    private ImageView imageView;

    private static final int PICK_IMAGE = 99;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search );
        textTranslate = findViewById( R.id.result_text_scanned );
        btnSearchByScan = findViewById( R.id.home_btn_scan );
        btnDetectText = findViewById( R.id.home_btn_detect );
        imageView = findViewById( R.id.camResult );
        btnSearchByScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        findViewById(R.id.home_btn_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goGallery = new Intent();
                goGallery.setType("image/*");
                goGallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(goGallery, "Select Picture"), PICK_IMAGE);
            }
        });

        btnDetectText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            detectTxt();
            }
        } );
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get( "data" );
            imageView.setImageBitmap( imageBitmap );
        } else if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(imageBitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void detectTxt() {

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer Detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        Detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processTxt(firebaseVisionText);
            }
        });

    }

    public void processTxt(FirebaseVisionText text) {
        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(SearchActivity.this, "No Text :(", LENGTH_LONG).show();
            return;
        }
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            String txt = block.getText();
//            IKI GAWE NGEMUNCULNO TULISAN E
//            textTranslate.setTextSize(18);
            textTranslate.setText(txt);
            rvBooks = findViewById(R.id.rv_scan_result);
            rvBooks.setLayoutManager(new LinearLayoutManager( this));
            rvBooks.setHasFixedSize(true);

            mdata = new ArrayList<>();
            bookAdapter = new BookAdapter(mdata);
            rvBooks.setAdapter(bookAdapter);

            db = FirebaseFirestore.getInstance();
            Toast.makeText(this, "Firebase",LENGTH_LONG).show();
            db.collection("books").whereEqualTo("title",txt).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.d("Debug: ", "null");
                    if(!queryDocumentSnapshots.isEmpty()){

                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot d : list){

                            Book p = d.toObject(Book.class);
                            mdata.add(p);

                        }

                        bookAdapter.notifyDataSetChanged();

                    }
                }

            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e.getMessage() != null){
                                Log.d("Debug : ", e.getMessage());
                            }
                        }

                    });

        }
    }

    public void daftarBuku(){
//        rvBooks = findViewById(R.id.rv_buku);
//        rvBooks.setLayoutManager(new LinearLayoutManager( this));
//        rvBooks.setHasFixedSize(true);
//
//        db = FirebaseFirestore.getInstance();
//        db.collection("books").whereEqualTo("title",txt).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                if(!queryDocumentSnapshots.isEmpty()){
//
//                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//
//                    for(DocumentSnapshot d : list){
//
//                        Book p = d.toObject(Book.class);
//                        mdata.add(p);
//
//                    }
//
//                    bookAdapter.notifyDataSetChanged();
//
//                }
//            }
//
//        })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                    }
//
//                });



    }
}

