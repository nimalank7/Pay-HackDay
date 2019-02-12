package com.example.govpay;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DisplayPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_payment);

        SharedPreferences sharedPref = DisplayPayment.this.getPreferences(Context.MODE_PRIVATE);
        String payment_id = sharedPref.getString("payment_id", "no payment id");
        System.out.println(payment_id);
        apiCall(payment_id);

    }

    public void apiCall(String payment_id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://publicapi.payments.service.gov.uk/v1/payments/" + payment_id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response successful");
                        System.out.println(response);
                        try {
                            JSONObject item = new JSONObject(response);
                            /*

                            String url = item.getJSONObject("_links").getJSONObject("next_url").getString("href");
                            String payment_id = item.getString("payment_id");
                            System.out.println(payment_id);
                            setUpViews();

                            */

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work");
                // setUpWeb();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("accept", "application/json");
                headers.put("Authorization", "");
                return headers;
            }

        };
        queue.add(stringRequest);
    }
    public void setUpViews() {

    }
}
