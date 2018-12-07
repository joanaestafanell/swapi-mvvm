package joana.test.swapimaster.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import joana.test.swapimaster.R;
import joana.test.swapimaster.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_ID = "extra_id";

    private String mPersonId;
    private DetailViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();

    }

    private void initDataBinding(){
        ActivityDetailBinding activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mPersonId = getIntent().getStringExtra(EXTRA_ID);
        mViewModel.loadPerson(mPersonId);
        activityDetailBinding.setViewmodel(mViewModel);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
