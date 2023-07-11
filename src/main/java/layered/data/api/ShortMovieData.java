package layered.data.api;

import java.time.LocalDate;
import java.util.List;

public record ShortMovieData(Long idMovie, String name, String plot,
    int duration, List<String> genres, LocalDate releaseDate,
    int ageRestriction) {

}
