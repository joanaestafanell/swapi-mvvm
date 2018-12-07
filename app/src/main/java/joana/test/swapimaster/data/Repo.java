package joana.test.swapimaster.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import joana.test.swapimaster.data.model.People;
import joana.test.swapimaster.data.model.Person;
import joana.test.swapimaster.data.network.RetrofitClient;
import joana.test.swapimaster.data.network.SwapiEndpoints;
import joana.test.swapimaster.data.room.RoomDB;
import joana.test.swapimaster.data.room.model.PersonDao;
import joana.test.swapimaster.util.Utils;
import joana.test.swapimaster.util.Resource;
import retrofit2.Call;

import java.util.List;

public class Repo {

    private PersonDao mPersonDao;
    private static int page = 0;

    public Repo(Application application) {
        RoomDB db = RoomDB.getDatabase(application);

        mPersonDao = db.personDao();
    }

    /**
     * Only fetch network data if local db is empty, otherwise display persisted data
     *
     * @return Observable People list
     */
    public LiveData<Resource<List<Person>>> getPeople() {

        return new NetworkBoundResource<List<Person>, People>() {
            @Override
            protected void saveCallResult(@NonNull People item) {
                mPersonDao.insertAll(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<Person>> loadFromDb() {
                return mPersonDao.getPeople();
            }

            @NonNull
            @Override
            protected Call<People> createCall() {
                page += 1;
                SwapiEndpoints apiService = RetrofitClient.getRetrofitInstance().create(SwapiEndpoints.class);
                return apiService.getPeople(page);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Person> data) {
                return Utils.isNullOrEmpty(data);
            }
        }.getAsLiveData();
    }

    public LiveData<Person> getPerson(String id) {
        return mPersonDao.getPerson(id);
    }

}
