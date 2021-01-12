package PhoneBook;

public class PhoneBookClassModel {

    String Name,Phone;

    @Override
    public String toString() {
        return "PhoneBookClassModel{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }

    public PhoneBookClassModel() {
    }

    public PhoneBookClassModel(String name, String phone) {
        Name = name;
        Phone = phone;
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
}
