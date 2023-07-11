package layered.data.entities;

import static layered.data.entities.SchemaName.SCHEMA_NAME;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import layered.data.api.FullUserData;
import layered.data.api.UserData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = SCHEMA_NAME)
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Person person;
  private String username;
  private String password;
  private int points;

  public User(Person person, String username, String pass) {
    this.person = person;
    this.username = username;
    this.password = pass;
  }

  public UserData toUserData() {
    return new UserData(id, username, points);
  }

  public FullUserData toFullUserData() {
    return new FullUserData(this.toUserData(), this.person.name(),
        this.person.surname(), this.person.email());
  }
}
