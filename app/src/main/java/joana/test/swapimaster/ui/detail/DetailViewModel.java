package joana.test.swapimaster.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import joana.test.swapimaster.data.Repo;
import joana.test.swapimaster.data.model.Person;

public class DetailViewModel extends AndroidViewModel {

    private Repo mRepo;

    public LiveData<Person> person;

    public DetailViewModel(@NonNull Application application) {
        super(application);

        mRepo = new Repo(application);
    }

    public void loadPerson(String id){
        person = mRepo.getPerson(id);
    }
}
