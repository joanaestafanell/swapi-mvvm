package joana.test.swapimaster.ui.master;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import joana.test.swapimaster.R;
import joana.test.swapimaster.data.model.Person;
import joana.test.swapimaster.ui.detail.DetailActivity;
import joana.test.swapimaster.util.Resource;

import java.util.List;

public class MasterActivity extends AppCompatActivity {

    private MasterViewModel mViewModel;
    private MasterAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master);

        mViewModel = ViewModelProviders.of(this).get(MasterViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new MasterAdapter(mViewModel);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayout.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        //Set listeners to reactive data
        mViewModel.getPeople().observe(this, new Observer<Resource<List<Person>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Person>> listResource) {
                if(listResource != null) {
                    switch (listResource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            mProgressBar.setVisibility(View.GONE);
                            if (listResource.data != null)
                                mAdapter.setPeople(listResource.data);
                            break;
                        case ERROR:
                            mProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), getString(R.string.loading_error_msg), Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }
        });

        mViewModel.onPersonClick().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String id) {
                //start detail activity
                Intent intent = new Intent(MasterActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_ID, id);
                startActivity(intent);
            }
        });

    }


}
