package vehiclerental;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Bike implements Rentable {

    private static final long RENTING_PRICE_PER_MINUTE = 15;

    private String id;
    private LocalTime rentingTime;

    public Bike(String id) {
        this.id = id;
    }

    @Override
    public int calculateSumPrice(long minutes) {
        return (int) (minutes * RENTING_PRICE_PER_MINUTE);
    }

    @Override
    public void rent(LocalTime time) {
        this.rentingTime = time;
    }

    @Override
    public void closeRent() {
        this.rentingTime = null;
    }

    @Override
    public LocalTime getRentingTime() {
        return rentingTime;
    }

    public String getId() {
        return id;
    }
}
