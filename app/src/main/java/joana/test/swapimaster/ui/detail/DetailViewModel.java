package joana.test.swapimaster.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import joana.test.swapimaster.data.Repo;
import joana.test.swapimaster.data.model.Character;
import joana.test.swapimaster.data.model.Film;

public class DetailViewModel extends AndroidViewModel {

    private Repo mRepo;

    public LiveData<Character> character;

    public MediatorLiveData<Film> mFilms;

    public DetailViewModel(@NonNull Application application) {
        super(application);

        mRepo = new Repo(application);
    }

    public void loadCharacter(String id){

        character = mRepo.getCharacter(id);

        //We should apply the same logic to Species, Vehicles and Starships
        mFilms = mRepo.getFilmsByCharacterId(id);

    }

}
