package com.kreiner.demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Name implements Serializable {
    String first;
    String middle;
    String last;

    public Name(){}

    public Name(String first, String middle, String last){
        this.first = first;
        this.middle = middle;
        this.last = last;
    }
}