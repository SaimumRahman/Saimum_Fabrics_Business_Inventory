package com.example.groceryapplication;

public class BillListsModalClass {

    String Bill,Date,Time;

    public BillListsModalClass() {
    }

    @Override
    public String toString() {
        return "BillListsModalClass{" +
                "Bill='" + Bill + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }

    public BillListsModalClass(String bill, String date, String time) {
        Bill = bill;
        Date = date;
        Time = time;
    }

    public String getBill() {
        return Bill;
    }

    public void setBill(String bill) {
        Bill = bill;
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
