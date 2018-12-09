package joana.test.swapimaster.ui.detail;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import joana.test.swapimaster.R;
import joana.test.swapimaster.BR;
import joana.test.swapimaster.data.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to feed character's films list inside DetailActivity. Uses data binding.
 */
public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder> {

    private List<Film> mFilms = new ArrayList<>();

    @NonNull
    @Override
    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_film, viewGroup, false);
        return new FilmsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {
        holder.bind(mFilms.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilms.size();
    }

    public void setFilms(List<Film> films){
        this.mFilms = films;
        notifyDataSetChanged();
    }

    public void addFilm(Film film){
        this.mFilms.add(film);
        notifyDataSetChanged();
    }

    class FilmsViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public FilmsViewHolder(ViewDataBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Film item){
            binding.setVariable(BR.film, item);
            binding.executePendingBindings();

        }
    }
}