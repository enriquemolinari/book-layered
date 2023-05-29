package layered.main;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import layered.data.Genre;

public class SetUpDb {

  public void init() {
    EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("hsqldb-cinema");

    var em = emf.createEntityManager();

    // delete everything?

    var comedy = new Genre("Comedy");
    var crime = new Genre("Crime");
    var drama = new Genre("Drama");
    var thriller = new Genre("Thriller");
    var mistery = new Genre("Mistery");
    var action = new Genre("Action");



    // em.persist(new Genre());

    em.close();
    emf.close();

  }
}
