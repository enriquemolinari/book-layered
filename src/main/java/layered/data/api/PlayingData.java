package layered.data.api;

import java.util.List;

public record PlayingData(Long movieId, String movieName, int duration,
    String coverImg, List<String> genres, List<ShortShowData> shows) {

}
