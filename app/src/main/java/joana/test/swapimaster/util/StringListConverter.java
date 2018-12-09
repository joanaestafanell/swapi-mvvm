package joana.test.swapimaster.util;

import android.arch.persistence.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StringListConverter {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToStringList(String data){
        if(data == null)
            return Collections.emptyList();

        Type listType = new TypeToken<List<String>>() {}.getType();

        List<String> l = gson.fromJson(data, listType);

        return l;
    }

    @TypeConverter
    public static String stringListToString (List<String> data){
        return gson.toJson(data);
    }
}
