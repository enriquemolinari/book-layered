package layered.data.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ShortShowData(Long idShow, LocalDateTime startTime,
    String theatreName, float price) {

  private static final String DATE_FORMAT_FROM_DBQUERY =
      "yyyy-MM-dd HH:mm:ss.SSSSSS";

  public static ShortShowData fromString(String comaSeparatedData) {
    String[] data = comaSeparatedData.split(",");
    if (data.length != 4) {
      throw new DataException(
          "There are missing values to build a ShowData info");
    }

    var idShow = Long.valueOf(data[0]);
    var startTime = LocalDateTime.parse(data[1],
        DateTimeFormatter.ofPattern(DATE_FORMAT_FROM_DBQUERY));
    var price = Float.valueOf(data[2]);
    var theatreName = data[3];

    return new ShortShowData(idShow, startTime, theatreName, price);
  }
}
