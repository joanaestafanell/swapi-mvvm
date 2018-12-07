package joana.test.swapimaster.util;

import joana.test.swapimaster.data.model.Person;

import java.util.List;

public class Utils {
    public static boolean isNullOrEmpty(List<Person> data) {
        return data == null || data.size() == 0;
    }

    public static boolean isNullOrEmpty(String data) {
        return data == null || data.length() == 0;
    }
}

