package com.example.Business_Invebtory.ModalClasses;

public class MohajonModel {

    String Address, Name, Phone, Shop_Name;

    @Override
    public String toString() {
        return "MohajonModel{" +
                "Address='" + Address + '\'' +
                ", Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Shop_Name='" + Shop_Name + '\'' +
                '}';
    }

    public MohajonModel() {
    }

    public MohajonModel(String address, String name, String phone, String shop_Name) {
        Address = address;
        Name = name;
        Phone = phone;
        Shop_Name = shop_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getShop_Name() {
        return Shop_Name;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }
}
