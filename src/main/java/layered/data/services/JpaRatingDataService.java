package layered.data.services;

import jakarta.persistence.EntityManagerFactory;
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
  public void rate(Long idUser, Long idMovie, int userValue, String comment) {
    checkUserHasAlreadyRated(idUser, idMovie);

    // insert into ratind details
    // [value, totalVotes] = actualRating(idmovie);
    // newValue = (actualvalue + uservalue) / (totalVotes + 1)
    // update o insert

    var em = emf.createEntityManager();
    var tx = em.getTransaction();

    try {
      tx.begin();
      var movie = em.getReference(Movie.class, idMovie);
      var user = em.getReference(User.class, idUser);

      // TODO: esto deberia lockear... Verificar que solo lockeo esta pelicula.
      var q = em.createQuery("from Rating r where r.movie.id = :idmovie",
          Rating.class);
      q.setParameter("idmovie", idMovie);

      em.persist(new RatingDetail(movie, user, comment, userValue));

      var ratingList = q.getResultList();
      if (ratingList.size() == 0) {
        em.persist(new Rating(movie, userValue, 1, userValue));
      } else {
        Rating r = ratingList.get(0);
        r.vote(userValue);
      }
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }



    // jdbi.useTransaction(handle -> {
    // // there should be a lock here to make this work properly
    // // I will leave this without implemented it properly as it is not the purpose of this code
    // // reader: remember that locking using 'for update' does not work when use agregation functions
    // var actualValues = handle
    // .createQuery(
    // "SELECT SUM(value) as total_sum, count(value) as total_count "
    // + "from rating_detail where id_movie = :idmovie")
    // .bind("idmovie", idMovie).mapToMap().one();
    //
    // handle.createUpdate(
    // "INSERT INTO rating_detail(id_movie, id_user, value, comment, created_at) "
    // + "values(:idmovie, :iduser, :value, :comment, :date)")
    // .bind("idmovie", idMovie).bind("iduser", idUser).bind("value", value)
    // .bind("comment", comment).bind("date", LocalDateTime.now()).execute();
    //
    // var existARate = handle
    // .createQuery(
    // "SELECT 1 as exist_rate from rating where id_movie = :idmovie")
    // .bind("idmovie", idMovie).mapTo(Integer.class).findOne();
    //
    // existARate.ifPresentOrElse((p) -> {
    // var newValue = ((new BigDecimal((Long) actualValues.get("total_sum")))
    // .add(new BigDecimal(value))).floatValue()
    // / ((Long) actualValues.get("total_count") + 1);
    //
    // handle
    // .createUpdate(
    // "UPDATE rating set value = :newvalue where id_movie = :idmovie")
    // .bind("idmovie", idMovie).bind("newvalue", newValue).execute();
    // }, () -> {
    // handle.createUpdate(
    // "INSERT INTO rating (id_movie, value) values(:idmovie, :initialValue)")
    // .bind("idmovie", idMovie).bind("initialValue", value).execute();
    // });
    // });
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
}
