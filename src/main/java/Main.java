import java.io.*;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WriteToJsonFiles writer = new WriteToJsonFiles();
        writer.addMoviesToMap();
        writer.saveToJsonFile("src/main/java/jsonFiles");
        System.out.println("JSON file created!");

        ReadByJsonFiles stats = new ReadByJsonFiles();

        System.out.println("------------------------------");

        String folderPath = "src/main/java/jsonFiles";
        String outputFolderPath = "src/main/java/jsonFiles";
        stats.generateStatistic(folderPath, attributeFromJsonFiles(), outputFolderPath);

        System.out.println("-------------------------------");

        File folder = new File("src/main/java/jsonFiles");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        ExecutorThreadJsonParser.runParsingWithDifferentThreadCounts(files);

    }

    public static String attributeFromJsonFiles() {
        String attribute = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the attribute name for further extraction from Json files, title, genre, year: ");
            attribute = br.readLine();

        } catch (IOException e) {
            System.out.println("Error reading Json file");
            e.printStackTrace();
        }
        return attribute;
    }
}
