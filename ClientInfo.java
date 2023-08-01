package DEV120_4_2_Tekiev;

import java.io.Serializable;
import java.time.LocalDate;

public class ClientInfo implements Serializable {
    private String type;
    private final PhoneNumber phoneNumber;
    private String name;
    private String address;
    private String birthday;
    private String director;
    private String contact;
    private final LocalDate regDate;
    public ClientInfo(PhoneNumber phoneNumber, String name, String address, String birthday) {
        this.type = "Person";
        if(phoneNumber == null)
            throw new IllegalArgumentException("phone number can't be null.");
        this.phoneNumber = phoneNumber;
        this.regDate = LocalDate.now();
        this.director = "-";
        this.contact = "-";
        setName(name);
        setAddress(address);
        setBirthday(birthday);
    }
    public ClientInfo(PhoneNumber phoneNumber, String name, String address, String director, String contact) {
        this.type = "Company";
        if(phoneNumber == null)
            throw new IllegalArgumentException("phone number can't be null.");
        this.phoneNumber = phoneNumber;
        this.regDate = LocalDate.now();
        this.birthday = "-";
        setName(name);
        setAddress(address);
        setDirector(director);
        setContact(contact);
    }
    public void setBirthday(String birthday) {
        if(birthday == null)
            throw new IllegalArgumentException("birthday can't be null.");
        this.birthday = birthday;
    }
    public void setName(String name) {
        if(name == null)
            throw new IllegalArgumentException("name can't be null.");
        this.name = name;
    }
    public void setAddress(String address) {
        if(address == null)
            throw new IllegalArgumentException("address can't be null.");
        this.address = address;
    }
    public void setDirector(String director) {
        if(director == null)
            throw new IllegalArgumentException("director can't be null.");
        this.director = director;
    }
    public void setContact(String contact) {
        if(contact == null)
            throw new IllegalArgumentException("contact can't be null.");
        this.contact = contact;
    }
    public String getAddress() {
        return address;
    }
    public String getType() {
        return type;
    }
    public String getDirector() {return director;}
    public String getContact() {return contact;}

    public String getName() {
        return name;
    }
    public String getBirthday() {
        return birthday;
    }
    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
    public LocalDate getRegDate() {
        return regDate;
    }
    @Override
    public String toString() {
        return "ClientInfo{" +
                "type='" + type + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", director='" + director + '\'' +
                ", contact='" + contact + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
