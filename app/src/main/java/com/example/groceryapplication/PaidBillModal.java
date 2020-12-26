package com.example.groceryapplication;

public class PaidBillModal {
    String Amount_Paid,Date;

    public PaidBillModal() {
    }

    @Override
    public String toString() {
        return "PaidBillModal{" +
                "Amount_Paid='" + Amount_Paid + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }

    public PaidBillModal(String amount_Paid, String date) {
        Amount_Paid = amount_Paid;
        Date = date;
    }

    public String getAmount_Paid() {
        return Amount_Paid;
    }

    public void setAmount_Paid(String amount_Paid) {
        Amount_Paid = amount_Paid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
