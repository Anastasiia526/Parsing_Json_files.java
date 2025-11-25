import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class ExecutorThreadJsonParser {

    public static void runParsingWithDifferentThreadCounts(File[] files)
            throws InterruptedException, ExecutionException {

        int[] threadCounts = {2, 4, 8, 10};

        for (int threads : threadCounts) {
            long start = System.currentTimeMillis();

            Map<String, List<Movie>> movies = parseFilesWithThreads(files, threads);

            long end = System.currentTimeMillis();

            System.out.println("Threads: " + threads +
                    " | Time: " + (end - start) + " ms" +
                    " | Directors parsed: " + movies.size());
        }
    }

    public static Map<String, List<Movie>> parseFilesWithThreads(File[] files, int threadCount)
            throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Map<String, List<Movie>>>> futures = new ArrayList<>();

        for (File file : files) {
            futures.add(executor.submit(() -> parseSingleFile(file)));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);

        Map<String, List<Movie>> finalMap = new HashMap<>();

        for (Future<Map<String, List<Movie>>> future : futures) {
            Map<String, List<Movie>> mapFromFile = future.get();

            for (var entry : mapFromFile.entrySet()) {
                finalMap
                        .computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                        .addAll(entry.getValue());
            }
        }

        return finalMap;
    }

    public static Map<String, List<Movie>> parseSingleFile(File file) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(
                    file,
                    new TypeReference<Map<String, List<Movie>>>() {}
            );

        } catch (Exception e) {
            throw new RuntimeException("Error parsing file: " + file.getName(), e);
        }
    }

}

