package com.example.app.ai_uas.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ai_uas.R;
import com.example.app.ai_uas.SearchActivity;
import com.example.app.ai_uas.model.Book;
import com.example.app.ai_uas.recyclerview.BookAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private MaterialButton btnSearchByScan;
    private MaterialButton btnDetectText;
    private TextView textTranslate;
    private Bitmap imageBitmap;
    private ImageView imageView;

    private String currentImgPath;

    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private List<Book> mdata;

    private FirebaseFirestore db;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        btnSearchByScan =view.findViewById( R.id.home_btn_scan );


        initBooks(view);
        initMDataBooks();

//        btnSearchByScan.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setupTempFile();
//            }
//        } );

        btnSearchByScan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity2Intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(activity2Intent);
            }
        } );

        TextInputEditText searchBar = view.findViewById(R.id.home_txt_search);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void setupTempFile() {
        String fileName = "photoforai";
        File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            File imgFile = File.createTempFile(fileName, ".jpg", storageDirectory);

            currentImgPath = imgFile.getAbsolutePath();
            Uri imgUri = FileProvider.getUriForFile(getActivity(), "com.example.app.ai_uas.fileprovider", imgFile);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            this.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    static final int REQUEST_IMAGE_CAPTURE = 199;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data.getData()!=null) {
            Toast.makeText(getActivity(), currentImgPath,LENGTH_LONG).show();
            Bitmap img = BitmapFactory.decodeFile(currentImgPath);
            imageView.setImageBitmap( img );
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

    public void processTxt(FirebaseVisionText text) {

        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(getActivity(), "No Text :(", LENGTH_LONG).show();
            return;
        }
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            String txt = block.getText();
//            IKI GAWE NGEMUNCULNO TULISAN E
            textTranslate.setTextSize(18);
            textTranslate.setText(txt);
        }
    }


    private void initBooks(View view){
        rvBooks = view.findViewById(R.id.home_rv_books);
        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBooks.setHasFixedSize(true);
    }
    private void initMDataBooks() {


        mdata = new ArrayList<>();
        bookAdapter = new BookAdapter(mdata);
        rvBooks.setAdapter(bookAdapter);
        db = FirebaseFirestore.getInstance();
        db.collection("books").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

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
                        Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
    }

}


