package com.kreiner.demo;

import lombok.Data;

@Data
public class Contact {

    private Long id;

    private Name name;

    private Address address;

    private Phone[] phone;

    private String email;

    public Contact(){}

    public Contact(ContactDBO dbo, Phone[] phones){
        this.id = dbo.getId();
        name = new Name(dbo.getFirstName(), dbo.getMiddleName(), dbo.getLastName());
        address = new Address(dbo.getStreet(), dbo.getCity(), dbo.getState(), dbo.getZip());
        email = dbo.getEmail();
        phone = phones;
    }

}
