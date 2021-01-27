package com.kiduyu.mercyproject.e_covidapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatewiseAdapter extends RecyclerView.Adapter<StatewiseAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<StatewiseModel> arrayList;
    private OnItemClickListner mListner;
    private static final String STATE_NAME = "stateName";
    private static final String STATE_CONFIRMED = "stateConfirmed";
    private static final String STATE_ACTIVE = "stateActive";
    private static final String STATE_DECEASED = "stateDeaceased";
    private static final String STATE_NEW_CONFIRMED = "stateNewConfirmed";
    private static final String STATE_NEW_RECOVERED = "stateNewRecovered";
    private static final String STATE_NEW_DECEASED = "stateNewDeceased";
    private static final String STATE_LAST_UPDATE = "stateLastUpdate";
    private static final String STATE_RECOVERED = "stateRecovered";

    public interface OnItemClickListner {
        void onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner) {
        mListner = listner;
    }

    public StatewiseAdapter(Context context, ArrayList<StatewiseModel> statewiseModelArrayList) {
        mContext = context;
        arrayList = statewiseModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.per_county_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final StatewiseModel currentItem = arrayList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Details for "+currentItem.getState()+" coming soon", Toast.LENGTH_SHORT).show();
            }
        });



        String stateName = currentItem.getState();
        String stateTotal = currentItem.getConfirmed();
        holder.stateTotalCases.setText(stateTotal);
        holder.stateName.setText(stateName);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filterList(ArrayList<StatewiseModel> filteredList) {
        arrayList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stateName, stateTotalCases;

        public ViewHolder(View itemView) {
            super(itemView);

            stateName = itemView.findViewById(R.id.district_name_textview);
            stateTotalCases = itemView.findViewById(R.id.district_confirmed_textview);

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
