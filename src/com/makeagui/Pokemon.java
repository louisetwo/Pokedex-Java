package com.makeagui;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Pokemon {
    private String name;
    private String type;
    private String pokeNumber;
    private LocalDate dateOfCatch;

    public Pokemon(String name, String type, String pokeNumber, LocalDate dateOfCatch) {
        this.name = name;
        this.type = type;
        this.pokeNumber = pokeNumber;
        this.dateOfCatch = dateOfCatch;
    }

    public Pokemon(String name, String type, String pokeNumber, String dateOfCatch) {
        this.name = name;
        this.type = type;
        this.pokeNumber = pokeNumber;
        this.setDateOfCatch(dateOfCatch);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPokeNumber() {
        return pokeNumber;
    }

    public void setPokeNumber(String pokeNumber) {
        this.pokeNumber = pokeNumber;
    }

    public LocalDate getDateOfCatch() {
        return dateOfCatch;
    }

    public void setDateOfCatch(LocalDate dateOfCatch) {
        this.dateOfCatch = dateOfCatch;
    }

    public void setDateOfCatch(String DateOfCatch){
        this.dateOfCatch = LocalDate.parse(DateOfCatch, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    public  int getTime(){
        LocalDate today = LocalDate.now();
        Period period = Period.between(this.dateOfCatch, today);
        return period.getYears();

    }

}