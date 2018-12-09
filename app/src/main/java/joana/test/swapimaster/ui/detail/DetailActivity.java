package joana.test.swapimaster.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import joana.test.swapimaster.R;
import joana.test.swapimaster.data.model.Film;
import joana.test.swapimaster.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_ID = "extra_id";

    private String mPersonId;
    private DetailViewModel mViewModel;
    private FilmsAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();

        initUI();

        mViewModel.mFilms.observe(this, new Observer<Film>() {
            @Override
            public void onChanged(@Nullable Film film) {
                mProgressBar.setVisibility(View.GONE);
                if(film != null)
                    mAdapter.addFilm(film);
            }
        });
    }

    private void initDataBinding(){
        ActivityDetailBinding activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mPersonId = getIntent().getStringExtra(EXTRA_ID);
        mViewModel.loadCharacter(mPersonId);

        // Specify the current activity as the lifecycle owner.
        activityDetailBinding.setLifecycleOwner(this);
        activityDetailBinding.setViewmodel(mViewModel);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private void initUI() {
        initToolbar();
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_films);
        mAdapter = new FilmsAdapter();

        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayout.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
    }

}
