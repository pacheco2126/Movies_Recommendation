package cat.urv.deim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;
import cat.urv.deim.io.FileLoader;
import cat.urv.deim.models.DLLOrdenada;
import cat.urv.deim.models.HashMapPelicules;
import cat.urv.deim.models.HashMapUsers;
import cat.urv.deim.models.ILlista;
import cat.urv.deim.models.IMultiLlistaGenerica;
import cat.urv.deim.models.IPelicules;
import cat.urv.deim.models.IUser;
import cat.urv.deim.models.MultiLlista;
import cat.urv.deim.models.Pelicula;
import cat.urv.deim.models.User;

public class MyTest {

    IPelicules hashMapPeli;
    IUser hashMapUser;
    IMultiLlistaGenerica<Pelicula, User> multilist;
    ILlista TopPelis;


    @BeforeEach
    void setup() throws ElementRepetit, ElementNoTrobat {
        hashMapPeli = new HashMapPelicules();
        hashMapUser = new HashMapUsers();
        multilist = new MultiLlista<>(); // Aquí inicializas multilist con una instancia de MultiLlista o cualquier otra implementación de IMultiLlistaGenerica
        TopPelis = new DLLOrdenada();
        FileLoader.carregarFitxerPelicules("movie_users_10_20.txt", hashMapPeli, hashMapUser, multilist, TopPelis);
    
    }


    @Test
    public void testCarregarPelicules() throws ElementNoTrobat {

        assertEquals(18, hashMapPeli.longitud());

        assertEquals(421, hashMapUser.longitud());
    }
 
    //metodo que te devuelve los usuarios que han visto una peli en concreto
    @Test
    public void testFila() throws ElementNoTrobat, ElementRepetit {
        setup();
    
        int peliculaId = 28;
    
        Pelicula pelicula = new Pelicula(peliculaId);
    
        List<User> usuarios = multilist.fila(pelicula);
       System.out.println("La pelicula " + peliculaId + " ha sido valorada por los usuarios: " + usuarios + "\n");
    }
    

    //metodo que te devuelve la pelis que un usuario ha visto
    @Test
    public void testColumna() throws ElementNoTrobat, ElementRepetit {
        setup();
        int userId = 1533635;
    
        User user = new User(userId);
    
        List<Pelicula> peliculas = multilist.columna(user);
        System.out.println("El usuario " + userId + " ha visto la/s pelicula/s: " + peliculas + "\n");
    }

        // metodo que te devuelve los 10 usuarios que mas peliculas han visto, es decir usuarios expertos, que su valoración es más fiable
        @Test
        public List<User>  TopUsuariosMasPeliculasVistas() throws ElementNoTrobat {
            List<User> usuariosMasPeliculasVistas = new ArrayList<>();

            for (int i = 0; i < hashMapUser.longitud(); i++) {
                User user = (User) hashMapUser.llistaUsers()[i];
                int numPeliculasVistas = multilist.columna(user).size();
                user.setPeliculasVistas(numPeliculasVistas);
                usuariosMasPeliculasVistas.add(user);
            }

            Collections.sort(usuariosMasPeliculasVistas, new Comparator<User>() {
                @Override
                public int compare(User user1, User user2) {
                    return Integer.compare(user2.getPeliculasVistas(), user1.getPeliculasVistas());
                }
            });

            if (usuariosMasPeliculasVistas.size() > 10) {
                usuariosMasPeliculasVistas = usuariosMasPeliculasVistas.subList(0, 10);
            }

            //printear
            System.out.print("Los 10 usuarios que más películas han visto son: \n");
            for (User user : usuariosMasPeliculasVistas) {
                System.out.print("El usuario "+user.getID() + " con "+  user.getPeliculasVistas() + " peliculas vistas\n");
            }
            return usuariosMasPeliculasVistas;
        }






    // metodo que te devuelve 3 recomendaciones para un usuario segun, TopUsuarios, TopPelis y las peliculas que ha visto el usuario
    @Test
    public void recomendacio() throws ElementRepetit, ElementNoTrobat {
        setup();
        int userId = 1533635;
    
        User user = new User(userId);
    
        // Obtener la lista de películas vistas por el usuario
        List<Pelicula> peliculasVistas = multilist.columna(user);
    
        // Crear una lista para almacenar las recomendaciones
        List<Pelicula> recomendaciones = new ArrayList<>();
    
        // Recorrer cada película vista por el usuario
        for (Pelicula pelicula : peliculasVistas) {

            // Obtener la lista de usuarios que han visto la película
            List<User> usuariosQueVieronPelicula = multilist.fila(pelicula);
    
            // Recorrer cada usuario que ha visto la película
            for (User otroUsuario : usuariosQueVieronPelicula) {
                // Evitar comparar con el mismo usuario
                if (!otroUsuario.equals(user)) {
                    // Verificar si el otro usuario ha visto una película que el usuario no ha visto
                    List<Pelicula> peliculasOtroUsuario = multilist.columna(otroUsuario);
                    List<User> topUsuarios = TopUsuariosMasPeliculasVistas();
                    
                    for (Pelicula peliculaOtroUsuario : peliculasOtroUsuario) {
                        //priorizamos primero si el ususario está en el Top 10 de usuarios que más peliculas han visto,es decir los expertos
                        if((topUsuarios.contains(otroUsuario)) && (!recomendaciones.contains(peliculaOtroUsuario)) && (!peliculasVistas.contains(peliculaOtroUsuario))) recomendaciones.add(peliculaOtroUsuario);
                        else if (!peliculasVistas.contains(peliculaOtroUsuario) && !recomendaciones.contains(peliculaOtroUsuario)&& (!peliculasVistas.contains(peliculaOtroUsuario)))  recomendaciones.add(peliculaOtroUsuario);
                    }
                }
            }
        }
    
        // Filtrar las recomendaciones según el TopPelis
        List<Pelicula> recomendacionesFiltradas = new ArrayList<>();
        Object[] elementos = TopPelis.elements();
        for (Object elemento : elementos) {
            if (recomendaciones.contains(elemento)) {
                recomendacionesFiltradas.add((Pelicula) elemento);
            }
        }

        // Ordenar las recomendaciones según el número de valoraciones
        Collections.sort(recomendacionesFiltradas, (p1, p2) -> Integer.compare(p2.getNumValoraciones(), p1.getNumValoraciones()));

        // Limitar el número de recomendaciones a 3 (las mejores)
        List<Pelicula> mejoresRecomendaciones = recomendacionesFiltradas.subList(0, Math.min(3, recomendacionesFiltradas.size()));

        // Imprimir las recomendaciones
        System.out.println("Recomendaciones para el usuario " + userId + ":");
        for (Pelicula recomendacion : mejoresRecomendaciones) {
            System.out.println("\t"+recomendacion);
        }
        System.out.println("\nLa recomendacion se basa en las comunes con los usuarios que han visto la/s pelicula/s que ha visto el usuario " + userId );
        System.out.println("Se comprueba si el usuario comun es experto (ha visto muchas peliculas), Y se filtra segun el Top 10 mejores peliculas");

    }
    
    // metodo que te devuelve las 10 mejores peliculas, segun las maximas valoraciones que tienen
    @Test 
    public void peliculasTOP() throws ElementRepetit, ElementNoTrobat {
        setup();
    
        // Imprimir la lista ordenada de las 10 mejores películas
        System.out.println("\nLista de las 10 mejores películas: \n");
    
        Object[] elementos = TopPelis.elements();
        for (int i = 0; i < 10 && i < elementos.length; i++) {
            Pelicula pelicula = (Pelicula) elementos[i];
          System.out.println("\t"+(10 - i) + ". " + pelicula + " con " +pelicula.getNumValoraciones() + " valoraciones");
        }
    }
    
    

}
