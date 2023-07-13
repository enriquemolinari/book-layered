package layered.data.entities;

import static layered.data.entities.SchemaName.SCHEMA_NAME;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  @JoinColumn(unique = true)
  private Movie movie;
  private float value;
  private int totalVotes;
  private float totalValue;

  public Rating(Movie movie, float value, int totalVotes, float totalValue) {
    this.movie = movie;
    this.value = value;
    this.totalVotes = totalVotes;
    this.totalValue = totalValue;
  }

  public void calculateNewRate(int userValue) {
    this.value = (this.totalValue + userValue) / (totalVotes + 1);
    this.totalValue += userValue;
    this.totalVotes++;
  }

}
