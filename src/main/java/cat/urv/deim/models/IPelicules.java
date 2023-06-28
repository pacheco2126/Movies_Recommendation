package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public interface IPelicules {

    // Metode per a afegir una pelicula
    public void inserir(int idP, Pelicula peli) throws ElementNoTrobat;

    // Metode per a saber si una pelicula esta guardada
    public boolean buscar(int idP) throws ElementNoTrobat;

    // Metode per saber si hi ha alguna pelicula guardada a l'estructura
    public boolean esBuida();

    // Metode per saber quantes pelicules hi ha emmagatzemades
    public int longitud();

    // Funcio que retorna totes les pelicules de l'estructura
    public Pelicula[] llistaPelicules() throws ElementNoTrobat;

}