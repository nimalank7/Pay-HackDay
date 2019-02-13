package com.example.govpay;

public class Payment {

    long amount;
    String reference = "12342";
    String description = "Government Donations";
    String return_url = "https://glacial-woodland-68589.herokuapp.com/confirmation";

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
