package joana.test.swapimaster.data.room.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import joana.test.swapimaster.data.model.Character;
import joana.test.swapimaster.util.StringListConverter;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters({StringListConverter.class})
public interface CharacterDao {
    @Insert(onConflict = REPLACE)
    void insert(Character character);

    @Insert(onConflict = REPLACE)
    void insertAll(List<Character> people);

    @Query("SELECT * FROM people_table ORDER BY name ASC")
    LiveData<List<Character>> getPeople();

    @Query("SELECT * FROM people_table WHERE name = :id")
    LiveData<Character> getCharacter(String id);

    @Query("SELECT * FROM people_table WHERE name = :id")
    Character getBasicCharacter(String id);
}
