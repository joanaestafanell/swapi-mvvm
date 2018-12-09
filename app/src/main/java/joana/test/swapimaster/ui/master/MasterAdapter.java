package joana.test.swapimaster.ui.master;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.ViewGroup;
import joana.test.swapimaster.BR;
import joana.test.swapimaster.R;
import joana.test.swapimaster.data.model.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that binds the VM to the RecyclerView to map UI data in an easy and clean way
 */

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.MasterViewHolder> {

    private MasterViewModel mViewModel;
    private List<Character> mPeople = new ArrayList<>();

    public MasterAdapter(MasterViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    @NonNull
    @Override
    public MasterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.card_master, viewGroup, false);
        return new MasterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterViewHolder masterViewHolder, int i) {
        masterViewHolder.bind(mPeople.get(i));
    }

    @Override
    public int getItemCount() {
        return mPeople.size();
    }

    public void setPeople(List<Character> people){
        this.mPeople = people;
        notifyDataSetChanged();
    }

    class MasterViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public MasterViewHolder(ViewDataBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Character item){
            binding.setVariable(BR.character, item);
            binding.setVariable(BR.viewmodel, mViewModel);
            binding.executePendingBindings();
        }
    }
}
