package com.example.groceryapplication;

public class BillListsModalClass {

    String Bill,Date;

    public BillListsModalClass() {
    }

    @Override
    public String toString() {
        return "BillListsModalClass{" +
                "Bill='" + Bill + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }

    public BillListsModalClass(String bill, String date) {
        Bill = bill;
        Date = date;
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
}
