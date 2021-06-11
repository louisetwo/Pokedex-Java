package com.Model;

import com.DAO.EntidadeBase;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="Pokemon")
public class Pokemon implements EntidadeBase {  //  https://pokeapi.co/
    @Id
    private Integer pokeNumber;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private LocalDate dateOfCatch;

    @ManyToOne
    private Pokedex pokedex;

    public Pokemon(String name, String type, int pokeNumber, String dateOfCatch, Pokedex pokedex) {
        this.name = name;
        this.type = type;
        this.pokeNumber = pokeNumber;
        this.setDateOfCatch(dateOfCatch);
        this.pokedex = pokedex;
    }

    public Pokemon() {

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

    public Integer getId() {
        return pokeNumber;
    }

    public void setPokeNumber(int pokeNumber) {
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