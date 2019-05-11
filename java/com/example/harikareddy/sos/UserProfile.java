package com.example.harikareddy.sos;

public class UserProfile {
    public String firstName;
    public String lastName;
    public String mobile;
    public String address;
    public String email;
    public String contact1,contact2,contact3;
    public UserProfile(){
    }
    public UserProfile(String firstName,String lastName,String Mobile, String Address, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = Mobile;
        this.address = Address;
        this.email = email;

    }
    public UserProfile(String contact1,String contact2, String contact3){
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.contact3 = contact3;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact3() {
        return contact3;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }
}

