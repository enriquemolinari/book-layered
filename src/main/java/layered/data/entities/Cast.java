package layered.data.entities;

import static layered.data.entities.SchemaName.SCHEMA_NAME;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import layered.data.api.MovieCastData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "CAST_PERSON")
@Table(schema = SCHEMA_NAME)
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Cast {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Person persona;
  private String characterName;
  // D = Director, A = Actor/Actress, W = Writer, C = Crew
  private char type;

  public Cast(Person person, String characterName, char type) {
    this.persona = person;
    this.characterName = characterName;
    this.type = type;
  }

  public MovieCastData toCastData() {
    return new MovieCastData(this.persona.fullName(), this.characterName,
        this.type);
  }
}
