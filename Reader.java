package it.polito.library;

public class Reader {
    String name;
    String surname;
    String readerId;
    Boolean rentalStatus = false;
    Integer noOfRentals=0;
    public Reader(String name, String surname, String readerId) {
        this.name = name;
        this.surname = surname;
        this.readerId = readerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

}
