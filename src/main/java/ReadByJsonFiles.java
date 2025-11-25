import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class ReadByJsonFiles {

    private final ObjectMapper mapper = new ObjectMapper();

    public void generateStatistic(String folder, String attribute, String outputFolderPath) {

        File folderPath = new File(folder);
        if (!folderPath.exists() || !folderPath.isDirectory()) {
            System.out.println("The folder does not exist or the path is incorrect. " + folder);
            return;
        }

        Map<String, Integer> count = new HashMap<>();
        File[] files = folderPath.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        if (files == null || files.length == 0) {
            System.out.println("JSON files from dad: " + folderPath);
            return;
        }

        for (File file : files) {
            try {
                Map<String, List<Movie>> moviesByDirector = mapper.readValue(file, new TypeReference<Map<String, List<Movie>>>() {});
            for(List<Movie> movies : moviesByDirector.values()){
                for (Movie movie : movies) {
                    String key = getAttributeValue(movie, attribute);
                    if (key != null) {

                        if (attribute.equalsIgnoreCase("genre")) {
                            String[] genres = key.split(",");
                            for (String g : genres) {
                                count.put(g.trim(), count.getOrDefault(g.trim(), 0) + 1);
                            }

                        } else {
                            count.put(key, count.getOrDefault(key, 0) + 1);
                        }
                    }
                }
            }

            } catch (IOException e) {
                System.out.println("Notice when reading a file: " + file.getName());
                e.printStackTrace();
            }
        }

        List<Map.Entry<String, Integer>> sorted = count.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        String outputFileName = "statistics_by_" + attribute + ".xml";

        File outputFolder = new File(outputFolderPath);

        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        File outputFile = new File(outputFolder, outputFileName);

        writeStatisticsToXml(sorted, outputFile.getAbsolutePath());

        writeStatisticsToXml(sorted, outputFileName);
        System.out.println("Statistics have been created for the file: " + outputFileName);
    }

        private String getAttributeValue(Movie movie, String attribute) {
            switch (attribute.toLowerCase()) {
                case "title": return movie.getTitle();
                case "genre": return String.join(", ", movie.getGenre()); // якщо genre у вигляді списку
                case "year": return String.valueOf(movie.getYearOfRelease());
                default: return null;
            }
        }

        private void writeStatisticsToXml(List<Map.Entry<String, Integer>> sortedData, String fileName) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();

                Element root = doc.createElement("statistics");
                doc.appendChild(root);

                for (Map.Entry<String, Integer> entry : sortedData) {
                    Element item = doc.createElement("item");

                    Element name = doc.createElement("name");
                    name.setTextContent(entry.getKey());

                    Element count = doc.createElement("count");
                    count.setTextContent(entry.getValue().toString());

                    item.appendChild(name);
                    item.appendChild(count);
                    root.appendChild(item);
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(fileName));
                transformer.transform(source, result);

            } catch (ParserConfigurationException | javax.xml.transform.TransformerException e) {
                e.printStackTrace();
            }
        }
    }


