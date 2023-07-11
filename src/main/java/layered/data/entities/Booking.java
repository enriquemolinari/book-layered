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
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private Show show;
  @ManyToOne
  private Seat seat;
  @ManyToOne
  private User user;
  private boolean reserved;
  private LocalDateTime reservedUntil;
  private boolean confirmed;

  public Booking(Show show, Seat seat) {
    this.show = show;
    this.seat = seat;
  }

}
