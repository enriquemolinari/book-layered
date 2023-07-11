package layered.data.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import layered.data.api.PlayingData;
import layered.data.api.ShowConfirmed;
import layered.data.api.ShowData;
import layered.data.api.ShowsDataService;

public class JpaShowsDataService implements ShowsDataService {

  private static final String AGG_SHOWS_SEPARATOR = ";";
  private static final int MINUTES_TO_KEEP_RESERVATION = 5;
  // private Jdbi jdbi;
  //
  // public JdbiShowsDataService(Jdbi jdbi) {
  // this.jdbi = jdbi;
  // }

  @Override
  public List<PlayingData> playingNow(LocalDateTime showsUntil) {
    // return jdbi.withHandle(handle -> {
    // var playingNow = handle.createQuery(
    // "SELECT m.id_movie, m.name, m.duration, m.id_cover_image, "
    // + "STRING_AGG(distinct CONCAT(s.id_show, ',', s.start_time, ',', s.price, ',', t.name), '"
    // + AGG_SHOWS_SEPARATOR + "') as shows, "
    // + "STRING_AGG(distinct g.description, ',') as genres "
    // + "FROM movie m " + "LEFT JOIN show s ON m.id_movie = s.id_movie "
    // + "LEFT JOIN movie_genre mg on m.id_movie = mg.id_movie "
    // + "LEFT JOIN genre g ON mg.id_genre = g.id_genre "
    // + "LEFT JOIN theatre t ON t.id_theatre = s.id_theatre "
    // + "WHERE s.start_time <= :until "
    // + "GROUP BY m.id_movie, m.name, m.duration, m.id_cover_image "
    // + "ORDER BY m.id_movie")
    // .bind("until", showsUntil).mapToMap().list();
    //
    // return playingNow.stream().map(l -> {
    //
    // var shows =
    // Arrays.asList(l.get("shows").toString().split(AGG_SHOWS_SEPARATOR))
    // .stream().map(s -> ShortShowData.fromString(s))
    // .collect(Collectors.toUnmodifiableList());
    //
    // return new PlayingData(Long.valueOf(l.get("id_movie").toString()),
    // l.get("name").toString(),
    // Integer.valueOf(l.get("duration").toString()),
    // l.get("id_cover_image").toString(),
    // List.of(l.get("genres").toString()), shows);
    // }).collect(Collectors.toUnmodifiableList());
    //
    // });
    return null;
  }

  @Override
  public ShowData show(Long idShow) {
    // return jdbi.withHandle(handle -> {
    // var show = handle.createQuery(
    // "select m.name, m.id_cover_image, m.duration, t.id_theatre, t.name as tname"
    // + ", s.start_time, s.price, "
    // + "b.reserved, b.reserved_until, b.confirmed, b.id_seat, se.number "
    // + " from show s, booking b, seat se, movie m, theatre t "
    // + " where s.id_show = :idshow and m.id_movie = s.id_movie"
    // + " and s.id_show = b.id_show"
    // + " and s.id_theatre = se.id_theatre"
    // + " and se.id_theatre = t.id_theatre"
    // + " and b.id_seat = se.id_seat")
    // .bind("idshow", idShow).mapToMap().list();
    //
    // var seats = new ArrayList<SeatData>();
    // var movieName = show.get(0).get("name").toString();
    // var coverImage = show.get(0).get("id_cover_image").toString();
    // var movieDuration =
    // Integer.valueOf(show.get(0).get("duration").toString());
    // var startTime = new ToLocalDate(show.get(0).get("start_time")).val();
    // var idTheatre = Long.valueOf(show.get(0).get("id_theatre").toString());
    // var theatreName = show.get(0).get("tname").toString();
    // var price = (BigDecimal) show.get(0).get("price");
    //
    // for (Map<String, Object> map : show) {
    // seats.add(new SeatData(Long.valueOf(map.get("id_seat").toString()),
    // Integer.valueOf(map.get("number").toString()),
    // Boolean.valueOf(map.get("reserved").toString()) == true
    // && (LocalDateTime.now().isBefore(
    // new ToLocalDate(map.get("reserved_until")).val())),
    // Boolean.valueOf(map.get("confirmed").toString())));
    // }
    //
    // return new ShowData(idShow, startTime, movieName, movieDuration,
    // coverImage, idTheatre, theatreName, seats, price.floatValue());
    // });
    return null;
  }

  @Override
  public void reserve(Long idShow, Long idUser, List<Long> idSeats) {
    // jdbi.useTransaction(handle -> {
    // var seatsChosen = handle.createQuery(
    // "select id_show, id_seat, reserved, confirmed, reserved_until "
    // + "from booking "
    // + "where id_show = :idshow and id_seat in (<idseats>) for update")
    // .bind("idshow", idShow).bindList("idseats", idSeats).mapToMap()
    // .list();
    //
    // checkReservedOrConfirmed(seatsChosen);
    //
    // handle.createUpdate(
    // "UPDATE booking SET id_user = :iduser, reserved = true, reserved_until = :until "
    // + "where id_show = :idshow and id_seat in (<idseats>)")
    // .bind("iduser", idUser).bind("idshow", idShow)
    // .bind("until",
    // LocalDateTime.now().plusMinutes(MINUTES_TO_KEEP_RESERVATION))
    // .bindList("idseats", idSeats).execute();
    // });
  }

  @Override
  public boolean isReservedBy(Long idShow, Long idUser, List<Long> idSeats) {
    // return jdbi.withHandle(handle -> {
    // var seatsChosen = handle.createQuery(
    // "select id_show, id_user, id_seat, reserved, confirmed, reserved_until "
    // + "from booking "
    // + "where id_show = :idshow and id_seat in (<idseats>)")
    // .bind("idshow", idShow).bindList("idseats", idSeats).mapToMap()
    // .list();
    //
    // if (seatsChosen.size() == 0) {
    // return false;
    // }
    //
    // return reservedByUser(seatsChosen, idUser);
    // });
    return false;
  }

  private void checkReservedOrConfirmed(List<Map<String, Object>> seatsChosen) {
    /*
     * if (seatsChosen.stream().anyMatch(m -> { return (new ToBoolean(m.get("reserved")).val() == true
     * && LocalDateTime .now().isBefore(new ToLocalDate(m.get("reserved_until")).val())) || new
     * ToBoolean(m.get("confirmed")).val() == true; })) { throw new DataException(
     * "At least one of the selected seats has just been reserved"); }
     */

  }

  private boolean reservedByUser(List<Map<String, Object>> seatsChosen,
      Long idUser) {
    /*
     * return seatsChosen.stream().allMatch(m -> { var idUserdb = m.get("id_user"); if (idUserdb ==
     * null) { return false; } return idUser.equals(Long.valueOf((Integer) idUserdb)) && new
     * ToBoolean(m.get("reserved")).val() == true && new ToBoolean(m.get("confirmed")).val() == false &&
     * LocalDateTime .now().isBefore(new ToLocalDate(m.get("reserved_until")).val()); });
     */
    return false;
  }

  @Override
  public ShowConfirmed confirm(Long idShow, Long idUser, List<Long> idSeats,
      float totalAmount, int newPoints) {
    // return jdbi.inTransaction(handle -> {
    // var seatsChosen = handle.createQuery(
    // "select id_show, id_user, id_seat, reserved, confirmed, reserved_until "
    // + "from booking "
    // + "where id_show = :idshow and id_seat in (<idseats>) for update")
    // .bind("idshow", idShow).bindList("idseats", idSeats).mapToMap()
    // .list();
    //
    // if (!reservedByUser(seatsChosen, idUser)) {
    // throw new DataException("You are not allowed to confirm the seats");
    // }
    //
    // // confirm all the seats
    // handle
    // .createUpdate("UPDATE booking SET confirmed = true "
    // + "where id_show = :idshow and id_seat in (<idseats>)")
    // .bind("idshow", idShow).bindList("idseats", idSeats).execute();
    //
    // // calculate points earned by the user
    // handle
    // .createUpdate("UPDATE users SET points = points + :points "
    // + "where id_user = :iduser")
    // .bind("iduser", idUser).bind("points", newPoints).execute();
    //
    // // create the sale
    // var timePayed = LocalDateTime.now();
    // var saleId = handle
    // .createUpdate("INSERT INTO sale(id_show, amount, id_user, payed_at) "
    // + "values(:idshow, :amount, :iduser, :date)")
    // .bind("idshow", idShow).bind("amount", totalAmount)
    // .bind("iduser", idUser).bind("date", timePayed)
    // .executeAndReturnGeneratedKeys().mapTo(Long.class).one();
    //
    // return new ShowConfirmed(saleId, timePayed);
    // });
    return null;
  }
}
