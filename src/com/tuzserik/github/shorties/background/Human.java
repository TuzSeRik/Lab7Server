package com.tuzserik.github.shorties.background;

import java.io.ObjectOutputStream;

public class Human extends Person {
    private String name;

    public Human(String name, int heigth, int weigth, String CName, Integer hp){
        super(heigth, CName, hp);
        this.name = name;
    }

    String getName(){
        return name;
    }

    @Override
    public void takeFood(Furnace furnace, ObjectOutputStream os){
        throw new RuntimeException();
    }
}
//+