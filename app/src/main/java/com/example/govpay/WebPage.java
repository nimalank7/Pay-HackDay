package com.example.govpay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WebPage extends AppCompatActivity {

    private String json_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Long amount = Long.parseLong(intent.getStringExtra("AMOUNT"));
        System.out.println(amount);

        // Create payment object
        Payment payment = new Payment(amount);
        // Converts payment to JSON
        Gson gson = new Gson();
        String json = gson.toJson(payment);
        json_object = json;

        super.onCreate(savedInstanceState);
        System.out.println("now onto API call...");
        apiCall();
    }

    public void apiCall() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://publicapi.payments.service.gov.uk/v1/payments";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response successful");
                        try {
                            JSONObject item = new JSONObject(response);
                            String url = item.getJSONObject("_links").getJSONObject("next_url").getString("href");
                            String payment_id = item.getString("payment_id");
                            System.out.println("This is the payment id: " + payment_id);
                            store_payment_id(payment_id);
                            setUpWeb(url);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("accept", "application/json");
                headers.put("Authorization", BuildConfig.ApiKey);
                return headers;
            }

            @Override
            public String getBodyContentType(){
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError{
                try {
                    return json_object.getBytes("utf-8"); // For DATA put our string
                } catch (Exception e) {
                    return null;
                }
            }
        };
        queue.add(stringRequest);
    }

    public void setUpWeb(String url){
        WebView myWebView = new WebView(this);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent;

                if (url.contains("myapp://display_payment")) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        setContentView(myWebView);
        myWebView.loadUrl(url);
    }

    public void store_payment_id(String payment_id) {
        SharedPreferences sharedPref = getSharedPreferences("PAYMENT_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("payment_id", payment_id);
        editor.commit();
        System.out.println("Stored Payment ID");
        System.out.println(sharedPref.getString("payment_id", "no payment id"));
    }
}
