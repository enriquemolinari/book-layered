package layered.data.api;

import java.util.Optional;

public interface UserAuthDataService {

  Optional<UserData> login(String username, String password);

  FullUserData details(Long idUser);
}
