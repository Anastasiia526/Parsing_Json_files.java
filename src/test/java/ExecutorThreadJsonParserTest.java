import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ExecutorThreadJsonParserTest {
    private File fileMock;
    private ObjectMapper mapperMock;

    @Before
    public void setUp() {
        fileMock = mock(File.class);
        mapperMock = mock(ObjectMapper.class);
    }

    @Test
    public void testParseSingleFileReturnsCorrectMap() throws Exception {
        Map<String, List<Movie>> expected = new HashMap<>();
        expected.put("Director", Collections.singletonList(new Movie("Director", "Title", 2000, "Drama")));

        ObjectMapper realMapper = new ObjectMapper();

        File tempFile = File.createTempFile("movies", ".json");
        tempFile.deleteOnExit();
        realMapper.writeValue(tempFile, expected);

        Map<String, List<Movie>> result = ExecutorThreadJsonParser.parseSingleFile(tempFile);

        assertEquals(1, result.size());
        assertTrue(result.containsKey("Director"));
        assertEquals("Title", result.get("Director").get(0).getTitle());
    }

    @Test
    public void testParseFilesWithThreadsMergesMapsCorrectly() throws Exception {
        Map<String, List<Movie>> map1 = new HashMap<>();
        map1.put("A", Collections.singletonList(new Movie("A", "Movie1", 2000, "Drama")));
        Map<String, List<Movie>> map2 = new HashMap<>();
        map2.put("B", Collections.singletonList(new Movie("B", "Movie2", 2001, "Comedy")));

        ObjectMapper realMapper = new ObjectMapper();
        File temp1 = File.createTempFile("file1", ".json");
        temp1.deleteOnExit();
        realMapper.writeValue(temp1, map1);

        File temp2 = File.createTempFile("file2", ".json");
        temp2.deleteOnExit();
        realMapper.writeValue(temp2, map2);

        File[] files = {temp1, temp2};

        Map<String, List<Movie>> result = ExecutorThreadJsonParser.parseFilesWithThreads(files, 2);

        assertEquals(2, result.size());
        assertTrue(result.containsKey("A"));
        assertTrue(result.containsKey("B"));
    }

    @Test
    public void testRunParsingWithDifferentThreadCountsDoesNotThrow() throws Exception {
        Map<String, List<Movie>> map = new HashMap<>();
        map.put("X", Collections.singletonList(new Movie("X", "MovieX", 1999, "Action")));

        ObjectMapper realMapper = new ObjectMapper();
        File tempFile = File.createTempFile("fileX", ".json");
        tempFile.deleteOnExit();
        realMapper.writeValue(tempFile, map);

        File[] files = {tempFile};

        ExecutorThreadJsonParser.runParsingWithDifferentThreadCounts(files);
    }
}
