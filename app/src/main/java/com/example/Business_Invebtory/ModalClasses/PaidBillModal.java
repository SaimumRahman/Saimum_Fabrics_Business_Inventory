package com.example.Business_Invebtory.ModalClasses;

public class PaidBillModal {
    String Amount_Paid,Date,Time;

    public PaidBillModal() {
    }

    @Override
    public String toString() {
        return "PaidBillModal{" +
                "Amount_Paid='" + Amount_Paid + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                '}';
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
