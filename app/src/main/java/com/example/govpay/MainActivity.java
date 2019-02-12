package com.example.govpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSite(View view){
        Intent intent = new Intent(this, WebPage.class);
        EditText editText = findViewById(R.id.amount);
        String amount_of_money = editText.getText().toString();
        intent.putExtra("AMOUNT", amount_of_money);
        startActivity(intent);
    }
}
