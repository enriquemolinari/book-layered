package layered.main;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import layered.data.services.JpaRatingDataService;

public class Main {

  public static void main(String[] args) {

    // String jdbcUrl = "jdbc:hsqldb:hsql://localhost/xdb";
    // String schemaName = "blabla";
    //
    // try (Connection connection = DriverManager.getConnection(jdbcUrl);
    // Statement statement = connection.createStatement()) {
    //
    // String createSchemaSql = "CREATE SCHEMA " + schemaName;
    // statement.executeUpdate(createSchemaSql);
    //
    // } catch (SQLException e) {
    // System.out.println("Error creating schema: " + e.getMessage());
    // }

    // TODO: no puedo crear el esquema al parecer...
    // si me conecto por jdbc seguramente pueda.

    // var dbSetUp = new SetUpDb();
    // dbSetUp.createSchema();

    EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("hsqldb-cinema");

    new SetUpDb().ddlCreation(emf);

    // var moviesService = new JpaMoviesDataService(emf);

    // for (ShortMovieData md : moviesService.allMovies()) {
    // System.out.println(md.toString());
    // }
    // System.out.println(moviesService.movieDetail(1L));

    // var userService = new JpaUserAuthDataService(emf);
    // var op = userService.login("emolinari", "123");
    //
    // op.ifPresent(arg -> System.out.println(arg.userName()));

    var ratingService = new JpaRatingDataService(emf);
    ratingService.rate(2L, 2L, 5, "bbbb");
    emf.close();
  }

}
