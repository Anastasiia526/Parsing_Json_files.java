import lombok.*;
import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Movie {
    private String director;
    private String title;
    private int yearOfRelease;
    private List<String> genre;

    public Movie(){}

    public Movie(String director, String title, int yearOfRelease, String genre) {
        this.director = director;
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.genre = Arrays.asList(genre.split(",\\s*"));
    }
}
