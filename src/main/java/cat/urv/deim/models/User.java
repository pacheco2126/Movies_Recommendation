package cat.urv.deim.models;

import java.util.Objects;

public class User implements Comparable<User>{
    private int idU, peliculesVistes;
    private String data_valoracio;
    private int valoracio;

    public User(int idU) {
        this.idU = idU;
    }
    
    public User(int idU,  int valoracio, String data_valoracio) {
        this.idU = idU;
        this.data_valoracio = data_valoracio;
        this.valoracio = valoracio;
    }

    public int getID() {
        return this.idU;
    }

    public int setID(int idU) {
        return this.idU = idU;
    }

    public String getDataValoracio() {
        return this.data_valoracio;
    }

    public int getValoracio() {
        return this.valoracio;
    }


    public int getPeliculasVistas() {
        return peliculesVistes;
    }
    public void incrementarPeliculasVistas() {
        peliculesVistes++;
    }
    public void setPeliculasVistas(int peliculesVistes) {
        this.peliculesVistes = peliculesVistes;
    }

    @Override
    public int compareTo(User userAComparar) {
        return Integer.compare(this.peliculesVistes, userAComparar.getPeliculasVistas());
    }


    @Override
    public String toString() {
        return "User [idU=" + idU + ", data_valoracio=" + data_valoracio + ", valoracio=" + valoracio + "]";
    }
    
        @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return idU == other.idU;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idU);
    }

    



    
}
