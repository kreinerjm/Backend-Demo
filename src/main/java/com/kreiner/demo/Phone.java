package com.kreiner.demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Phone implements Serializable {
    String number;
    String type;

    public Phone(){}

    public Phone(String number, String type){
        this.number = number;
        this.type = type;
    }

    public Phone(PhoneDBO dbo){
        this.number = dbo.getNumber();
        this.type = dbo.getType();
    }
}