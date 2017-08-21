package com.example.admin.amazonrestcalls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Admin on 8/20/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    ArrayList<Book> books = new ArrayList<>();
    Context context;

    public BookAdapter(ArrayList<Book> books){
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.book_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(books.get(position).getTitle());
        holder.tvAuthor.setText(books.get(position).getAuthor());
        Glide.with(context).load(books.get(position).getPicture()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.ivPicture);
        //Log.d("ViewHolder", "onBindViewHolder: Glide Done");
    }

    @Override
    public int getItemCount() {
        if(books != null){
            return books.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor;
        ImageView ivPicture;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            ivPicture = (ImageView) itemView.findViewById(R.id.ivCover);
        }
    }
}
