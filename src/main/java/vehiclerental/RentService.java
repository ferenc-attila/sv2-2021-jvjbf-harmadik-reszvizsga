package vehiclerental;

import java.time.LocalTime;
import java.util.*;

public class RentService {

    private Set<Rentable> rentables = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private Map<Rentable, User> actualRenting = new TreeMap<>();

    public void registerUser(User user) {
        if (users.stream()
                .map(User::getUserName)
                .anyMatch(u -> u.equals(user.getUserName()))) {
            throw new UserNameIsAlreadyTakenException("Username is taken!");
        }
        users.add(user);
    }

    public void addRentable(Rentable rentable) {
        rentables.add(rentable);
    }

    public void rent(User user, Rentable rentable, LocalTime time) {
        if (rentable.getRentingTime() != null && actualRenting.containsKey(rentable)) {
            throw new IllegalStateException("Vehicle is rented");
        }
        if (user.getBalance() < rentable.calculateSumPrice(180)) {
            throw new IllegalStateException("Not enough balance");
        }
        rentable.rent(time);
        actualRenting.put(rentable, user);
    }

    public void closeRent(Rentable rentable, int minutes) {
        if (!actualRenting.containsKey(rentable)) {
            throw new IllegalStateException("Vehicle not rented!");
        }
        actualRenting.get(rentable).minusBalance(rentable.calculateSumPrice(minutes));
        actualRenting.remove(rentable);
        rentable.closeRent();
    }

    public Set<Rentable> getRentables() {
        return Collections.unmodifiableSet(rentables);
    }

    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    public Map<Rentable, User> getActualRenting() {
        return Map.copyOf(actualRenting);
    }
}
