package com.kiduyu.mercyproject.e_covidapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class CountyFragments extends Fragment implements StatewiseAdapter.OnItemClickListner  {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    ProgressDialog progressDialog;
    public static int confirmation = 0;
    private StatewiseAdapter statewiseAdapter;
    private ArrayList<StatewiseModel> statewiseModelArrayList;
    public static String testValue;
    public static boolean isRefreshed;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.county_fragment, container, false);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Per Country Data");
        recyclerView = view.findViewById(R.id.county_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.countywise_refresh);
        search = view.findViewById(R.id.search_editText_county);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        statewiseModelArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getActivity());
        showProgressDialog();
        extractData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                extractData();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Data refreshed!", Toast.LENGTH_SHORT).show();
            }
        });



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return view;

    }

    private void extractData() {

        String[] arr={"Mombasa", "Kwale", "Kilifi", "Tana River", "Lamu",
                "Taita–Taveta", "Garissa", "Wajir", "Mandera", "Marsabit",
                "Isiolo", "Meru", "Tharaka-Nithi", "Embu", "Kitui",
                "Machakos", "Makueni", "Nyandarua", "Nyeri", "Kirinyaga",
                "Murang'a", "Turkana", "West Pokot", "Samburu", "Trans-Nzoia",
                "Uasin Gishu", "Elgeyo-Marakwet", "Nandi", "Baringo", "Laikipia",
                "Nakuru", "Narok", "Kajiado", "Bomet", "Kakamega",
                "Vihiga", "Bungoma", "Busia", "Siaya", "Kisumu",
                "Homa Bay", "Migori", "Kisii", "Nyamira", "Nairobi"};
// iterating over an array
        for (int i = 0; i < arr.length; i++) {
            Log.d("TAG", "extractData: "+arr.length);
            String y=arr[i];
            Random r = new Random();
            int low = 100;
            int high = 200;
            int result = r.nextInt(high-low) + low;
            String confirmed = String.valueOf(result);
            statewiseModelArrayList.add(new StatewiseModel(y, confirmed, "", "", "", "", "","", ""));

        }
        progressDialog.cancel();
        statewiseAdapter = new StatewiseAdapter(getActivity(), statewiseModelArrayList);
        recyclerView.setAdapter(statewiseAdapter);

    }

    private void filter(String text) {


    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (confirmation != 1) {
                    progressDialog.cancel();
                    Toast.makeText(getActivity(), "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);
    }

    @Override
    public void onItemClick(int position) {
        StatewiseModel clickedItem = statewiseModelArrayList.get(position);
        Toast.makeText(getActivity(), clickedItem.getState(), Toast.LENGTH_SHORT).show();

    }
}
