package com.example.govpay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DisplayPayment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_payment);

        SharedPreferences sharedPref = getSharedPreferences("PAYMENT_DATA", MODE_PRIVATE);
        String payment_id = sharedPref.getString("payment_id", "no payment id");
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

                            Payment successful_payment= new Payment(item.getInt("amount"), item.getString("email"), item.getJSONObject("state").getString("status"));
                            System.out.println("Successfully parsed JSON into Payment");
                            JSONObject card_details_json = item.getJSONObject("card_details");
                            JSONObject billing_address_json = card_details_json.getJSONObject("billing_address");
                            Billing_address billing_address = new Billing_address(billing_address_json.getString("line1"),
                                    billing_address_json.getString("line2"),
                                    billing_address_json.getString("postcode"),
                                    billing_address_json.getString("city"),
                                    billing_address_json.getString("country")
                                    );

                            CardDetails card_details = new CardDetails(card_details_json.getString("last_digits_card_number"),
                                    card_details_json.getString("first_digits_card_number"),
                                    card_details_json.getString("cardholder_name"),
                                    card_details_json.getString("expiry_date"),
                                    billing_address,
                                    card_details_json.getString("card_brand")
                                    );

                            setUpViews(successful_payment, card_details);
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
        };
        queue.add(stringRequest);
    }
    public void setUpViews(Payment payment, CardDetails card_details) {
        TextView payment_view = findViewById(R.id.payment);
        TextView card_details_view = findViewById(R.id.card_details);
        TextView billing_address_view = findViewById(R.id.billing_address);
        payment_view.append("Amount: " + payment.getAmount() + "\n");
        payment_view.append("Status: " + payment.getStatus() + "\n");
        payment_view.append("Reference: " + payment.getReference() + "\n");
        payment_view.append("Description: " + payment.getDescription() + "\n");

        card_details_view.setText("Card Details: " + "\n");
        card_details_view.append("Card Type: " + card_details.getCard_brand() + "\n");
        card_details_view.append("First Digits of Card: " + card_details.getFirst_digits_card_number() + "\n");
        card_details_view.append("Last Digits of Card: " + card_details.getLast_digits_card_number() + "\n");
        card_details_view.append("Expiry Date: " + card_details.getExpiry_date() + "\n");
        card_details_view.append("Name: " + card_details.getCardholder_name() + "\n");
        card_details_view.append("Email: " + payment.getEmail() + "\n");

        billing_address_view.setText("Billing Address: \n");
        billing_address_view.append("Line 1: " + card_details.billing_address.getLine1() + "\n");
        billing_address_view.append("Line 2: " + card_details.billing_address.getLine2() + "\n");
        billing_address_view.append("City: " + card_details.billing_address.getCity() + "\n");
        billing_address_view.append("Country: " + card_details.billing_address.getCountry() + "\n");
        billing_address_view.append("Post code: " + card_details.billing_address.getPostcode());
    }

    public void payAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
