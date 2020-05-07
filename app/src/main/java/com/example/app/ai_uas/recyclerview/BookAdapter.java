package com.example.app.ai_uas.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.app.ai_uas.R;
import com.example.app.ai_uas.model.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.bookViewHolder> {

    List<Book> mData;

    public BookAdapter(List<Book> mData) {
        this.mData = mData;
    }


    @NonNull
    @Override
    public bookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new bookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bookViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(mData.get(position).getDrawableResources())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(holder.imgBook);


    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class bookViewHolder extends RecyclerView.ViewHolder{

        ImageView imgBook;
        TextView title, author, shordDesc, longDesc, pages, published;


        public bookViewHolder(@NonNull View itemView){
            super(itemView);

            imgBook = itemView.findViewById(R.id.book_img_book);
            title = itemView.findViewById(R.id.book_item_title);
            author = itemView.findViewById(R.id.book_item_author);
//            shordDesc = itemView.findViewById(R.id.book_item_p);
//            longDesc = itemView.findViewById(R.id.book_item_title);
            published = itemView.findViewById(R.id.book_item_page_published);
            pages = itemView.findViewById(R.id.book_item_page_count);

        }

    }

}
