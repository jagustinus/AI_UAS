package com.example.app.ai_uas.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.app.ai_uas.R;
import com.example.app.ai_uas.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.bookViewHolder> implements Filterable {

    List<Book> mData;
    List<Book> mDataFiltered;

    public BookAdapter(List<Book> mData) {
        this.mData = mData;
        this.mDataFiltered = mData;
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
        Book book = mDataFiltered.get(position);

        Glide.with(holder.itemView.getContext())
                .load(mDataFiltered.get(position).getThumbnailUrl())
                .transform(new CenterCrop(), new RoundedCorners(16))
                .into(holder.imgBook);



        holder.title.setText(book.getTitle());
        holder.author.setText(book.getTitle());
        holder.pages.setText(String.valueOf(book.getPageCount()));
        holder.published.setText(book.getIsbn());


    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if(key.isEmpty()){
                    mDataFiltered = mData;
                } else{
                    List<Book> lstFiltered = new ArrayList<>();
                    for (Book row : mData){
                        if(row.getTitle().toLowerCase().contains(key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }

                    mDataFiltered = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (List<Book>) results.values;
                notifyDataSetChanged();
            }
        };
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

