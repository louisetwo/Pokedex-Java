package com.makeagui;

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
    private JButton buttonDelete;
    private JLabel labelPokeball;
    private JLabel labelPokemonImage;
    private ArrayList<Pokemon> pokemons;
    private  DefaultListModel listPokemonModel;
    private String pokedexImagesUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";



    Screen(){          // Constructor
        super("My pokedex owo ");  // Contrutor do Jframe quer o titulo para o que queremos na janela
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // O que o programa faz quando clica no iconde de fechar janela - Sair quando clicar
        pokemons = new ArrayList<Pokemon>();  // Initialize arraylist in constructor
        listPokemonModel = new DefaultListModel();
        listPokemon.setModel(listPokemonModel);
        buttonSave.setEnabled(false);
        ImageIcon image = new ImageIcon(getClass().getResource("/images/pokedex.png"));
        this.labelPokeball.setIcon(getScaledImage(image, 50, 50));

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
                    textDateOfCatch.getText()
            );
            addPokemon(p);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void buttonSavedClick(ActionEvent e){
        int personNumber = listPokemon.getSelectedIndex();
        if (personNumber >= 0) {
            Pokemon p = pokemons.get(personNumber);
            p.setName(textName.getText());
            p.setType(textType.getText());
            try {
                p.setPokeNumber(Integer.parseInt(textPokeNumber.getText()));
            }catch(Exception ex) {
                ex.printStackTrace();
            }
            p.setDateOfCatch(textDateOfCatch.getText());   // String
            refreshPokemonList();
        }
    }

    public void listPokemonSelection(ListSelectionEvent e){
        int index = listPokemon.getSelectedIndex();     // give the number of the item that you clicked
        if(index >= 0){
            Pokemon p = pokemons.get(index);
            textName.setText(p.getName());
            textType.setText(p.getType());
            textPokeNumber.setText(Integer.toString(p.getPokeNumber()));
            textDateOfCatch.setText(p.getDateOfCatch().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));   //Local date
            labelTime.setText(Integer.toString(p.getTime()) + " years");
            buttonSave.setEnabled(true);

            try {
                URL url = new URL(this.pokedexImagesUrl + p.getPokeNumber() +".png");
                BufferedImage image = ImageIO.read(url);
                this.labelPokemonImage.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                ImageIcon image = new ImageIcon(getClass().getResource("/images/notFound.png"));
                this.labelPokemonImage.setIcon(getScaledImage(image, 96, 96));
            }

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
        sortPokemons();
        refreshPokemonList();
        listPokemon.setSelectedIndex(0);
    }

    private void sortPokemons() {
        pokemons.sort((a,b) -> {
            if (a.getPokeNumber() > b.getPokeNumber()) return 1;
            else return -1;
        });
    }

    public void deletePokemon() {
        int index = listPokemon.getSelectedIndex();
        pokemons.remove(index);
        refreshPokemonList();
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

        Pokemon pikachu = new Pokemon("Pikachu storm Junior", "lightning", 1, "12/01/1999");
        Pokemon charizard = new Pokemon("Charizard owo", "Fire", 2, "01/03/2019");
        Pokemon Jigllypuff = new Pokemon("Jigllypuff ffff", "Fairy", 3, "01/01/2020");
        Pokemon entei = new Pokemon("Entei u-u", "Fire", 4, "06/10/2021");
        Pokemon magikarp = new Pokemon("Magikarp", "Water", 5, "02/12/2015");
        Pokemon giratinna = new Pokemon("Giratinna", "Grass", 6, "17/05/2017");
        Pokemon abraa = new Pokemon("Abraa Kadabra", "Psychic / Iron ", 7, "17/12/2000");

        screen.addPokemon(charizard);
        screen.addPokemon(pikachu);
        screen.addPokemon(Jigllypuff);
        screen.addPokemon(magikarp);
    }
}