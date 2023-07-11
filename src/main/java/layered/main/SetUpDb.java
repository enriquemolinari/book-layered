package layered.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import layered.data.entities.Booking;
import layered.data.entities.Cast;
import layered.data.entities.Genre;
import layered.data.entities.Movie;
import layered.data.entities.Person;
import layered.data.entities.Rating;
import layered.data.entities.RatingDetail;
import layered.data.entities.Seat;
import layered.data.entities.Show;
import layered.data.entities.Theatre;
import layered.data.entities.User;

public class SetUpDb {

  private static final int BATCH_SIZE = 20;

  public SetUpDb() {}

  public void ddlCreation(EntityManagerFactory emf) {
    var em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();

      var comedy = new Genre("Comedy");
      var crime = new Genre("Crime");
      var drama = new Genre("Drama");
      var thriller = new Genre("Thriller");
      var mistery = new Genre("Mistery");
      var action = new Genre("Action");

      var jake = new Person("Jake", "White", "jake@mymovies.com");
      var josh = new Person("Josh", "Blue", "josh@mymovies.com");
      var nervan = new Person("Nervan", "Allister", "nervan@mymovies.com");
      var ernest = new Person("Ernest", "Finey", "ernest@mymovies.com");
      var enrique =
          new Person("Enrique", "Molinari", "enrique.molinari@gmail.com");
      var josefina = new Person("Josefina", "Simini", "jsimini@gmail.com");
      var lucia = new Person("Lucia", "Molimini", "lu@mymovies.com");
      var nico = new Person("Nicolas", "Molimini", "nico@mymovies.com");
      var camilo = new Person("Camilo", "Fernandez", "cami@mymovies.com");
      var franco = new Person("Franco", "Elchow", "franco@mymovies.com");
      var michael = new Person("Michael", "Martinez", "michael@mymovies.com");
      var michell = new Person("Michell", "Orenson", "michell@mymovies.com");
      var craig = new Person("Craig", "Wagemen", "craig@mymovies.com");
      var judith = new Person("Judith", "Zavele", "judith@mymovies.com");
      var andre = new Person("Andre", "Lambert", "andre@mymovies.com");
      var colin = new Person("Colin", "Clifferd", "andre@mymovies.com");

      var craigCast = new Cast(craig, null, 'D');
      var judithCast = new Cast(judith, null, 'D');
      var andreCast = new Cast(andre, null, 'D');
      var colinCast = new Cast(colin, null, 'D');

      var jakeCast = new Cast(jake, "Daniel Finne", 'A');
      var joshCast = new Cast(josh, "Norber Carl", 'A');
      var ernestCast = new Cast(ernest, "Edward Blomsky (senior)", 'A');
      var nervanCast = new Cast(nervan, "Edward Blomsky (young)", 'A');
      var camiloCast = new Cast(camilo, "Judy", 'A');
      var francoCast = new Cast(franco, "George", 'A');
      var michaelCast = new Cast(michael, "Mike", 'A');
      var michellCast = new Cast(michell, "Teressa", 'A');

      var schoolMovie = new Movie("Rock in the School", LocalDate.now(), 13,
          109,
          "A teacher tries to teach Rock & Roll music and history to elementary school kids");
      schoolMovie.addCast(colinCast);
      schoolMovie.addCast(jakeCast);
      schoolMovie.addCast(joshCast);
      schoolMovie.addCast(camiloCast);
      schoolMovie.addGenre(comedy);
      schoolMovie.addGenre(drama);
      em.persist(schoolMovie);

      var fishMovie = new Movie("Small Fish", LocalDate.now().minusDays(1), 18,
          125, "A caring father teaches life values while fishing.");
      fishMovie.addCast(andreCast);
      fishMovie.addCast(ernestCast);
      fishMovie.addCast(nervanCast);
      fishMovie.addGenre(action);
      fishMovie.addGenre(mistery);
      fishMovie.addGenre(crime);
      em.persist(fishMovie);

      var teaMovie = new Movie("Crash Tea", LocalDate.now().minusDays(3), 13,
          105, "A documentary about tea.");
      teaMovie.addCast(craigCast);
      teaMovie.addCast(michaelCast);
      teaMovie.addCast(michellCast);
      teaMovie.addGenre(comedy);
      em.persist(teaMovie);

      var runningMovie = new Movie("Running far Away", LocalDate.now(), 6, 105,
          "José a sad person run away from his town looking for new adventures.");
      runningMovie.addCast(judithCast);
      runningMovie.addCast(francoCast);
      runningMovie.addGenre(thriller);
      runningMovie.addGenre(action);
      em.persist(runningMovie);

      var ta = new Theatre("Theatre A");
      var tb = new Theatre("Theatre B");

      // Seats from Theatre A
      for (int i = 1; i <= 30; i++) {
        ta.addSeat(new Seat(i));
      }
      em.persist(ta);
      em.flush();

      // Seats from Theatre B
      for (int i = 1; i <= 50; i++) {
        tb.addSeat(new Seat(i));
      }
      em.persist(tb);
      em.flush();

      var show1 = new Show(fishMovie, ta,
          LocalDateTime.now().plusDays(1).plusHours(1), 21f);
      em.persist(show1);

      var show2 = new Show(fishMovie, ta,
          LocalDateTime.now().plusDays(1).plusHours(4), 21f);
      em.persist(show2);

      var show3 = new Show(schoolMovie, tb,
          LocalDateTime.now().plusDays(2).plusHours(1), 19f);
      em.persist(show3);

      var show4 = new Show(schoolMovie, tb,
          LocalDateTime.now().plusDays(2).plusHours(5), 19f);
      em.persist(show4);

      var show5 = new Show(teaMovie, ta,
          LocalDateTime.now().plusDays(2).plusHours(2), 19f);
      em.persist(show5);

      var show6 =
          new Show(runningMovie, tb, LocalDateTime.now().plusHours(2), 19f);
      em.persist(show6);

      // Bookings
      @SuppressWarnings(value = "unchecked")
      List<Show> shows =
          em.createQuery("from Show order by id desc").getResultList();
      for (Show show : shows) {

        int i = 0;
        for (Seat seat : ta.seats()) {
          var booking = new Booking(show, seat);
          em.persist(booking);
          if (i % BATCH_SIZE == 0) {
            em.flush();
            em.clear();
          }
          i++;
        }
      }

      // users
      var eu = new User(enrique, "emolinari", "123");
      em.persist(eu);
      var ju = new User(josefina, "jsimini", "123");
      em.persist(ju);
      em.persist(new User(nico, "nico", "123"));
      em.persist(new User(lucia, "lucia", "123"));

      var rd1 = new RatingDetail(schoolMovie, eu, "Great movie!", 5);
      em.persist(rd1);
      var rd2 = new RatingDetail(fishMovie, eu, "Fantastic !!", 4);
      em.persist(rd2);

      em.persist(new Rating(schoolMovie, 5f, 1, 5f));
      em.persist(new Rating(fishMovie, 4f, 1, 4f));

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw new RuntimeException(e);
    } finally {
      if (em != null && em.isOpen()) {
        em.close();
      }
    }
  }
}
