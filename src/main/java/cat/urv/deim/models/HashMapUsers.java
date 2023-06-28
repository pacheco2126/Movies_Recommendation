package cat.urv.deim.models;

import cat.urv.deim.exceptions.ElementNoTrobat;

public class HashMapUsers implements IUser{

    private HashMapIndirecte<Integer, Integer> hashMap;

    public HashMapUsers() {
        hashMap = new HashMapIndirecte<>(10);
    }

    @Override
    public void inserir(int idU, User user) {
        hashMap.inserir(idU, idU);
    }

    @Override
    public boolean buscar(int idU) throws ElementNoTrobat {
        return hashMap.buscar(idU);
    }

    @Override
    public boolean esBuida() {
        return hashMap.esBuida();
    }

    @Override
    public int longitud() {
        return hashMap.longitud();
    }

    @Override
    public User[] llistaUsers() throws ElementNoTrobat {
        Object[] claus = hashMap.obtenirClaus();
        User[] users = new User[hashMap.longitud()];
        int index = 0;
        for (Object c : claus) {
            try {
                users[index] = new User(hashMap.element((Integer) c));
            } catch (ElementNoTrobat e) {
                e.printStackTrace();
            }
            index++;
        }
        return users;
    }


    
}
