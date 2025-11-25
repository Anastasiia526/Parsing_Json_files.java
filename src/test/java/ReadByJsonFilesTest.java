import org.junit.*;
import org.mockito.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

    public class ReadByJsonFilesTest {

        private ReadByJsonFiles reader;

        @Before
        public void setUp() {
            reader = new ReadByJsonFiles();
        }

        @Test
        public void testGenerateStatisticWithNonExistentFolder() {
            reader.generateStatistic("nonexistent_folder", "title", "output");
        }

        @Test
        public void testGenerateStatisticWithEmptyFolder() throws IOException {
            File tempDir = Files.createTempDirectory("empty").toFile();
            reader.generateStatistic(tempDir.getAbsolutePath(), "title", tempDir.getAbsolutePath());
            tempDir.deleteOnExit();
        }

        @Test
        public void testGenerateStatisticWithMockedMovies() throws IOException {
            File tempDir = Files.createTempDirectory("movies").toFile();
            File tempFile = new File(tempDir, "movies.json");

            Movie movie1 = new Movie("Director1", "Movie1", 2000, "Comedy");
            Movie movie2 = new Movie("Director1", "Movie2", 2001, "Drama");

            Map<String, List<Movie>> map = new HashMap<>();
            map.put("Director1", Arrays.asList(movie1, movie2));

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(tempFile, map);

            reader.generateStatistic(tempDir.getAbsolutePath(), "genre", tempDir.getAbsolutePath());

            File outputFile = new File(tempDir, "statistics_by_genre.xml");
            Assert.assertTrue("Output XML file should exist", outputFile.exists());

            tempFile.deleteOnExit();
            outputFile.deleteOnExit();
            tempDir.deleteOnExit();
        }

        @Test
        public void testGetAttributeValueGenre() throws Exception {
            Movie movie = new Movie("Director", "Title", 2020, "Comedy, Drama");
            java.lang.reflect.Method method = ReadByJsonFiles.class.getDeclaredMethod("getAttributeValue", Movie.class, String.class);
            method.setAccessible(true);
            String value = (String) method.invoke(reader, movie, "genre");
            Assert.assertEquals("Comedy, Drama", value);
        }

        @Test
        public void testWriteStatisticsToXmlCreatesFile() throws Exception {
            java.lang.reflect.Method method = ReadByJsonFiles.class.getDeclaredMethod("writeStatisticsToXml", List.class, String.class);
            method.setAccessible(true);

            List<Map.Entry<String, Integer>> data = new ArrayList<>();
            data.add(new AbstractMap.SimpleEntry<>("Comedy", 3));
            data.add(new AbstractMap.SimpleEntry<>("Drama", 2));

            File tempFile = File.createTempFile("stats", ".xml");
            method.invoke(reader, data, tempFile.getAbsolutePath());

            Assert.assertTrue(tempFile.exists());
            tempFile.deleteOnExit();
        }
}
