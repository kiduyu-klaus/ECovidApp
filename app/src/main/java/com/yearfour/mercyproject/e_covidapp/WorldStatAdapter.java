package com.yearfour.mercyproject.e_covidapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;

public class WorldStatAdapter extends RecyclerView.Adapter<WorldStatAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<WorldStatModel> arrayList;
    private WorldStatAdapter.OnItemClickListner mListner;
    private static final String COUNTRY_NAME = "country";
    private static final String COUNTRY_CONFIRMED = "cases";
    private static final String COUNTRY_ACTIVE = "active";
    private static final String COUNTRY_DECEASED = "deaths";
    private static final String COUNTRY_NEW_CONFIRMED = "todayCases";
    private static final String COUNTRY_TESTS = "tests";
    private static final String COUNTRY_NEW_DECEASED = "todayDeaths";
    private static final String COUNTRY_FLAGURL = "flag";
    private static final String COUNTRY_RECOVERED = "recovered";
    public static final String COUNTRY_NEW_RECOVERED = "todayRecovered";

    public interface OnItemClickListner {
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        mListner = listner;
    }


    public WorldStatAdapter(Context context, ArrayList<WorldStatModel> countrywiseModelArrayList) {
        mContext = context;
        arrayList = countrywiseModelArrayList;
    }

    @NonNull
    @Override
    public WorldStatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.worldstat_list_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WorldStatAdapter.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorldStatModel clickedItem = arrayList.get(position);
                Intent perCountryIntent = new Intent(mContext, PerStateData.class);

                perCountryIntent.putExtra(COUNTRY_NAME, clickedItem.getCountry());
                perCountryIntent.putExtra(COUNTRY_CONFIRMED, clickedItem.getConfirmed());
                perCountryIntent.putExtra(COUNTRY_ACTIVE, clickedItem.getActive());
                perCountryIntent.putExtra(COUNTRY_RECOVERED, clickedItem.getRecovered());
                perCountryIntent.putExtra(COUNTRY_DECEASED, clickedItem.getDeceased());
                perCountryIntent.putExtra(COUNTRY_NEW_CONFIRMED, clickedItem.getNewConfirmed());
                perCountryIntent.putExtra(COUNTRY_NEW_DECEASED, clickedItem.getNewDeceased());
                perCountryIntent.putExtra(COUNTRY_TESTS, clickedItem.getTests());
                perCountryIntent.putExtra(COUNTRY_FLAGURL, clickedItem.getFlag());
                perCountryIntent.putExtra(COUNTRY_NEW_RECOVERED, clickedItem.getNewRecovered());


                mContext.startActivity(perCountryIntent);
            }
        });

        WorldStatModel currentItem = arrayList.get(position);
        String countryName = currentItem.getCountry();
        String countryTotal = currentItem.getConfirmed();
        String countryFlag = currentItem.getFlag();
        String countryRank = String.valueOf(position+1);
        int countryTotalInt = Integer.parseInt(countryTotal);
        holder.rankTextView.setText(countryRank+".");
        holder.countryTotalCases.setText(NumberFormat.getInstance().format(countryTotalInt));
        holder.countryName.setText(countryName);
        Glide.with(mContext).load(countryFlag).into(holder.flagImage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filterList(ArrayList<WorldStatModel> filteredList) {
        arrayList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView countryName, countryTotalCases, rankTextView;
        ImageView flagImage;

        public ViewHolder(View itemView) {
            super(itemView);

            countryName = itemView.findViewById(R.id.country_name_textview);
            countryTotalCases = itemView.findViewById(R.id.country_confirmed_textview);
            flagImage = itemView.findViewById(R.id.flag_image);
            rankTextView = itemView.findViewById(R.id.country_rank);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
