package com.example.app.ai_uas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Button btnSearchByScan;
    private Button btnDetectText;
    private TextView textTranslate;
    private Bitmap imageBitmap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.fragment_home );

        textTranslate = findViewById(R.id.home_text_translate);
        btnSearchByScan = findViewById(R.id.home_btn_scan);
        btnDetectText = findViewById( R.id.home_btn_detect );
        imageView = findViewById(R.id.imageView);

        btnSearchByScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                System.out.println("ASHIAP BANG ATTA");

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

    private void dispatchTakePictureIntent(){
        Intent takePicture = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE); // Error?
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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


    public void processTxt(FirebaseVisionText text) {

        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(SearchActivity.this, "No Text :(", Toast.LENGTH_LONG).show();
            return;
        }
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            String txt = block.getText();
//            IKI GAWE NGEMUNCULNO TULISAN E
            textTranslate.setTextSize(18);
            textTranslate.setText(txt);
        }
    }
}
