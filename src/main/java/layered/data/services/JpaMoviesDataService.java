package layered.data.services;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManagerFactory;
import layered.data.api.FullMovieData;
import layered.data.api.MoviesDataService;
import layered.data.api.ShortMovieData;
import layered.data.entities.Movie;

public class JpaMoviesDataService implements MoviesDataService {

  private EntityManagerFactory emf;

  public JpaMoviesDataService(EntityManagerFactory emf) {
    this.emf = emf;
  }

  @Override
  public List<ShortMovieData> allMovies() {
    try (var em = emf.createEntityManager()) {
      List<Movie> movies =
          em.createQuery("from Movie", Movie.class).getResultList();

      return movies.stream().map(m -> m.toShortMovieData())
          .collect(Collectors.toUnmodifiableList());
    }
  }

  @Override
  public FullMovieData movieDetail(Long idMovie) {
    try (var em = emf.createEntityManager()) {
      var movie = em.find(Movie.class, idMovie);
      return movie.toFullMovieData();
    }
  }
}
