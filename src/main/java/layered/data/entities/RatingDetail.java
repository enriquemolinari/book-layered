package layered.data.entities;

import static layered.data.entities.SchemaName.SCHEMA_NAME;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = SCHEMA_NAME)
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class RatingDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private Movie movie;
  @ManyToOne
  private User user;
  private int value;
  private String comment;
  private LocalDateTime createdAt;

  public RatingDetail(Movie movie, User user, String comment, int value) {
    this.movie = movie;
    this.user = user;
    this.comment = comment;
    this.value = value;
    this.createdAt = LocalDateTime.now();
  }

}
