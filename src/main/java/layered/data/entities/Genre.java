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
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String description;

  public Genre(String description) {
    this.description = description;
  }

  public String description() {
    return this.description;
  }
}
