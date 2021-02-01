package com.example.Business_Invebtory.ModalClasses;

public class ImageModelClass {

    String date,time,Shop_Names,Image;

    @Override
    public String toString() {
        return "ImageModelClass{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", Shop_Names='" + Shop_Names + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

    public ImageModelClass() {
    }

    public ImageModelClass(String date, String time, String shop_Names, String image) {
        this.date = date;
        this.time = time;
        Shop_Names = shop_Names;
        Image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShop_Names() {
        return Shop_Names;
    }

    public void setShop_Names(String shop_Names) {
        Shop_Names = shop_Names;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
