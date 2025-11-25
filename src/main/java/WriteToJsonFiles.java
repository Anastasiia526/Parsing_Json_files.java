import com.fasterxml.jackson.core.*;

import java.io.*;
import java.util.*;


public class WriteToJsonFiles {

    private final Map<String, List<Movie>> moviesByDirector = new HashMap<>();

    public Map<String, List<Movie>> getMoviesByDirector() {
        return moviesByDirector;
    }

    public static void writeJsonToFile(Map<String, List<Movie>> movieMap, String fileName) {

        JsonFactory factory = new JsonFactory();
        File file = new File(fileName);

        try (JsonGenerator generator = factory.createGenerator(file, JsonEncoding.UTF8)) {

            generator.writeStartObject();

            for (Map.Entry<String, List<Movie>> entry : movieMap.entrySet()) {

                String key = entry.getKey();
                List<Movie> movies = entry.getValue();

                generator.writeArrayFieldStart(key);

                for (Movie movie : movies) {

                    generator.writeStartObject();

                    generator.writeStringField("title", movie.getTitle());
                    generator.writeNumberField("yearOfRelease", movie.getYearOfRelease());
                    generator.writeArrayFieldStart("genre");
                    for (String g : movie.getGenre()) {
                        generator.writeString(g);
                    }
                    generator.writeEndArray();

                    generator.writeEndObject();
                }

                generator.writeEndArray();
            }
            generator.writeEndObject();

        } catch (IOException e) {
            System.out.println("Error while creating JsonGenerator");
            e.printStackTrace();
        }
    }

    public void saveToJsonFile(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        for(Map.Entry<String, List<Movie>> entry : moviesByDirector.entrySet()) {
            String director = entry.getKey();
            List<Movie> movies = entry.getValue();

            String fileName = director.replace(" ", "_") + ".json";
            File jsonFile = new File(file, fileName);

            Map<String, List<Movie>> moviesByDirector = new HashMap<>();
            moviesByDirector.put(director, movies);
            writeJsonToFile(moviesByDirector, jsonFile.getAbsolutePath());
        }
    }

    public void addMovie(String director, Movie movie) {

        moviesByDirector.computeIfAbsent(director, d -> new ArrayList<>()).add(movie);
    }

    public void addMovieList(List<Movie> movieList) {
        for (Movie movie : movieList) {
            addMovie(movie.getDirector(), movie);
        }
    }

    public void addMoviesToMap() {

        List<Movie> moviesBySpielberg = new ArrayList<>();

        moviesBySpielberg.add(new Movie("Steven Spielberg", "Firelight", 1964, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Amblin'", 1968, "Short, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Duel", 1971, "Thriller"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Something Evil", 1972, "Horror"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Sugarland Express", 1974, "Crime, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Jaws", 1975, "Thriller"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Close Encounters of the Third Kind", 1977, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "1941", 1979, "Comedy"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Raiders of the Lost Ark", 1981, "Adventure"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "E.T. the Extra-Terrestrial", 1982, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Twilight Zone: The Movie (Segment)", 1983, "Fantasy, Horror"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Indiana Jones and the Temple of Doom", 1984, "Adventure"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Color Purple", 1985, "Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Empire of the Sun", 1987, "Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Indiana Jones and the Last Crusade", 1989, "Adventure"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Always", 1989, "Romance, Fantasy"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Hook", 1991, "Fantasy"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Jurassic Park", 1993, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Schindler's List", 1993, "Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Lost World: Jurassic Park", 1997, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Amistad", 1997, "Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Saving Private Ryan", 1998, "War, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "A.I. Artificial Intelligence", 2001, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Minority Report", 2002, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Catch Me If You Can", 2002, "Crime, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Terminal", 2004, "Comedy, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "War of the Worlds", 2005, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Munich", 2005, "Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Indiana Jones and the Kingdom of the Crystal Skull", 2008, "Adventure"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Adventures of Tintin", 2011, "Animation, Adventure"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "War Horse", 2011, "War, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Lincoln", 2012, "Biography, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Bridge of Spies", 2015, "Drama, Thriller"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The BFG", 2016, "Fantasy"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Post", 2017, "Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Ready Player One", 2018, "Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "West Side Story", 2021, "Musical, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Fabelmans", 2022, "Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Night Gallery (Eyes)", 1969, "TV, Horror"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Savage", 1973, "TV, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Columbo: Murder by the Book", 1971, "TV, Crime"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Columbo: Ransom for a Dead Man", 1971, "TV, Crime"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Blues (Executive Segment)", 2003, "Documentary")); // as director segment
        moviesBySpielberg.add(new Movie("Steven Spielberg", "ACLU Tribute (Short)", 1995, "Short, Documentary"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "A Timeless Call (Short)", 2008, "Short, Documentary"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Space Station 3D (Narrative segment)", 2002, "Documentary"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Taken (Pilot)", 2002, "TV, Sci-Fi"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Amazing Stories: The Mission", 1985, "TV, War, Drama"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Amazing Stories: Ghost Train", 1986, "TV, Fantasy"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Saving Private Ryan: Into the Breach (Short)", 1998, "Documentary"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "Auschwitz (Short)", 1994, "Documentary"));
        moviesBySpielberg.add(new Movie("Steven Spielberg", "The Lost World: Making Of (Short)", 1997, "Documentary"));

        addMovieList(moviesBySpielberg);

        List<Movie> moviesByAllen = new ArrayList<>(List.of(new Movie("Woody Allen", "What's Up, Tiger Lily?", 1966, "Comedy"),
                new Movie("Woody Allen", "Take the Money and Run", 1969, "Comedy"),
                new Movie("Woody Allen", "Bananas", 1971, "Comedy"),
                new Movie("Woody Allen", "Everything You Always Wanted to Know About Sex", 1972, "Comedy"),
                new Movie("Woody Allen", "Sleeper", 1973, "Comedy"),
                new Movie("Woody Allen", "Love and Death", 1975, "Comedy"),
                new Movie("Woody Allen", "Annie Hall", 1977, "Romantic Comedy"),
                new Movie("Woody Allen", "Interiors", 1978, "Drama"),
                new Movie("Woody Allen", "Manhattan", 1979, "Romantic Comedy"),
                new Movie("Woody Allen", "Stardust Memories", 1980, "Drama"),
                new Movie("Woody Allen", "A Midsummer Night's Sex Comedy", 1982, "Comedy"),
                new Movie("Woody Allen", "Zelig", 1983, "Comedy"),
                new Movie("Woody Allen", "Broadway Danny Rose", 1984, "Comedy"),
                new Movie("Woody Allen", "The Purple Rose of Cairo", 1985, "Comedy, Fantasy"),
                new Movie("Woody Allen", "Hannah and Her Sisters", 1986, "Drama"),
                new Movie("Woody Allen", "Radio Days", 1987, "Comedy"),
                new Movie("Woody Allen", "September", 1987, "Drama"),
                new Movie("Woody Allen", "Another Woman", 1988, "Drama"),
                new Movie("Woody Allen", "Crimes and Misdemeanors", 1989, "Drama"),
                new Movie("Woody Allen", "Alice", 1990, "Drama"),
                new Movie("Woody Allen", "Shadows and Fog", 1991, "Comedy"),
                new Movie("Woody Allen", "Husbands and Wives", 1992, "Drama"),
                new Movie("Woody Allen", "Manhattan Murder Mystery", 1993, "Comedy"),
                new Movie("Woody Allen", "Bullets Over Broadway", 1994, "Comedy"),
                new Movie("Woody Allen", "Mighty Aphrodite", 1995, "Comedy"),
                new Movie("Woody Allen", "Everyone Says I Love You", 1996, "Musical, Comedy"),
                new Movie("Woody Allen", "Deconstructing Harry", 1997, "Comedy"),
                new Movie("Woody Allen", "Celebrity", 1998, "Comedy, Drama"),
                new Movie("Woody Allen", "Sweet and Lowdown", 1999, "Drama"),
                new Movie("Woody Allen", "Small Time Crooks", 2000, "Comedy"),
                new Movie("Woody Allen", "The Curse of the Jade Scorpion", 2001, "Comedy"),
                new Movie("Woody Allen", "Hollywood Ending", 2002, "Comedy"),
                new Movie("Woody Allen", "Anything Else", 2003, "Comedy"),
                new Movie("Woody Allen", "Melinda and Melinda", 2004, "Comedy, Drama"),
                new Movie("Woody Allen", "Match Point", 2005, "Drama, Thriller"),
                new Movie("Woody Allen", "Scoop", 2006, "Comedy"),
                new Movie("Woody Allen", "Cassandra's Dream", 2007, "Drama"),
                new Movie("Woody Allen", "Vicky Cristina Barcelona", 2008, "Romantic Comedy"),
                new Movie("Woody Allen", "Whatever Works", 2009, "Comedy"),
                new Movie("Woody Allen", "You Will Meet a Tall Dark Stranger", 2010, "Comedy"),
                new Movie("Woody Allen", "Midnight in Paris", 2011, "Romantic Comedy"),
                new Movie("Woody Allen", "To Rome with Love", 2012, "Romantic Comedy"),
                new Movie("Woody Allen", "Blue Jasmine", 2013, "Drama"),
                new Movie("Woody Allen", "Magic in the Moonlight", 2014, "Romantic Comedy"),
                new Movie("Woody Allen", "Irrational Man", 2015, "Drama"),
                new Movie("Woody Allen", "Café Society", 2016, "Romantic Comedy"),
                new Movie("Woody Allen", "Wonder Wheel", 2017, "Drama"),
                new Movie("Woody Allen", "A Rainy Day in New York", 2019, "Romantic Comedy"),
                new Movie("Woody Allen", "Rifkin's Festival", 2020, "Comedy"),
                new Movie("Woody Allen", "Coup de Chance", 2023, "Drama, Romance")));
        addMovieList(moviesByAllen);

        List<Movie> moviesByScorsese = new ArrayList<>(List.of(new Movie("Martin Scorsese", "Who's That Knocking at My Door", 1967, "Drama"),
                new Movie("Martin Scorsese", "Boxcar Bertha", 1972, "Crime, Drama"),
                new Movie("Martin Scorsese", "Mean Streets", 1973, "Crime"),
                new Movie("Martin Scorsese", "Italianamerican", 1974, "Documentary"),
                new Movie("Martin Scorsese", "Alice Doesn't Live Here Anymore", 1974, "Drama"),
                new Movie("Martin Scorsese", "The Concert for Bangladesh (Segment)", 1972, "Documentary"),
                new Movie("Martin Scorsese", "Taxi Driver", 1976, "Drama"),
                new Movie("Martin Scorsese", "New York, New York", 1977, "Musical"),
                new Movie("Martin Scorsese", "American Boy: A Profile of Steven Prince", 1978, "Documentary"),
                new Movie("Martin Scorsese", "The Last Waltz", 1978, "Documentary"),
                new Movie("Martin Scorsese", "Raging Bull", 1980, "Biography, Drama"),
                new Movie("Martin Scorsese", "The King of Comedy", 1982, "Comedy, Drama"),
                new Movie("Martin Scorsese", "After Hours", 1985, "Comedy"),
                new Movie("Martin Scorsese", "The Color of Money", 1986, "Drama, Sport"),
                new Movie("Martin Scorsese", "The Last Temptation of Christ", 1988, "Drama"),
                new Movie("Martin Scorsese", "New York Stories (Segment)", 1989, "Comedy, Drama"),
                new Movie("Martin Scorsese", "Goodfellas", 1990, "Crime"),
                new Movie("Martin Scorsese", "Cape Fear", 1991, "Thriller"),
                new Movie("Martin Scorsese", "The Age of Innocence", 1993, "Drama"),
                new Movie("Martin Scorsese", "Casino", 1995, "Crime"),
                new Movie("Martin Scorsese", "Kundun", 1997, "Biography, Drama"),
                new Movie("Martin Scorsese", "My Voyage to Italy", 1999, "Documentary"),
                new Movie("Martin Scorsese", "Bringing Out the Dead", 1999, "Drama"),
                new Movie("Martin Scorsese", "Gangs of New York", 2002, "Crime, Drama"),
                new Movie("Martin Scorsese", "The Aviator", 2004, "Biography"),
                new Movie("Martin Scorsese", "No Direction Home: Bob Dylan", 2005, "Documentary"),
                new Movie("Martin Scorsese", "The Departed", 2006, "Crime, Thriller"),
                new Movie("Martin Scorsese", "Shine a Light", 2008, "Documentary"),
                new Movie("Martin Scorsese", "Shutter Island", 2010, "Thriller"),
                new Movie("Martin Scorsese", "George Harrison: Living in the Material World", 2011, "Documentary"),
                new Movie("Martin Scorsese", "Hugo", 2011, "Adventure"),
                new Movie("Martin Scorsese", "The Wolf of Wall Street", 2013, "Biography, Comedy"),
                new Movie("Martin Scorsese", "The 50 Year Argument", 2014, "Documentary"),
                new Movie("Martin Scorsese", "Silence", 2016, "Drama"),
                new Movie("Martin Scorsese", "Rolling Thunder Revue: A Bob Dylan Story", 2019, "Documentary"),
                new Movie("Martin Scorsese", "The Irishman", 2019, "Crime, Drama"),
                new Movie("Martin Scorsese", "Pretend It's a City", 2021, "Documentary"),
                new Movie("Martin Scorsese", "Personality Crisis: One Night Only", 2022, "Documentary"),
                new Movie("Martin Scorsese", "Killers of the Flower Moon", 2023, "Crime, Drama"),
                new Movie("Martin Scorsese", "Made in Milan", 1990, "Documentary"),
                new Movie("Martin Scorsese", "A Letter to Elia", 2010, "Documentary"),
                new Movie("Martin Scorsese", "The Neighborhood", 1970, "Short, Documentary"),
                new Movie("Martin Scorsese", "It's Not Just You, Murray!", 1964, "Short, Comedy"),
                new Movie("Martin Scorsese", "What’s a Nice Girl Like You Doing in a Place Like This?", 1963, "Short, Comedy"),
                new Movie("Martin Scorsese", "The Big Shave", 1967, "Short"),
                new Movie("Martin Scorsese", "Street Scenes", 1970, "Documentary"),
                new Movie("Martin Scorsese", "Michael Jackson's Bad", 1987, "Short, Music"),
                new Movie("Martin Scorsese", "The Audition", 2015, "Short"),
                new Movie("Martin Scorsese", "The Key to Reserva", 2007, "Short"),
                new Movie("Martin Scorsese", "Bluebird", 2003, "Short")));
        addMovieList(moviesByScorsese);

        List<Movie> moviesByEastwood = new ArrayList<>(List.of(new Movie("Clint Eastwood", "Play Misty for Me", 1971, "Thriller"),
                new Movie("Clint Eastwood", "High Plains Drifter", 1973, "Western"),
                new Movie("Clint Eastwood", "Breezy", 1973, "Romance"),
                new Movie("Clint Eastwood", "The Eiger Sanction", 1975, "Thriller"),
                new Movie("Clint Eastwood", "The Outlaw Josey Wales", 1976, "Western"),
                new Movie("Clint Eastwood", "The Gauntlet", 1977, "Action"),
                new Movie("Clint Eastwood", "Every Which Way but Loose", 1978, "Comedy"),
                new Movie("Clint Eastwood", "Bronco Billy", 1980, "Comedy, Drama"),
                new Movie("Clint Eastwood", "Firefox", 1982, "Action"),
                new Movie("Clint Eastwood", "Honkytonk Man", 1982, "Drama"),
                new Movie("Clint Eastwood", "Sudden Impact", 1983, "Crime"),
                new Movie("Clint Eastwood", "City Heat", 1984, "Action, Comedy"),
                new Movie("Clint Eastwood", "Pale Rider", 1985, "Western"),
                new Movie("Clint Eastwood", "Heartbreak Ridge", 1986, "War, Drama"),
                new Movie("Clint Eastwood", "Bird", 1988, "Biography, Drama"),
                new Movie("Clint Eastwood", "White Hunter Black Heart", 1990, "Adventure, Drama"),
                new Movie("Clint Eastwood", "The Rookie", 1990, "Action"),
                new Movie("Clint Eastwood", "Unforgiven", 1992, "Western, Drama"),
                new Movie("Clint Eastwood", "A Perfect World", 1993, "Crime, Drama"),
                new Movie("Clint Eastwood", "The Bridges of Madison County", 1995, "Romance, Drama"),
                new Movie("Clint Eastwood", "Absolute Power", 1997, "Thriller"),
                new Movie("Clint Eastwood", "Midnight in the Garden of Good and Evil", 1997, "Drama"),
                new Movie("Clint Eastwood", "True Crime", 1999, "Thriller"),
                new Movie("Clint Eastwood", "Space Cowboys", 2000, "Sci-Fi, Adventure"),
                new Movie("Clint Eastwood", "Blood Work", 2002, "Thriller"),
                new Movie("Clint Eastwood", "Mystic River", 2003, "Drama"),
                new Movie("Clint Eastwood", "Million Dollar Baby", 2004, "Drama"),
                new Movie("Clint Eastwood", "Flags of Our Fathers", 2006, "War, Drama"),
                new Movie("Clint Eastwood", "Letters from Iwo Jima", 2006, "War, Drama"),
                new Movie("Clint Eastwood", "Changeling", 2008, "Drama"),
                new Movie("Clint Eastwood", "Gran Torino", 2008, "Drama"),
                new Movie("Clint Eastwood", "Invictus", 2009, "Biography, Sport"),
                new Movie("Clint Eastwood", "Hereafter", 2010, "Drama, Fantasy"),
                new Movie("Clint Eastwood", "J. Edgar", 2011, "Biography, Drama"),
                new Movie("Clint Eastwood", "Jersey Boys", 2014, "Biography, Music"),
                new Movie("Clint Eastwood", "American Sniper", 2014, "War, Biography"),
                new Movie("Clint Eastwood", "Sully", 2016, "Biography, Drama"),
                new Movie("Clint Eastwood", "The 15:17 to Paris", 2018, "Drama, Thriller"),
                new Movie("Clint Eastwood", "The Mule", 2018, "Crime, Drama"),
                new Movie("Clint Eastwood", "Richard Jewell", 2019, "Biography, Drama"),
                new Movie("Clint Eastwood", "Cry Macho", 2021, "Drama, Western"),
                new Movie("Clint Eastwood", "The Beguiled (Short)", 1971, "Short"),
                new Movie("Clint Eastwood", "Misty Revisited (Short)", 1972, "Short, Documentary"),
                new Movie("Clint Eastwood", "The Eastwood Factor", 2010, "Documentary"),
                new Movie("Clint Eastwood", "Piano Blues", 2003, "Documentary"),
                new Movie("Clint Eastwood", "Dave Brubeck: In His Own Sweet Way", 2010, "Documentary"),
                new Movie("Clint Eastwood", "The Good, The Bad and The Beautiful (Short)", 2014, "Short"),
                new Movie("Clint Eastwood", "Eastwood Directs: The Untold Story", 2013, "Documentary"),
                new Movie("Clint Eastwood", "The Evolution of a Director (Short)", 1992, "Documentary"),
                new Movie("Clint Eastwood", "Letters from Iwo Jima: Red Sun, Black Sand (Short)", 2007, "Documentary")));

        addMovieList(moviesByEastwood);

        List<Movie> moviesByScott = new ArrayList<>(List.of(new Movie("Ridley Scott", "Boy and Bicycle", 1965, "Short"),
                new Movie("Ridley Scott", "The Duellists", 1977, "Drama"),
                new Movie("Ridley Scott", "Alien", 1979, "Sci-Fi, Horror"),
                new Movie("Ridley Scott", "Blade Runner", 1982, "Sci-Fi"),
                new Movie("Ridley Scott", "Legend", 1985, "Fantasy"),
                new Movie("Ridley Scott", "Someone to Watch Over Me", 1987, "Thriller"),
                new Movie("Ridley Scott", "Black Rain", 1989, "Crime, Thriller"),
                new Movie("Ridley Scott", "Thelma & Louise", 1991, "Drama"),
                new Movie("Ridley Scott", "1492: Conquest of Paradise", 1992, "Historical, Drama"),
                new Movie("Ridley Scott", "White Squall", 1996, "Adventure, Drama"),
                new Movie("Ridley Scott", "G.I. Jane", 1997, "Drama"),
                new Movie("Ridley Scott", "Gladiator", 2000, "Historical, Drama"),
                new Movie("Ridley Scott", "Hannibal", 2001, "Thriller"),
                new Movie("Ridley Scott", "Black Hawk Down", 2001, "War, Drama"),
                new Movie("Ridley Scott", "Matchstick Men", 2003, "Comedy, Crime"),
                new Movie("Ridley Scott", "Kingdom of Heaven", 2005, "Historical, Drama"),
                new Movie("Ridley Scott", "A Good Year", 2006, "Romance, Comedy"),
                new Movie("Ridley Scott", "American Gangster", 2007, "Crime, Drama"),
                new Movie("Ridley Scott", "Body of Lies", 2008, "Thriller"),
                new Movie("Ridley Scott", "Robin Hood", 2010, "Adventure, Drama"),
                new Movie("Ridley Scott", "Prometheus", 2012, "Sci-Fi"),
                new Movie("Ridley Scott", "The Counselor", 2013, "Crime, Thriller"),
                new Movie("Ridley Scott", "Exodus: Gods and Kings", 2014, "Historical, Drama"),
                new Movie("Ridley Scott", "The Martian", 2015, "Sci-Fi"),
                new Movie("Ridley Scott", "Alien: Covenant", 2017, "Sci-Fi, Horror"),
                new Movie("Ridley Scott", "All the Money in the World", 2017, "Crime, Drama"),
                new Movie("Ridley Scott", "The Last Duel", 2021, "Historical, Drama"),
                new Movie("Ridley Scott", "House of Gucci", 2021, "Crime, Drama"),
                new Movie("Ridley Scott", "Napoleon", 2023, "Biography, War"),
                new Movie("Ridley Scott", "Britannia Hospital (Segment)", 1982, "Satire"),
                new Movie("Ridley Scott", "The Author of Himself (Short)", 2006, "Short"),
                new Movie("Ridley Scott", "Hennessy X.O: The Seven Worlds (Short)", 2019, "Short, Fantasy"),
                new Movie("Ridley Scott", "Blade Runner: Black Out 2022 (Producer/Director segment)", 2017, "Animation"),
                new Movie("Ridley Scott", "BMW The Hire: Beat the Devil", 2002, "Short"),
                new Movie("Ridley Scott", "Guinness Surf (Commercial)", 1999, "Short"),
                new Movie("Ridley Scott", "Apple 1984 (Commercial)", 1984, "Short"),
                new Movie("Ridley Scott", "Channel 4 Idents (Short)", 1982, "Short"),
                new Movie("Ridley Scott", "Blade Runner (Final Cut Restoration Featurette)", 2007, "Documentary"),
                new Movie("Ridley Scott", "Alien: The Director's Cut (Featurette)", 2003, "Documentary"),
                new Movie("Ridley Scott", "Prometheus: Making Of", 2012, "Documentary"),
                new Movie("Ridley Scott", "Gladiator: The Making Of", 2000, "Documentary"),
                new Movie("Ridley Scott", "Legend: Making Of", 1985, "Documentary"),
                new Movie("Ridley Scott", "Thelma & Louise: The Last Journey", 1991, "Documentary"),
                new Movie("Ridley Scott", "Black Hawk Down: The Real Story", 2001, "Documentary"),
                new Movie("Ridley Scott", "Kingdom of Heaven: Pilgrimage", 2005, "Documentary"),
                new Movie("Ridley Scott", "American Gangster: The Return", 2007, "Documentary"),
                new Movie("Ridley Scott", "All the Money in the World: Behind the Scenes", 2017, "Documentary"),
                new Movie("Ridley Scott", "House of Gucci: A Legacy", 2021, "Documentary"),
                new Movie("Ridley Scott", "Napoleon: Behind the Crown", 2023, "Documentary")));

        addMovieList(moviesByScott);
    }
}
