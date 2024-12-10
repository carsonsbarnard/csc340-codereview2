package com.bookstorestaticwebsite.StaticBookStoreWebsite.customer;


import com.bookstorestaticwebsite.StaticBookStoreWebsite.order.BookOrder;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.review.Review;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    @NonNull
    private String email;
    @NonNull
    private String password;

    private String firstName;
    private String lastName;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private String phone;
    private Date registerDate;

    public Customer() {
    }

    public Customer(@NonNull String email, @NonNull String password) {
        this.email = email;
        this.password = password;
    }

    public Customer(@NonNull String email, @NonNull String password, String firstName, String lastName, String addressLine1, String addressLine2, String city, String state, String zipcode, String country, String phone, Date registerDate) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.phone = phone;
        this.registerDate = registerDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
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

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString(){
        return "Full name: " + this.firstName + " " + this.lastName;
    }
    public String getFullAddress(){
        return this.addressLine1 + ", " + this.city + ", " + this.state + ", " + this.country + ", " + this.zipcode;
    }
}
