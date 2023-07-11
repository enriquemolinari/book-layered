package layered.data.entities;

import static layered.data.entities.SchemaName.SCHEMA_NAME;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
public class Theatre {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "id_theatre")
  private List<Seat> seats;

  public Theatre(String name) {
    this.name = name;
    this.seats = new ArrayList<>();
  }

  public Theatre(List<Seat> seats, String name) {
    this.name = name;
    this.seats = List.copyOf(seats);
  }

  public void addSeat(Seat seat) {
    this.seats.add(seat);
  }

  public List<Seat> seats() {
    return Collections.unmodifiableList(seats);
  }
}
