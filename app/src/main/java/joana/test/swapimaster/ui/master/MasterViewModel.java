package joana.test.swapimaster.ui.master;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import joana.test.swapimaster.data.Repo;
import joana.test.swapimaster.data.model.Character;
import joana.test.swapimaster.util.Resource;
import joana.test.swapimaster.util.SingleLiveEvent;

import java.util.List;

public class MasterViewModel extends AndroidViewModel {

    private Repo mRepo;

    private final SingleLiveEvent<String> mOnPersonClick = new SingleLiveEvent<>();
    private LiveData<Resource<List<Character>>> mPeople;

    public MasterViewModel(@NonNull Application application) {
        super(application);
        mRepo = new Repo(application);
        loadPeople();
    }

    public void loadPeople(){
        mPeople = mRepo.getPeople();
    }

    public LiveData<Resource<List<Character>>> getPeople() {
        return mPeople;
    }

    public LiveData<Resource<List<Character>>> getSearchResults(String text) {
        return mRepo.getSearchResults(text);
    }

    public SingleLiveEvent<String> onPersonClick(){
        return mOnPersonClick;
    }

    public void showDetails(String id) {
        //Propagate event
        mOnPersonClick.setValue(id);
    }
}
