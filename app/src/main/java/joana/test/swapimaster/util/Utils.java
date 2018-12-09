package joana.test.swapimaster.util;

import joana.test.swapimaster.data.model.Character;

import java.util.List;

public class Utils {
    public static boolean isNullOrEmpty(List<Character> data) {
        return data == null || data.size() == 0;
    }

    public static boolean isNullOrEmpty(String data) {
        return data == null || data.length() == 0;
    }

    public static String getFilmIdFromUrl(String filmUrl) {
        String[] parts = filmUrl.split("/");
        if(parts.length == 0)
            return "";

        return parts[parts.length-1];
    }

    public static int getNextPageFromURL(String nextUrl){
        String[] parts = nextUrl.split("&page=");

        if(parts.length == 0)
            return -1;

        return Integer.parseInt(parts[parts.length-1]);
    }
}

