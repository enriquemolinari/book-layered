package layered.data.entities;

import static layered.data.entities.SchemaName.SCHEMA_NAME;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import layered.data.api.FullMovieData;
import layered.data.api.MovieCastData;
import layered.data.api.ShortMovieData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = SCHEMA_NAME)
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private LocalDate releaseDate;
  private Integer ageRestriction;
  private Integer duration;
  private String plot;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_movie")
  private List<Cast> casts;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_movie")
  private List<Genre> genres;

  public Movie(String name, LocalDate releaseDate, Integer ageRestriction,
      Integer duration, String plot) {
    this.name = name;
    this.releaseDate = releaseDate;
    this.ageRestriction = ageRestriction;
    this.duration = duration;
    this.plot = plot;
    this.casts = new ArrayList<Cast>();
    this.genres = new ArrayList<Genre>();
  }

  public void addCast(Cast cast) {
    this.casts.add(cast);
  }

  public void addGenre(Genre genre) {
    this.genres.add(genre);
  }

  public ShortMovieData toShortMovieData() {
    return new ShortMovieData(this.id, this.name, this.plot, this.duration,
        this.genresToStringList(), this.releaseDate, this.ageRestriction);
  }

  public FullMovieData toFullMovieData() {
    return new FullMovieData(
        new ShortMovieData(this.id, this.name, this.plot, this.duration,
            this.genresToStringList(), this.releaseDate, this.ageRestriction),
        this.toCastDataList());
  }

  private List<String> genresToStringList() {
    return this.genres.stream().map(g -> g.description())
        .collect(Collectors.toUnmodifiableList());
  }

  private List<MovieCastData> toCastDataList() {
    return this.casts.stream().map(c -> c.toCastData())
        .collect(Collectors.toUnmodifiableList());
  }


}
