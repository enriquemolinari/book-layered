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
public class Show {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private Theatre theatre;
  @ManyToOne
  private Movie movie;
  private LocalDateTime startTime;
  private float price;

  public Show(Movie movie, Theatre theatre, LocalDateTime startTime,
      float price) {
    this.movie = movie;
    this.theatre = theatre;
    this.startTime = startTime;
    this.price = price;
  }

}
