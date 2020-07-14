import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum Sex {
    MAN,
    WOMAN;

    private static final List<Sex> VALUES = Arrays.asList(Sex.values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Sex randomSex()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
