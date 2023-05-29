package layered.data;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
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
  @OneToMany
  private List<Cast> casts;
  @OneToMany
  private List<Genre> genres;


  public Movie(String name) {
    this.name = name;
  }

  public String name() {
    return this.name;
  }

}
