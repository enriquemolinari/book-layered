package layered.data.api;

public record SeatData(Long idSeat, int number, boolean reserved,
    boolean confirmed) {
}
