package com.yearfour.mercyproject.e_covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Objects;

import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_ACTIVE;
import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_CONFIRMED;
import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_DECEASED;
import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_NAME;
import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_NEW_CONFIRMED;
import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_NEW_DECEASED;
import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_NEW_RECOVERED;
import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_RECOVERED;
import static com.yearfour.mercyproject.e_covidapp.WorldStatFragment.COUNTRY_TESTS;

public class PerStateData extends AppCompatActivity {
    TextView perCountryConfirmed, perCountryActive, perCountryDeceased, perCountryNewConfirmed, perCountryTests, perCountryNewDeceased, perCountryRecovered, perCountryNewRecovered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_state_data);
        Intent intent = getIntent();
        String countryName = intent.getStringExtra(COUNTRY_NAME);
        String countryConfirmed = intent.getStringExtra(COUNTRY_CONFIRMED);
        String countryActive = intent.getStringExtra(COUNTRY_ACTIVE);
        String countryDeceased = intent.getStringExtra(COUNTRY_DECEASED);
        String countryRecovery = intent.getStringExtra(COUNTRY_RECOVERED);
        String countryNewConfirmed = intent.getStringExtra(COUNTRY_NEW_CONFIRMED);
        String countryNewDeceased = intent.getStringExtra(COUNTRY_NEW_DECEASED);
        String countryTests = intent.getStringExtra(COUNTRY_TESTS);
        String countryNewRecovered = intent.getStringExtra(COUNTRY_NEW_RECOVERED);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Objects.requireNonNull(getSupportActionBar()).setTitle(countryName);

        if (countryName.equals("Kenya")){
            startActivity(new Intent(this,MainActivity.class));
        }
        perCountryConfirmed = findViewById(R.id.percountry_confirmed_textview);
        perCountryActive = findViewById(R.id.percountry_active_textView);
        perCountryRecovered = findViewById(R.id.percountry_recovered_textView);
        perCountryDeceased = findViewById(R.id.percountry_death_textView);
        perCountryNewConfirmed = findViewById(R.id.percountry_confirmed_new_textView);
        perCountryTests = findViewById(R.id.percountry_tests_textView);
        perCountryNewDeceased = findViewById(R.id.percountry_death_new_textView);
        perCountryNewRecovered = findViewById(R.id.percountry_recovered_new_textView);

        String activeCopy = countryActive;
        String recoveredCopy = countryRecovery;
        String deceasedCopy = countryDeceased;

        int activeInt = Integer.parseInt(countryActive);
        countryActive = NumberFormat.getInstance().format(activeInt);

        int recoveredInt = Integer.parseInt(countryRecovery);
        countryRecovery = NumberFormat.getInstance().format(recoveredInt);

        int deceasedInt = Integer.parseInt(countryDeceased);
        countryDeceased = NumberFormat.getInstance().format(deceasedInt);

        int confirmedInt = Integer.parseInt(countryConfirmed);
        countryConfirmed = NumberFormat.getInstance().format(confirmedInt);

        int testsInt = Integer.parseInt(countryTests);
        countryTests = NumberFormat.getInstance().format(testsInt);

        perCountryConfirmed.setText(countryConfirmed);
        perCountryActive.setText(countryActive);
        perCountryDeceased.setText(countryDeceased);
        perCountryTests.setText(countryTests);
        perCountryNewConfirmed.setText("+" + countryNewConfirmed);
        perCountryNewDeceased.setText("+" + countryNewDeceased);
        perCountryRecovered.setText(countryRecovery);
        perCountryNewRecovered.setText("+"+countryNewRecovered);
    }
}