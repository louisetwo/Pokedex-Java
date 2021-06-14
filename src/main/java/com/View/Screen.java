package com.View;

import com.DAO.GenericDAO;
import com.DAO.PokedexDAO;
import com.DAO.PokemonDAO;
import com.Model.Pokedex;
import com.Model.Pokemon;

import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

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
    private JButton buttonDelete;
    private JLabel labelPokeball;
    private JLabel labelPokemonImage;
    private JLabel labelTrainer;
    private  DefaultListModel listPokemonModel;
    private String pokedexImagesUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    private final PokemonDAO<Pokemon> pokemonDAO; // conexão com a tabela de pokemon no banco
    private final PokedexDAO<Pokedex> pokedexDAO; // conexão com a tabela de pokedex no banco

    private Pokedex pokedex = new Pokedex(1, "Louise");

    // Colours   https://www.schemecolor.com/pokemon-colors.php

    Screen(){          // Constructor
        super("My pokedex òwó  ");  //  Jframe constructor
        pokemonDAO = new PokemonDAO<Pokemon>();
        pokedexDAO = new PokedexDAO<Pokedex>();
        pokedexDAO.saveOrUpdate(pokedex);
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //  exit when clicked
        listPokemonModel = new DefaultListModel();
        listPokemon.setModel(listPokemonModel);
        buttonSave.setEnabled(false);
        ImageIcon image = new ImageIcon(getClass().getResource("/images/pokedex.png"));
        this.labelPokeball.setIcon(getScaledImage(image, 50, 50));
        this.labelTrainer.setText("Trainer: " + pokedex.getPokemonTrainer());



        this.pack();

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

        buttonDelete.addActionListener(new ActionListener() {              // Action to delete
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePokemon();
            }
        });
    }

    public  void buttomNewClick(ActionEvent e){
        try {
            int PokeNumber = Integer.parseInt(textPokeNumber.getText());
            Pokemon p = new Pokemon(
                    textName.getText(),
                    textType.getText(),
                    PokeNumber,
                    textDateOfCatch.getText(),
                    pokedex
            );
            addPokemon(p);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void buttonSavedClick(ActionEvent e){
        int indexNumber = listPokemon.getSelectedIndex();
        if (indexNumber >= 0) {
            Pokemon p = pokedex.getPokemons().get(indexNumber);
            p.setName(textName.getText());
            p.setType(textType.getText());
            try {
                p.setPokeNumber(Integer.parseInt(textPokeNumber.getText()));
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            p.setDateOfCatch(textDateOfCatch.getText());   // String
            pokemonDAO.saveOrUpdate(p);
            refreshPokemonList();
        }
    }

    public void listPokemonSelection(ListSelectionEvent e){
        int index = listPokemon.getSelectedIndex();     // give the number of the item that you clicked
        if(index >= 0){
            Pokemon p = pokedex.getPokemons().get(index);
            textName.setText(p.getName());
            textType.setText(p.getType());
            textPokeNumber.setText(Integer.toString(p.getId()));
            textDateOfCatch.setText(p.getDateOfCatch().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));   //Local date
            labelTime.setText(Integer.toString(p.getTime()) + " years");
            buttonSave.setEnabled(true);

            try {
                URL url = new URL(this.pokedexImagesUrl + p.getId() +".png");
                BufferedImage image = ImageIO.read(url);
                this.labelPokemonImage.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                ImageIcon image = new ImageIcon(getClass().getResource("/images/notFound.png"));
                this.labelPokemonImage.setIcon(getScaledImage(image, 120, 120));  //96x96
            }

        }else{
            buttonSave.setEnabled(false);
        }

    }

    public void refreshPokemonList(){
        listPokemonModel.removeAllElements();
        System.out.println(" Removing all pokemons from list ");
        for(Pokemon p: pokedex.getPokemons()){       // for pokemon p inside pokemons  - rebuild
            System.out.println("Adding pokemon to list: " +p.getName());
            listPokemonModel.addElement(p.getName());
        }
    }

    public void addPokemon(Pokemon p){
        pokedex.getPokemons().add(p);
        pokemonDAO.saveOrUpdate(p);
        sortPokemons();
        refreshPokemonList();
        listPokemon.setSelectedIndex(0);
    }

    private void sortPokemons() {
        pokedex.getPokemons().sort((a,b) -> {
            if (a.getId() > b.getId()) return 1;
            else return -1;
        });
    }

    public void deletePokemon() {
        int index = listPokemon.getSelectedIndex();
        Pokemon p = pokedex.getPokemons().get(index);
        pokemonDAO.remove(Pokemon.class, p.getId());
        pokedex.getPokemons().remove(index);
        refreshPokemonList();
        listPokemon.setSelectedIndex(0);

    }

    private ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
        Image image = srcImg.getImage(); // transform it
        Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        ImageIcon imageIcon = new ImageIcon(newimg);
        return imageIcon;
    }

    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.setVisible(true);
        List pokemonsFromDB = screen.pokemonDAO.listPokemonByPokedexId(screen.pokedex.getId());
        screen.pokedex.setPokemonList(pokemonsFromDB);
        screen.refreshPokemonList();
    }
}