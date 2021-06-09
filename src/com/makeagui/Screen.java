package com.makeagui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Screen extends JFrame {
    private JPanel panelTop;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JList listPokemon;
    private JButton buttonNew;
    private JButton buttonSave;
    private JTextField textName;
    private JTextField textType;
    private JTextField textPokeNumber;
    private JTextField textDateOfCatch;
    private JLabel labelTime;
    private JPanel panelMain;
    private ArrayList<Pokemon> pokemons;
    private  DefaultListModel listPokemonModel;



    Screen(){          // Constructor
        super("My pokedex owo ");  // Contrutor do Jframe quer o titulo para o que queremos na janela
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // O que o programa faz quando clica no iconde de fechar janela - Sair quando clicar
        this.pack();
        pokemons = new ArrayList<Pokemon>();  // Initialize arraylist in constructor
        listPokemonModel = new DefaultListModel();
        listPokemon.setModel(listPokemonModel);
        buttonSave.setEnabled(false);


        buttonNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {   // takes as a parameter a function
                buttomNewClick(e);                    //put in a reference to that function(buttonNewclick)
            }

        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSavedClick(e);

            }
        });

        listPokemon.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                listPokemonSelection(e);

            }
        });

    }

    public  void buttomNewClick(ActionEvent e){
        Pokemon p = new Pokemon(
                textName.getText(),
                textType.getText(),
                textPokeNumber.getText(),
                textDateOfCatch.getText()
        );
        pokemons.add(p);
        refreshPokemonList();

    }

    public void buttonSavedClick(ActionEvent e){
        int personNumber = listPokemon.getSelectedIndex();
        if (personNumber >= 0) {
            Pokemon p = pokemons.get(personNumber);
            p.setName(textName.getText());
            p.setType(textType.getText());
            p.setPokeNumber(textPokeNumber.getText());
            p.setDateOfCatch(textDateOfCatch.getText());   // String
            refreshPokemonList();
        }
    }

    public void listPokemonSelection(ListSelectionEvent e){
        int personNumber = listPokemon.getSelectedIndex();     // give the number of the item that you clicked
        if(personNumber >= 0){
            Pokemon p = pokemons.get(personNumber);
            textName.setText(p.getName());
            textType.setText(p.getType());
            textPokeNumber.setText(p.getPokeNumber());
            textDateOfCatch.setText(p.getDateOfCatch().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));   //Local date
            labelTime.setText(Integer.toString(p.getTime()) + " years");
            buttonSave.setEnabled(true);

        }else{
            buttonSave.setEnabled(false);
        }

    }

    public void refreshPokemonList(){
        listPokemonModel.removeAllElements();
        System.out.println(" Removing all pokemons from list ");
        for(Pokemon p: pokemons){       // for pokemon p inside pokemons  - rebuild
            System.out.println("Adding pokemon to list: " +p.getName());
            listPokemonModel.addElement(p.getName());
        }

    }

    public void addPokemon(Pokemon p){
        pokemons.add(p);
        refreshPokemonList();

    }

    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.setVisible(true);

        Pokemon pikachu = new Pokemon("Pikachu storm Junior", "lightning", "0001", "12/01/1999");
        Pokemon charizard = new Pokemon("Charizard owo", "Fire", "0002", "01/03/2019");
        Pokemon Jigllypuff = new Pokemon("Jigllypuff ffff", "Fairy", "003", "01/01/2020");
        Pokemon entei = new Pokemon("Entei u-u", "Fire", "130", "06/10/2021");
        Pokemon magikarp = new Pokemon("Magikarp", "Water", "120", "02/12/2015");
        Pokemon giratinna = new Pokemon("Giratinna", "Grass", "133 0004", "17/05/2017");
        Pokemon abraa = new Pokemon("Abraa Kadabra", "Psychic / Iron ", "555 0005", "17/12/2000");

        screen.addPokemon(pikachu);
        screen.addPokemon(charizard);
        screen.addPokemon(Jigllypuff);
        screen.addPokemon(magikarp);

    }



}