package com.kreiner.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ContactDBO {

    private @Id @GeneratedValue Long id;

    private String firstName;
    private String middleName;
    private String lastName;

    private String street;
    private String city;
    private String state;
    private String zip;

    private String email;

    public ContactDBO(){}

    public ContactDBO(String first, String middle, String last, String street,
                      String city, String state, String zip, String email){
        firstName = first;
        middleName = middle;
        lastName = last;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
    }

    public ContactDBO(Contact contact){
        this.id = contact.getId();
        this.firstName = contact.getName().first;
        this.middleName = contact.getName().middle;
        this.lastName = contact.getName().last;
        this.street = contact.getAddress().street;
        this.city = contact.getAddress().city;
        this.state = contact.getAddress().state;
        this.zip = contact.getAddress().zip;
        this.email = contact.getEmail();
    }
}
