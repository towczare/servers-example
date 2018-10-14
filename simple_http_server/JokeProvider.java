import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class JokeProvider {
    private static List<String> JOKES = new ArrayList<>();
    private static Random RANDOM = new Random();
    static {
        JOKES.add("Pierwszy suchar");
        JOKES.add("Drugi suchar");
        JOKES.add("Trzeci suchar");
        JOKES.add("Czwarty suchar");
        JOKES.add("Piąty suchar");
        JOKES.add("Szósty suchar");
    }
    private static int SIZE = JOKES.size();

    public static String getRandomJoke() {
        return JOKES.get(RANDOM.nextInt(SIZE));
    }
}