package com.octavianusosbert.addressbook;

public class Contacts {
    private String id;
    private String name;
    private long phone_number;
    private String address;

    public Contacts(String id, String name, int phone_number, String address) {
        super();
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.address = address;
    }

    public Contacts() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
