package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class HashMapPelicules implements IPelicules {


    private HashMapIndirecte<Integer, Integer> hashMap;

    public HashMapPelicules() {
        hashMap = new HashMapIndirecte<>(10);
    }

    @Override
    public void inserir(int idP, Pelicula peli) {
        hashMap.inserir(idP, idP);
    }


    @Override
    public boolean buscar(int idP) throws ElementNoTrobat {
        return hashMap.buscar(idP);
    }

    @Override
    public boolean esBuida() {
        return hashMap.esBuida();
    }


    @Override
    public Pelicula[] llistaPelicules() {
        Object[] claus = hashMap.obtenirClaus();
        Pelicula[] peliculas = new Pelicula[hashMap.longitud()];
        int index = 0;
        for (Object c : claus) {
            try {
                peliculas[index] = new Pelicula(hashMap.element((Integer) c));
            } catch (ElementNoTrobat e) {
                e.printStackTrace();
            }
            index++;
        }
        return peliculas;
    }

    @Override
    public int longitud() {
        return hashMap.longitud();
    }

}