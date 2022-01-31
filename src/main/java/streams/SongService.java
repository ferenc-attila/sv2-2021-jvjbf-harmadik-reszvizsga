package streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SongService {

    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        validateSong(song);
        songs.add(song);
    }

    private void validateSong(Song song) {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null!");
        }
    }

    private void validateListOfSongs() {
        if (songs.isEmpty()) {
            throw new IllegalArgumentException("List of songs is empty!");
        }
    }

    private void validateString(String string) {
        if (string.isBlank()) {
            throw new IllegalArgumentException("Empty string!");
        }
    }

    public Optional<Song> shortestSong() {
        validateListOfSongs();
        return songs.stream()
                .min(Comparator.comparingInt(Song::getLength));
    }

    public List<Song> findSongByTitle(String title) {
        validateListOfSongs();
        return songs.stream()
                .filter(song -> title.equals(song.getTitle()))
                .toList();
    }

    public boolean isPerformerInSong(Song song, String performer) {
        validateListOfSongs();
        validateSong(song);
        validateString(performer);
        return songs.stream()
                .filter(s -> s.equals(song))
                .flatMap(s -> s.getPerformers().stream())
                .anyMatch(p -> p.equals(performer));
    }

    public List<String> titlesBeforeDate(LocalDate date) {
        validateListOfSongs();
        return songs.stream()
                .filter(song -> song.getRelease().isBefore(date))
                .map(Song::getTitle)
                .toList();
    }
}
