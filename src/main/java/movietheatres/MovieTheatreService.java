package movietheatres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {

    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public void readFromFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                createEntriesInShows(line);
            }
            sortMovies();
        } catch (IOException ioe) {
            throw new IllegalStateException("Unable to read file!", ioe);
        }
    }

    private void createEntriesInShows(String line) {
        String movieTheater = parseRow(line)[0];
        Movie movie = parseMovie(line);
        if (!shows.containsKey(movieTheater)) {
            shows.put(movieTheater, new ArrayList<>());
        }
        shows.get(movieTheater).add(movie);
    }

    private void sortMovies() {
        for (List<Movie> movies : shows.values()) {
            movies.sort(Comparator.comparing(Movie::getStartTime));
        }
    }

    private Movie parseMovie(String line) {
        String[] movieString = parseRow(line)[1].split(";");
        return new Movie(movieString[0], LocalTime.parse(movieString[1]));
    }

    private String[] parseRow(String line) {
        return line.split("-");
    }

    public List<String> findMovie(String title) {
        return shows.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .anyMatch(movie -> movie.getTitle().equals(title)))
                .map(Map.Entry::getKey)
                .toList();
    }

    public LocalTime findLatestShow(String title) {
        return shows.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .filter(movie -> movie.getTitle().equals(title)))
                .max(Comparator.comparing(Movie::getStartTime)).orElseThrow(() -> new IllegalArgumentException("No such movie")).getStartTime();
    }

    public Map<String, List<Movie>> getShows() {
        return Map.copyOf(shows);
    }
}
