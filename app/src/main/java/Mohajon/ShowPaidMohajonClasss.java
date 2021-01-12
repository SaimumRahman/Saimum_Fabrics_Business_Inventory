package Mohajon;

public class ShowPaidMohajonClasss {
    String Amount_Paid,Date,Time;

    @Override
    public String toString() {
        return "ShowPaidMohajonClasss{" +
                "Amount_Paid='" + Amount_Paid + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }

    public ShowPaidMohajonClasss() {
    }

    public ShowPaidMohajonClasss(String amount_Paid, String date, String time) {
        Amount_Paid = amount_Paid;
        Date = date;
        Time = time;
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
