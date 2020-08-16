package com.kiduyu.mercyproject.e_covidapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.kiduyu.mercyproject.e_covidapp.Utils.ExtractData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Homefragment extends Fragment {
    String confirmed;
    String active;
    String date;
    String recovered;
    String deaths;
    String newConfirmed;
    String newDeaths;
    String newRecovered;
    String totalTests;
    String oldTests;
    int testsInt;
    String totalTestsCopy;
    public static int confirmation = 0;
    public static boolean isRefreshed;
    private long backPressTime;
    private Toast backToast;
    private RequestQueue requestQueue;

    TextView textView_confirmed, textView_confirmed_new, textView_recovered, textView_recovered_new, textView_death, textView_death_new, textView_tests, textView_date, textView_tests_new, textview_time;
    TextView textView_active;
    TextView textView_tiime;
    ProgressDialog progressDialog;
    ExtractData extractData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        textView_confirmed = view.findViewById(R.id.confirmed_textView);
        textView_confirmed_new = view.findViewById(R.id.confirmed_new_textView);

//        extractData = new ExtractData(getActivity()) ;
        requestQueue = Volley.newRequestQueue(getActivity());
        textView_recovered = view.findViewById(R.id.recovered_textView);
        textView_tiime = view.findViewById(R.id.time_textView);
        textView_recovered_new = view.findViewById(R.id.recovered_new_textView);
        textView_death = view.findViewById(R.id.death_textView);
        textView_death_new = view.findViewById(R.id.death_new_textView);
        textView_tests = view.findViewById(R.id.tests_textView);
        textView_date = view.findViewById(R.id.date_textView);
        textView_active = view.findViewById(R.id.active_textView);
        textView_tests_new = view.findViewById(R.id.tests_new_textView);

        Content content = new Content();
        content.execute();
        return view;

    }

    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.cancel();

            textView_confirmed.setText(confirmed);
            textView_confirmed_new.setText(newConfirmed);
            textView_recovered.setText(recovered);
            textView_recovered_new.setText(newRecovered);
            textView_death.setText(deaths);
            textView_death_new.setText(newDeaths);
            textView_date.setText(date);

        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://www.kenyans.co.ke/coronavirus";

                Document doc = Jsoup.connect(url).get();

                Elements confirmed_case = doc.getElementsByClass("numbers-wrap numbers-cases");
                //int size = confirmed_case.size();

                String confirmed_cases = confirmed_case
                        .select("a")
                        .select("div.number-big")
                        .text();
                String confirmed_cases_new = confirmed_case
                        .select("a")
                        .select("div.number-small")
                        .text();
                Log.d("TAG", "doInBackground: "+confirmed_cases);
                confirmed =confirmed_cases;
                newConfirmed=confirmed_cases_new;

                Elements confirmed_case_recovered = doc.getElementsByClass("numbers-wrap numbers-recoveries");
                //int size = confirmed_case.size();

                String confirmed_recoveries = confirmed_case_recovered
                        .select("a")
                        .select("div.number-big")
                        .text();
                String confirmed_recoveries_new = confirmed_case_recovered
                        .select("a")
                        .select("div.number-small")
                        .text();
                Log.d("TAG", "doInBackground: " + confirmed_cases);
                recovered = confirmed_recoveries;
                newRecovered = confirmed_recoveries_new;

                Elements confirmed_case_deaths = doc.getElementsByClass("numbers-wrap numbers-fatalities");
                //int size = confirmed_case.size();

                String confirmed_deaths = confirmed_case_deaths
                        .select("a")
                        .select("div.number-big")
                        .text();
                String confirmed_deaths_new = confirmed_case_deaths
                        .select("a")
                        .select("div.number-small")
                        .text();
                Log.d("TAG", "doInBackground: " + confirmed_cases);
                deaths = confirmed_deaths;
                newDeaths = confirmed_deaths_new;

                Elements last_update = doc.getElementsByClass("numbers-date-update");
                //int size = confirmed_case.size();

                String lastUpdate = last_update
                        .select("time")
                        .text();

                date=lastUpdate;
                /*for (int i=0;i<1;i++){
                    String confirmed_cases = confirmed_case
                            .select("p")
                            .select("span")
                            .select("a")
                            .eq(i)
                            .text();
                    String confirmed_case_link = confirmed_case
                            .select("p")
                            .select("span")
                            .select("a")
                            .eq(i)
                            .attr("href");
                    Log.d("TAG", "doInBackground: "+confirmed_cases);
                }

                 */
                //String confirmed_cases = confirmed_case.text();

                //Log.d("TAG", "doInBackground: "+size);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String dataURL = "https://corona.lmao.ninja/v2/countries";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, dataURL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String countryName = jsonObject.getString("country");
                            String countryConfirmed = jsonObject.getString("cases");
                            String countryActive = jsonObject.getString("active");
                            String countryRecovered = jsonObject.getString("recovered");
                            String countryDeceased = jsonObject.getString("deaths");
                            String countryNewConfirmed = jsonObject.getString("todayCases");
                            String countryNewDeceased = jsonObject.getString("todayDeaths");
                            String countryTests = jsonObject.getString("tests");
                            String countryNewRecovered = jsonObject.getString("todayRecovered");
                            JSONObject object = jsonObject.getJSONObject("countryInfo");
                            String flagUrl = object.getString("flag");

                            if (countryName.equals("Kenya")){
                                Log.d("TAG", "onResponse: "+countryActive);
                                int countryActiven = Integer.parseInt(countryActive);
                                int countrysampless = Integer.parseInt(countryTests);
                                textView_active.setText(NumberFormat.getInstance().format(countryActiven));
                                textView_tests.setText(NumberFormat.getInstance().format(countrysampless));
                                textView_tiime.setText("08:24 Am");

                            }



                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(jsonArrayRequest);
            return null;

        }
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
                    //Toast.makeText(MainActivity.this, "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);
    }


    }
