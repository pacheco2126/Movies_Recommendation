package cat.urv.deim.models;

import java.util.Objects;

public class Pelicula implements Comparable<Pelicula> {
    private int idP, numValoraciones;


    public Pelicula(int idP) {
        this.idP = idP;
    }

    public int getID() {
        return this.idP;
    }

    public int setID(int idP) {
        return this.idP = idP;
    }

    //numero de valoraciones máximas
    public int getNumValoraciones() {
        return numValoraciones;
    }

    public void incrementarValoraciones() {
        numValoraciones++;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pelicula other = (Pelicula) obj;
        return idP == other.idP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idP);
    }


    @Override
    public int compareTo(Pelicula peliculaAComparar) {
        int resultat = this.idP - peliculaAComparar.getID();
        return resultat;
    }

    @Override
    public String toString() {
        return "Pelicula [idP=" + idP + "]";
    }

} 