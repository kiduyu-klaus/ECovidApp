package com.yearfour.mercyproject.e_covidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    Context mcontext;
    private List<News> newsList;

    public NewsAdapter(Context context, List<News> newsList1) {
        this.newsList = newsList1;
        this.mcontext = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());

        //Glide.with(mcontext).load(news.getImage()).into(holder.cover);


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.news_news);
            description = view.findViewById(R.id.news_date);

        }

    }
}
