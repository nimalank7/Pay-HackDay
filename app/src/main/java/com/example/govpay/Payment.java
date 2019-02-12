package com.example.govpay;

public class Payment {

    long amount;
    String reference = "12345";
    String description = "Government Donations";
    String return_url = "http://www.google.com/";

    public Payment(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public String getReference() {
        return reference;
    }

    public String getDescription() {
        return description;
    }

    public String getReturn_url() {
        return return_url;
    }
}
