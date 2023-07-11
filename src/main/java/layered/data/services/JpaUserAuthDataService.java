package layered.data.services;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import jakarta.persistence.EntityManagerFactory;
import layered.data.api.FullUserData;
import layered.data.api.UserAuthDataService;
import layered.data.api.UserData;
import layered.data.entities.LoginAudit;
import layered.data.entities.User;

public class JpaUserAuthDataService implements UserAuthDataService {

  private EntityManagerFactory emf;

  public JpaUserAuthDataService(EntityManagerFactory emf) {
    this.emf = emf;
  }

  @Override
  public Optional<UserData> login(String username, String password) {
    Objects.requireNonNull(username, "username must not be null");
    Objects.requireNonNull(password, "password must not be null");
    var em = emf.createEntityManager();
    var tx = em.getTransaction();

    try {
      tx.begin();
      var q = em.createQuery(
          "from User u where u.username = :username and u.password = :password",
          User.class);

      q.setParameter("username", username);
      q.setParameter("password", password);

      var user = q.getSingleResult();

      em.persist(new LoginAudit(LocalDateTime.now(), user));
      tx.commit();

      return Optional.of(user.toUserData());
    } catch (Exception e) {
      tx.rollback();
      return Optional.empty();
    } finally {
      em.close();
    }
  }

  @Override
  public FullUserData details(Long idUser) {
    try (var em = emf.createEntityManager()) {
      var user = em.find(User.class, idUser);
      return user.toFullUserData();
    }
  }
}
