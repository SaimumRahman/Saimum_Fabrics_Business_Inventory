package Mohajon;

public class ShowBillMohajonClass {
    String Bill,Date,Time;

    @Override
    public String toString() {
        return "ShowBillMohajonClass{" +
                "Bill='" + Bill + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }

    public ShowBillMohajonClass() {
    }

    public ShowBillMohajonClass(String bill, String date, String time) {
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
