package com.kiduyu.mercyproject.e_covidapp.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtractData {
    Context context;

    public ExtractData(Context mcontext) {
        this.context = mcontext;
    }

    RequestQueue requestQueue = Volley.newRequestQueue(context);

    public String extractData() {
        final String[] countryAActive = {""};
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

                        if (countryName.equals("Kenya")){
                            Log.d("TAG", "onResponse: "+countryActive);

                            countryAActive[0] = countryActive;

                        }

                        return;
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
        return countryAActive[0];
    }

    private String OnReturn(String countryActive) {
        return countryActive;
    }
}
