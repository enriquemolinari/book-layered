package layered.data.api;

public class DataException extends RuntimeException {

  public DataException(String msg) {
    super(msg);
  }

  public DataException(String msg, Exception e) {
    super(msg, e);
  }
}
