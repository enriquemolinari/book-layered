package layered.data.entities;

import static layered.data.entities.SchemaName.SCHEMA_NAME;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private int seatNumber;

  public Seat(int seatNumber) {
    this.seatNumber = seatNumber;
  }

}
