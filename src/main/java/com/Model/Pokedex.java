package com.Model;

import com.DAO.EntidadeBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Pokedex")
public class Pokedex implements EntidadeBase {  //  https://pokeapi.co/
    @Id
    private Integer id;

    @Column
    private String pokemonTrainer;

    @OneToMany
    private List<Pokemon> pokemons = new ArrayList<Pokemon>();

    public Pokedex(int id, String pokemonTrainer) {
        this.id = id;
        this.pokemonTrainer = pokemonTrainer;
    }

    public Pokedex() {

    }

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getPokemonTrainer() {
        return this.pokemonTrainer;
    }

    public void setPokemonTrainer(String name) {
        this.pokemonTrainer = name;
    }

    public List<Pokemon> getPokemons() {
        return this.pokemons;
    }

    public void addPokemon(Pokemon pokemon){
        this.pokemons.add(pokemon);
    }

    public void setPokemonList(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
    public void removePokemon(Pokemon pokemon){
        this.pokemons.remove(pokemon);
    }

}