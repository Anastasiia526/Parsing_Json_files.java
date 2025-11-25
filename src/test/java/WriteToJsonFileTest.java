import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class WriteToJsonFileTest {

    @Test
    public void addMovieTest() {
        WriteToJsonFiles writeToJsonFiles = new WriteToJsonFiles();
        Movie movie = new Movie("Clint Eastwood", "Play Misty for Me", 1971, "Thriller");
        writeToJsonFiles.addMovie("Clint Eastwood", movie);

        assertTrue(writeToJsonFiles.getMoviesByDirector().containsKey("Clint Eastwood"));
        assertEquals(1, writeToJsonFiles.getMoviesByDirector().get("Clint Eastwood").size());
        assertEquals(movie, writeToJsonFiles.getMoviesByDirector().get("Clint Eastwood").get(0));
    }

    @Test
    public void addMovieListTest() {
        WriteToJsonFiles writeToJsonFiles = new WriteToJsonFiles();
        List<Movie> movieList = new ArrayList<>(List.of(new Movie("Clint Eastwood", "Play Misty for Me", 1971, "Thriller"),
                new Movie("Clint Eastwood", "High Plains Drifter", 1973, "Western")));

        writeToJsonFiles.addMovieList(movieList);

        assertTrue(writeToJsonFiles.getMoviesByDirector().containsKey("Clint Eastwood"));
        assertEquals(2, writeToJsonFiles.getMoviesByDirector().get("Clint Eastwood").size());
    }

    @Test
    public void addMovieToMapTest() {
        WriteToJsonFiles writeToJsonFiles = new WriteToJsonFiles();
        writeToJsonFiles.addMoviesToMap();

        assertEquals(5, writeToJsonFiles.getMoviesByDirector().size());
    }


    @Test
    public void testWriteJsonToFile() throws IOException {

        Movie avatar = new Movie(
                "Clint Eastwood",
                "Play Misty for Me",
                1971,
                "Thriller"
        );

        Movie titanic = new Movie(
                "Clint Eastwood",
                "The Eiger Sanction",
                1975,
                "Thriller"
        );

        Map<String, List<Movie>> movieMap =
                Map.of("moviesByEastwood", List.of(avatar, titanic));

        String testFile = "test_movies.json";
        WriteToJsonFiles.writeJsonToFile(movieMap, testFile);

        File file = new File(testFile);
        assertTrue("JSON file must be created", file.exists());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(file);

        assertTrue(("There must be a key 'moviesByEastwood'"), root.has("moviesByEastwood"));
        JsonNode movies = root.get("moviesByEastwood");
        assertEquals("There should be 2 movies", 2, movies.size());

        JsonNode m1 = movies.get(0);
        assertEquals("Play Misty for Me", m1.get("title").asText());
        assertEquals(1971, m1.get("yearOfRelease").asInt());

        assertTrue(m1.has("genre"));
        assertEquals(List.of("Thriller"), mapper.convertValue(m1.get("genre"), List.class));

        JsonNode m2 = movies.get(1);
        assertEquals("The Eiger Sanction", m2.get("title").asText());
        assertEquals(1975, m2.get("yearOfRelease").asInt());
        assertEquals(List.of("Thriller"), mapper.convertValue(m2.get("genre"), List.class));
    }

    @Test
    public void saveToJsonFileCreatesDirectoryAndFilesTest() {
        WriteToJsonFiles writer = new WriteToJsonFiles();

        writer.addMovie("Clint Eastwood", new Movie("Clint Eastwood", "Play Misty for Me", 1971, "Thriller"));
        writer.addMovie("Clint Eastwood", new Movie("Clint Eastwood", "The Eiger Sanction", 1975, "Thriller"));

        File folder = new File("temp_test_folder");
        if (folder.exists()) folder.delete();
        folder.mkdirs();

        writer.saveToJsonFile(folder.getAbsolutePath());

        File result = new File(folder, "Clint_Eastwood.json");

        assertTrue(result.exists());

        result.delete();
        folder.delete();
    }

    private JsonFactory jsonFactoryMock;
    private JsonGenerator jsonGeneratorMock;

    @Before
    public void setUp() throws IOException {
        jsonGeneratorMock = mock(JsonGenerator.class);

        jsonFactoryMock = mock(JsonFactory.class);
        when(jsonFactoryMock.createGenerator(any(File.class), eq(JsonEncoding.UTF8)))
                .thenReturn(jsonGeneratorMock);
    }

    @Test
    public void writeJsonToFileWithMockedJsonGeneratorTest() throws IOException {

        Map<String, List<Movie>> map = Map.of(
                "Someone", List.of(new Movie("Someone", "Film", 2000, "Drama"))
        );

        for (Map.Entry<String, List<Movie>> entry : map.entrySet()) {
            String director = entry.getKey();
            List<Movie> movies = entry.getValue();

            jsonGeneratorMock.writeStartObject();
            jsonGeneratorMock.writeArrayFieldStart(director);
            for (Movie movie : movies) {
                jsonGeneratorMock.writeStartObject();
                jsonGeneratorMock.writeStringField("title", movie.getTitle());
                jsonGeneratorMock.writeNumberField("yearOfRelease", movie.getYearOfRelease());
                jsonGeneratorMock.writeArrayFieldStart("genre");
                for (String g : movie.getGenre()) {
                    jsonGeneratorMock.writeString(g);
                }
                jsonGeneratorMock.writeEndArray();
                jsonGeneratorMock.writeEndObject();
            }
            jsonGeneratorMock.writeEndArray();
            jsonGeneratorMock.writeEndObject();
        }

        verify(jsonGeneratorMock, atLeastOnce()).writeStartObject();
        verify(jsonGeneratorMock, atLeastOnce()).writeStringField(eq("title"), eq("Film"));
    }
}

