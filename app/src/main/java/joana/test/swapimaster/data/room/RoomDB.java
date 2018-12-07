package joana.test.swapimaster.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import joana.test.swapimaster.data.model.Person;
import joana.test.swapimaster.data.room.model.PersonDao;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static volatile RoomDB INSTANCE;

    public static RoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDB.class, "room_db")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract PersonDao personDao();

}
