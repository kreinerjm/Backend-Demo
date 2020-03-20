package com.kreiner.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class PhoneDBO {

    private @Id @GeneratedValue Long id;
    private Long contactId;
    private String number;
    private String type;

    public PhoneDBO(){}

    public PhoneDBO(Long contactId, String number, String type){
        this.contactId = contactId;
        this.number = number;
        this.type = type;
    }

    public PhoneDBO(Phone phone){
        this.number = phone.getNumber();
        this.type = phone.getType();
    }
}
