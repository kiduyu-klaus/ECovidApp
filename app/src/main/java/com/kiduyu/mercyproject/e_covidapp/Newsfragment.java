package com.kiduyu.mercyproject.e_covidapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Newsfragment extends Fragment {
    private NewsAdapter news_adapter;
    private ArrayList<News> newslist= new ArrayList<>();
    ProgressDialog progressDialog;
    TextView newsTime;
    String news_date;
    String date;
    String about;
    ImageView prevDay;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);


        RecyclerView recycler = view.findViewById(R.id.recyclerview_news_fragment);
        news_adapter= new NewsAdapter(getActivity(),newslist);
        recycler.setAdapter(news_adapter);
        newsTime=view.findViewById(R.id.news_time);
        prevDay=view.findViewById(R.id.previous_day);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        recycler.setLayoutManager(layoutManager);
        recycler.setFocusable(false);
        Content content = new Content();
        content.execute();
        return view;

    }

    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            //bindAudioService();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prevDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm =getActivity().getSupportFragmentManager();
                    Bundle arguments = new Bundle();
                    arguments.putString("booklink","yesterday");
                    Newsfragment readerCheckBookDetailFragment = new Newsfragment();
                    readerCheckBookDetailFragment.setArguments(arguments);
                    fm.beginTransaction().addToBackStack(null).replace(R.id.fragment_container,readerCheckBookDetailFragment).commit();

                }
            });
            newsTime.setText("News For :"+news_date);
            progressDialog.dismiss();
            news_adapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            String BASE_URL = getArguments().getString("booklink");

            if (BASE_URL.equals("today")) {

                try {
                    String bookurl = "https://www.kenyans.co.ke/coronavirus";

                    String url = bookurl;

                    Document doc = Jsoup.connect(url).get();

                    Elements data = doc.getElementsByClass("item-list news-updates");


                    Elements news = data.first().select("ul").select("li.update-row");

                    news_date = data.first().select("h3").select("time")
                            .text();


                    int size = news.size();
                    Log.d("items", "complete" + news_date);

                    for (int i = 0; i < size; i++) {

                        date = news
                                .select("div.updates-body")
                                .select("span.updates-time")
                                .eq(i)
                                .text();
                        Log.d("items", "coverurl" + date);

                        String about = news
                                .select("div.updates-body")
                                .select("span.updates-body")
                                .eq(i)
                                .text();
                        Log.d("items", "coverurl" + about);


                        // String name = getArguments().getString("name");
                        newslist.add(new News(about, date));


                        //mObjects.add(new Audio(detailtitle+" part: "+i,audiourl));
                        // Log.d("items", "Image url: " + coverurl+" Image url part: " + i);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String bookurl = "https://www.kenyans.co.ke/coronavirus";

                    String url = bookurl;

                    Document doc = Jsoup.connect(url).get();

                    Elements data = doc.getElementsByClass("item-list news-updates").eq(1);


                    Elements news = data.select("ul").select("li")
                            .select("div.updates-body");

                    if (news.isEmpty()){
                        Log.d("items", "isempty" );
                    }

                    news_date = data.select("h3").select("time")
                            .text();


                    int size = news.size();
                    Log.d("items", "complete" + size);

                    for (int i = 0; i < size; i++) {

                        date = news
                                .select("div.updates-body")
                                .select("span.updates-time")
                                .eq(i)
                                .text();
                        if (date.isEmpty()){
                            Log.d("items", "Dataempty"+"none");
                        } else {
                            Log.d("items", "coverurl" + date);

                            about = news
                                    .select("div.updates-body")
                                    .select("span.updates-body")
                                    .eq(i)
                                    .text();
                            Log.d("items", "coverurl" + about);
                        }

                        // String name = getArguments().getString("name");
                        newslist.add(new News(about, date));


                        //mObjects.add(new Audio(detailtitle+" part: "+i,audiourl));
                        // Log.d("items", "Image url: " + coverurl+" Image url part: " + i);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }
    }


    }
