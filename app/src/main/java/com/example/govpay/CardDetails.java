package com.example.govpay;

public class CardDetails {
    String last_digits_card_number;
    String first_digits_card_number;
    String cardholder_name;
    String expiry_date;
    Billing_address billing_address;
    String card_brand;

    public String getLast_digits_card_number() {
        return last_digits_card_number;
    }

    public void setLast_digits_card_number(String last_digits_card_number) {
        this.last_digits_card_number = last_digits_card_number;
    }

    public String getFirst_digits_card_number() {
        return first_digits_card_number;
    }

    public void setFirst_digits_card_number(String first_digits_card_number) {
        this.first_digits_card_number = first_digits_card_number;
    }

    public String getCardholder_name() {
        return cardholder_name;
    }

    public void setCardholder_name(String cardholder_name) {
        this.cardholder_name = cardholder_name;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public Billing_address getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(Billing_address billing_address) {
        this.billing_address = billing_address;
    }

    public String getCard_brand() {
        return card_brand;
    }

    public void setCard_brand(String card_brand) {
        this.card_brand = card_brand;
    }

    public CardDetails(String last_digits_card_number, String first_digits_card_number, String cardholder_name, String expiry_date, Billing_address billing_address, String card_brand) {
        this.last_digits_card_number = last_digits_card_number;
        this.first_digits_card_number = first_digits_card_number;
        this.cardholder_name = cardholder_name;
        this.expiry_date = expiry_date;
        this.billing_address = billing_address;
        this.card_brand = card_brand;
    }
}
