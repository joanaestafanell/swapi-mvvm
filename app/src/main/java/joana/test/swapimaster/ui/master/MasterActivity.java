package joana.test.swapimaster.ui.master;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import joana.test.swapimaster.R;
import joana.test.swapimaster.data.model.Character;
import joana.test.swapimaster.ui.detail.DetailActivity;
import joana.test.swapimaster.util.Resource;

import java.util.List;

/**
 * Master/Detail app entry point: Displays people entity list, with a SearchView box inside the action bar.
 */
public class MasterActivity extends AppCompatActivity {

    private MasterViewModel mViewModel;
    private MasterAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master);

        mViewModel = ViewModelProviders.of(this).get(MasterViewModel.class);

        initUI();

        //Set listeners to reactive data
        mViewModel.getPeople().observe(this, new Observer<Resource<List<Character>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Character>> listResource) {
                if (listResource != null) {
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

    /**
     * Set Search box in main screen action bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mProgressBar.setVisibility(View.VISIBLE);
                mViewModel.getSearchResults(query).observe(MasterActivity.this, new Observer<Resource<List<Character>>>() {
                    @Override
                    public void onChanged(@Nullable Resource<List<Character>> listResource) {
                        if (listResource != null) {
                            switch (listResource.status) {
                                case LOADING:
                                    mProgressBar.setVisibility(View.VISIBLE);
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
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                mViewModel.getPeople().observe(MasterActivity.this, new Observer<Resource<List<Character>>>() {
                    @Override
                    public void onChanged(@Nullable Resource<List<Character>> listResource) {
                        if (listResource != null) {
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
                return true;
            }
        });

        return true;
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new MasterAdapter(mViewModel);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayout.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);


    }
}
