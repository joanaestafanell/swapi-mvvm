package joana.test.swapimaster.ui.master;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import joana.test.swapimaster.data.Repo;
import joana.test.swapimaster.data.model.Person;
import joana.test.swapimaster.util.Resource;
import joana.test.swapimaster.util.SingleLiveEvent;

import java.util.List;

public class MasterViewModel extends AndroidViewModel {

    private Repo mRepo;

    private final SingleLiveEvent<String> mOnPersonClick = new SingleLiveEvent<>();

    public MasterViewModel(@NonNull Application application) {
        super(application);
        mRepo = new Repo(application);
    }

    public LiveData<Resource<List<Person>>> getPeople(){
        return mRepo.getPeople();
    }

    public SingleLiveEvent<String> onPersonClick(){
        return mOnPersonClick;
    }

    public void showDetails(String id) {
        //Propagate event
        mOnPersonClick.setValue(id);
    }
}
