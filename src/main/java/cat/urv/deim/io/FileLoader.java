
package cat.urv.deim.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cat.urv.deim.models.IPelicules;
import cat.urv.deim.models.IUser;
import cat.urv.deim.models.Pelicula;
import cat.urv.deim.models.User;
import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.ElementRepetit;
import cat.urv.deim.models.ILlista;
import cat.urv.deim.models.IMultiLlistaGenerica;

public class FileLoader {

    public static void carregarFitxerPelicules(String path, IPelicules llistaPeli, IUser llistaUser, IMultiLlistaGenerica multilist, ILlista TopPelis) throws ElementRepetit, ElementNoTrobat{
   
        File file = new File(path);
        BufferedReader br = null;
        Pelicula peli = null;
        User user = null;
        int peliculasInsertadas = 0;


        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {

                if (line.endsWith(":")) {
              
                    // Si la película actual no es null, la insertamos en TOPpeliculas
                    if(peli !=null)  {
                        if (peliculasInsertadas < 10) { // Si la lista tiene menos de 10 películas, insertamos la película actual
                            if (!TopPelis.buscar(peli)) { 
                                TopPelis.inserir(peli);
                                peliculasInsertadas++;
                           //   System.out.println("La peli "+ peli.getID() +" insertada, porque lista < 10 y tiene " +peli.getNumValoraciones()+" Maximas valoraciones\n");
                            }

                        } else {
                                    
                            // Obtener la película con la menor valoración de las insertadas
                            Pelicula peliculaMenorValoracion = null;
                            for (int i = 0; i < TopPelis.longitud(); i++) { 
                                Pelicula pelicula = (Pelicula) TopPelis.elements()[i]; // Si la película actual tiene menos valoraciones que la película con la menor valoración
                                if (peliculaMenorValoracion == null || pelicula.getNumValoraciones() < peliculaMenorValoracion.getNumValoraciones()) { 
                                    peliculaMenorValoracion = pelicula;
                                }
                            }
                            // Verificar si la película actual tiene mejor valoración o más usuarios que mejor han valorado, que la película con la menor valoración
                            if (peli.compareTo(peliculaMenorValoracion) > 0 || (peli.compareTo(peliculaMenorValoracion) == 0 && peli.getNumValoraciones() > peliculaMenorValoracion.getNumValoraciones())) {
                              
                                // Remover la película con la menor valoración y añadir la película actual
                                TopPelis.esborrar(peliculaMenorValoracion);
                                TopPelis.inserir(peli);

                            }
                        }
                    }
                    // Crear una nueva película
                    int IdP = Integer.parseInt(line.substring(0, line.length() - 1)); // Eliminar el último carácter (:) y convertir el string a int
                    peli = new Pelicula(IdP);

                    llistaPeli.inserir(IdP, peli);

                } else {
                    String[] parts = line.split(",");
                    int IdU = Integer.parseInt(parts[0]);
                    int valoracio = Integer.parseInt(parts[1]);
                    String data_valoracio = parts[2];
                    user = new User(IdU, valoracio, data_valoracio);
                    
                    llistaUser.inserir(IdU, user);


                    if (peli != null && user != null) { 
                        user.incrementarPeliculasVistas();  // Incrementar el número de películas vistas por el usuario
                        multilist.inserir(peli, user);
                        
                        if (valoracio == 5) {
                            // Incrementar el número de valoraciones (5) de la película
                            peli.incrementarValoraciones();
                        }                        
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}