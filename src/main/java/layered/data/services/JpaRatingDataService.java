package layered.data.services;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import layered.data.api.DataException;
import layered.data.api.RatingData;
import layered.data.api.RatingDataService;
import layered.data.entities.Movie;
import layered.data.entities.Rating;
import layered.data.entities.RatingDetail;
import layered.data.entities.User;

public class JpaRatingDataService implements RatingDataService {

  private EntityManagerFactory emf;

  public JpaRatingDataService(EntityManagerFactory emf) {
    this.emf = emf;
  }

  @Override
  public void rate(Long idUser, Long idMovie, int valueAssigned,
      String comment) {
    var em = emf.createEntityManager();
    var tx = em.getTransaction();

    var movie = em.getReference(Movie.class, idMovie);
    var user = em.getReference(User.class, idUser);

    try {
      tx.begin();

      var ratingList = ratingForMovie(idMovie, em);

      checkUserHasAlreadyRated(idUser, idMovie);

      saveNewRating(valueAssigned, comment, em, movie, user, ratingList);

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw e;
    } finally {
      em.close();
    }
  }

  @Override
  public RatingData rate(Long idMovie) {
    // return jdbi.withHandle(handle -> {
    // var ratingValue = handle
    // .createQuery("SELECT value from rating where id_movie = :idmovie")
    // .bind("idmovie", idMovie).mapTo(Float.class).findOne();
    //
    // var ratingDetail = handle.createQuery(
    // "select username, comment, value, created_at from rating_detail rd, users u where rd.id_user =
    // u.id_user and id_movie = :idmovie")
    // .bind("idmovie", idMovie).mapToMap().list();
    //
    // if (ratingValue.isEmpty()) {
    // return new RatingData(0L, 0F, List.of());
    // }
    //
    // var details = ratingDetail.stream().map(rd -> {
    // return new RatingDetail(rd.get("username").toString(),
    // new ToLocalDate(rd.get("created_at")).val(),
    // Integer.valueOf(rd.get("value").toString()),
    // rd.get("comment").toString());
    //
    // }).collect(Collectors.toUnmodifiableList());
    //
    // return new RatingData(Long.valueOf(ratingDetail.size()),
    // ratingValue.get(), details);
    // });
    return null;
  }

  @Override
  public void checkUserHasAlreadyRated(Long idUser, Long idMovie) {
    try (var em = emf.createEntityManager()) {
      var q = em.createQuery(
          "select count(*) from RatingDetail rd where rd.user.id = :userid and rd.movie.id = :movieid",
          Long.class);

      q.setParameter("userid", idUser);
      q.setParameter("movieid", idMovie);

      var count = q.getSingleResult();

      if (count > 0) {
        throw new DataException("You have already voted");
      }
    }
  }

  private void saveNewRating(int valueAssigned, String comment,
      EntityManager em, Movie movie, User user, List<Rating> ratingList) {
    // save ratingDetail for user
    em.persist(new RatingDetail(movie, user, comment, valueAssigned));

    if (ratingList.size() == 0) {
      // ratingList == 0 means this is the first vote for the movie
      em.persist(new Rating(movie, valueAssigned, 1, valueAssigned));
    } else {
      // there is a rate for this movie, calculate the new value
      ratingList.get(0).calculateNewRate(valueAssigned);
    }
  }

  private List<Rating> ratingForMovie(Long idMovie, EntityManager em) {
    var ratingQuery = em
        .createQuery("from Rating r where r.movie.id = :idmovie", Rating.class);
    ratingQuery.setParameter("idmovie", idMovie);

    // locking to prevent race conditions during voting transaction
    ratingQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
    var ratingList = ratingQuery.getResultList();
    return ratingList;
  }

}
