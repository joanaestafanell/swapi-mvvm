package joana.test.swapimaster.data.room.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import joana.test.swapimaster.data.model.Person;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PersonDao {
    @Insert(onConflict = REPLACE)
    void insert(Person person);

    @Insert(onConflict = REPLACE)
    void insertAll(List<Person> people);

    @Query("SELECT * FROM people_table ORDER BY name ASC")
    LiveData<List<Person>> getPeople();

    @Query("SELECT * FROM people_table WHERE name = :id")
    LiveData<Person> getPerson(String id);
}
