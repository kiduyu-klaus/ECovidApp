package com.yearfour.mercyproject.e_covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

public class MainActivity extends AppCompatActivity {
    String confirmed;
    String active;
    String date;
    String recovered;
    Button button;
    ToolTip toolTip;
    TourGuide mTourGuideHandler;
    String deaths;
    String newConfirmed;
    String newDeaths;
    private RequestQueue requestQueue;
    String newRecovered;
    private long backPressTime;
    private Toast backToast;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_name)+" kenya");

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Content content = new Content();
        content.execute();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Homefragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigationHome);
        }
        Animation animation = new TranslateAnimation(0f, 0f, 200f, 0f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setInterpolator(new BounceInterpolator());

        toolTip = new ToolTip()
                .setTitle("About This System")
                .setDescription("Click on the button to Learn more...")
                .setTextColor(Color.parseColor("#bdc3c7"))
                .setBackgroundColor(Color.parseColor("#e74c3c"))
                .setShadow(true)
                .setGravity(Gravity.TOP | Gravity.LEFT)
                .setEnterAnimation(animation);

        //button = findViewById(R.id.info);

        /*mTourGuideHandler = TourGuide.init(MainActivity.this).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(toolTip)
                .setOverlay(new Overlay())
                .playOn(R.id.info);
                
         */


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationHome:
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,
                            new Homefragment()).commit();
                    return true;
                case R.id.navigationNews:
                    FragmentManager fm = getSupportFragmentManager();
                    Bundle arguments = new Bundle();
                    arguments.putString("booklink", "today");
                    Newsfragment readerCheckBookDetailFragment = new Newsfragment();
                    readerCheckBookDetailFragment.setArguments(arguments);
                    fm.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, readerCheckBookDetailFragment).commit();


                    //MenuItem.se
                    return true;
                case R.id.navigationworld:
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new WorldStatFragment()).commit();

                    return true;
                case R.id.navigationlevels:
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new MyLevelsFragents()).commit();

                    return true;
                case R.id.navigationcounty:
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new CountyFragments()).commit();

                    return true;
            }


            return false;
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
       final PieChart mPieChart = findViewById(R.id.piechart);
            mPieChart.clearChart();
            String trim = confirmed.replaceAll(",", "");
            String trim2 = recovered.replaceAll(",", "");
            String trim3 = deaths.replaceAll(",", "");
            int total = Integer.parseInt(trim) + Integer.parseInt(trim2) + Integer.parseInt(trim3);
            // int percentage=total*100;
            Log.d("TAG", "total: " + total);
            int confirm = Integer.parseInt(trim);
            double confirmed_cases = (double) confirm / total;
            double confirmed_cases_ok = (double) confirmed_cases * 100;

            int recoveries = Integer.parseInt(trim2);
            double recover_cases = (double) recoveries / total;
            double recover_cases_ok = (double) recover_cases * 100;

            int deat = Integer.parseInt(trim3);
            double death_cases = (double) deat / total;
            double death_casess_ok = (double) death_cases * 100;

            Log.d("TAG", "total: " + (int) confirmed_cases_ok);
            //int confirm=Integer.parseInt(trim2);
            // double recovery_percent= confirm/total*100;
            double deaths_percent = Integer.parseInt(trim3) / total * 100;

            Log.d("TAG", "onPostExecute: " + " " + " " + deaths_percent);
            mPieChart.addPieSlice(new PieModel("Active", (int) confirmed_cases_ok, Color.parseColor("#007afe")));
            mPieChart.addPieSlice(new PieModel("Recovered", (int) recover_cases_ok, Color.parseColor("#08a045")));
            mPieChart.addPieSlice(new PieModel("Deceased", (int) death_casess_ok, Color.parseColor("#F6404F")));

            mPieChart.startAnimation();




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
                Log.d("TAG", "doInBackground: " + confirmed_cases);
                confirmed = confirmed_cases;

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

                date = lastUpdate;
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



            return null;

        }
    }


    @Override
    public void onBackPressed() {

        if (backPressTime + 800 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }

    public String formatDate(String date, int testCase) {
        Date mDate = null;
        String dateFormat;
        try {
            mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date);
            if (testCase == 0) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.US).format(mDate);
                return dateFormat;
            } else if (testCase == 1) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(mDate);
                return dateFormat;
            } else if (testCase == 2) {
                dateFormat = new SimpleDateFormat("hh:mm a", Locale.US).format(mDate);
                return dateFormat;
            } else {
                Log.d("error", "Wrong input! Choose from 0 to 2");
                return "Error";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }


}
