package com.kreiner.demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    String street;
    String city;
    String state;
    String zip;

    public Address(){}

    public Address(String street, String city, String state, String zip){
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}