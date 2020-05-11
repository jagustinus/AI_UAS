package com.example.app.ai_uas.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ai_uas.R;
import com.example.app.ai_uas.model.Book;
import com.example.app.ai_uas.recyclerview.BookAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {
    private RecyclerView rvBooks;
    private BookAdapter bookAdapter;
    private List<Book> mdata;

    private FirebaseFirestore db;

    public BookFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        initBooks(view);
        initMDataBooks();


        return view;
    }
    private void initBooks(View view){
        rvBooks = view.findViewById(R.id.book_rv_books);
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
