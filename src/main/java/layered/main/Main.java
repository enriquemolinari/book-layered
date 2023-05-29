package layered.main;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import layered.data.Movie;

public class Main {

  public static void main(String[] args) {

    EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("hsqldb-cinema");

    var em = emf.createEntityManager();

    Movie m = em.find(Movie.class, 1L);
    System.out.println(m.name());


    em.close();
    emf.close();
  }

}
