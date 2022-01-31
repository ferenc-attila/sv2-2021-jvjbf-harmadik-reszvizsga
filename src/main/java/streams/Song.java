package streams;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Song {

    private String title;
    private int length;
    private List<String> performers;
    private LocalDate release;

    public Song(String title, int length, List<String> performers, LocalDate release) {
        this.title = title;
        this.length = length;
        this.performers = performers;
        this.release = release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return length == song.length && title.equals(song.title) && performers.equals(song.performers) && release.equals(song.release);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, length, performers, release);
    }

    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    public List<String> getPerformers() {
        return Collections.unmodifiableList(performers);
    }

    public LocalDate getRelease() {
        return release;
    }
}
