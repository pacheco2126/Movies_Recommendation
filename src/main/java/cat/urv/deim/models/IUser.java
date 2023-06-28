package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public interface IUser {
    
    public void inserir(int idU, User user) throws ElementNoTrobat;

    public boolean buscar(int idU) throws ElementNoTrobat;

    public boolean esBuida();

    public int longitud();

    public User[] llistaUsers() throws ElementNoTrobat;
}
