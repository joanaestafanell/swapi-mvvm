package joana.test.swapimaster.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import joana.test.swapimaster.data.model.Film;
import joana.test.swapimaster.data.model.People;
import joana.test.swapimaster.data.model.Character;
import joana.test.swapimaster.data.network.RetrofitClient;
import joana.test.swapimaster.data.network.SwapiEndpoints;
import joana.test.swapimaster.data.room.RoomDB;
import joana.test.swapimaster.data.room.model.CharacterDao;
import joana.test.swapimaster.util.Utils;
import joana.test.swapimaster.util.Resource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * Class that follows Repository pattern: is the only intermediate between app and local/remote data.
 * Implementation might be hided with an Interface.
 *
 */

public class Repo {

    private CharacterDao mCharacterDao;

    private static int page = 0;

    final MutableLiveData<Resource<List<Character>>> mSearchResult = new MutableLiveData<>();
    private List<Character> mTempList = new ArrayList<>();
    private MediatorLiveData<Film> mFilms = new MediatorLiveData<>();

    public Repo(Application application) {
        RoomDB db = RoomDB.getDatabase(application);

        mCharacterDao = db.peopleDao();
    }

    /**
     * Only fetch network data if local db is empty, otherwise display persisted data
     *
     * @return Observable People list
     */
    public LiveData<Resource<List<Character>>> getPeople() {

        return new NetworkBoundPaginationResource<List<Character>, People>() {
            @Override
            protected void saveCallResult(@NonNull People item) {
                //Adapt server data
                for (ListIterator characterIt = item.getResults().listIterator(); characterIt.hasNext(); ) {
                    List<String> filmsUrls = ((Character) characterIt.next()).getFilms();
                    for (ListIterator filmsIt = filmsUrls.listIterator(); filmsIt.hasNext(); ) {
                        String filmId = Utils.getFilmIdFromUrl((String) filmsIt.next());
                        filmsIt.set(filmId);
                    }
                }
                mCharacterDao.insertAll(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<Character>> loadFromDb() {
                return mCharacterDao.getPeople();
            }

            @NonNull
            @Override
            protected Call<People> createCall() {
                page += 1;
                SwapiEndpoints swapiEndpoints = RetrofitClient.getRetrofitInstance().create(SwapiEndpoints.class);
                return swapiEndpoints.getPeople(page);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Character> data) {
                return Utils.isNullOrEmpty(data);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Character>>> getSearchResults(String text) {
        mTempList.clear();
        fetchSearchResults(text, 1);
        return mSearchResult;
    }

    private void fetchSearchResults(String text, int pageIndex) {
        SwapiEndpoints swapiEndpoints = RetrofitClient.getRetrofitInstance().create(SwapiEndpoints.class);
        Call<People> call = swapiEndpoints.searchInPeople(text, pageIndex);
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                if (response.isSuccessful()) {
                    if (!Utils.isNullOrEmpty(response.body().getNext())) {
                        mTempList.addAll(response.body().getResults());
                        mSearchResult.setValue(Resource.loading(mTempList));
                        int next = Utils.getNextPageFromURL(response.body().getNext());
                        fetchSearchResults(text, next);
                    } else {
                        mTempList.addAll(response.body().getResults());
                        mSearchResult.setValue(Resource.success(mTempList));
                    }
                }
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }

    public LiveData<Character> getCharacter(String id) {
        return mCharacterDao.getCharacter(id);
    }

    public MediatorLiveData<Film> getFilmsByCharacterId(String id){

        new getFilmsByCharacterId().execute(id);
        return mFilms;
    }

    private class getFilmsByCharacterId extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String id = strings[0];

            List<String> list = mCharacterDao.getBasicCharacter(id).getFilms();
            for (String filmId: list
                    ) {
                LiveData<Film> film = getFilmByEpisodeId(Integer.parseInt(filmId));
                mFilms.addSource(film, newData -> mFilms.setValue(newData));
            }

            return null;
        }
    }

    public LiveData<Film> getFilmByEpisodeId(Integer id) {
        MutableLiveData<Film> result = new MutableLiveData<>();
        //Fetch film from remote
        SwapiEndpoints swapiEndpoints = RetrofitClient.getRetrofitInstance().create(SwapiEndpoints.class);
        Call<Film> call = swapiEndpoints.getFilm(id);
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful())
                    result.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {

            }
        });

        return result;
    }

}
