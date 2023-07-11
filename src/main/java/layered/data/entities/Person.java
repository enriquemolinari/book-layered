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
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String surname;
  private String email;

  public Person(String name, String surname, String email) {
    this.name = name;
    this.surname = surname;
    this.email = email;
  }

  public String fullName() {
    return name + " " + surname;
  }

  public String name() {
    return name;
  }

  public String surname() {
    return surname;
  }

  public String email() {
    return email;
  }
}
