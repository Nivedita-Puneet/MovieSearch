package moviesearch.example.com.moviesearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import moviesearch.example.com.moviesearch.data.Movie;
import moviesearch.example.com.moviesearch.data.Tvshow;
import moviesearch.example.com.moviesearch.utilities.NetworkUtils;

/**
 * Created by PUNEETU on 06-03-2017.
 */

public class TvshowsAdapter extends RecyclerView.Adapter<TvshowsAdapter.TvshowAdapterViewHolder> {

    private List<Tvshow> tvshows;
    //private final TvshowAdapterOnclickHandler mOnclickHandler;
    private Context context;

    public TvshowsAdapter(Context context) {
        // this.mOnclickHandler = mOnclickHandler;
        this.context = context;
    }

    public interface TvshowAdapterOnclickHandler {

        void listItemClickListener(String imdbId);
    }

    public class TvshowAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView year;
        ImageView poster;

        public TvshowAdapterViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.movie_title);
            year = (TextView) view.findViewById(R.id.year);
            poster = (ImageView) view.findViewById(R.id.list_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            //mOnclickHandler.listItemClickListener(movies.get(getAdapterPosition()).getImdbID());
        }

    }

    @Override
    public TvshowsAdapter.TvshowAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int id = R.layout.list_item;
        boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(context).inflate(id, parent, shouldAttachToParentImmediately);
        return new TvshowAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TvshowsAdapter.TvshowAdapterViewHolder holder, int position) {
        holder.title.setText(tvshows.get(position).getTitle());
        holder.year.setText(tvshows.get(position).getReleaseDate());
        Picasso.with(this.context).load(NetworkUtils.IMAGE_BASE_URL + tvshows.get(position).getPoster()).into(holder.poster);
    }


    @Override
    public int getItemCount() {
        if (null == tvshows) {
            return 0;
        } else {
            return tvshows.size();
        }
    }

    public void setMovies(List<Tvshow> tvshows) {

        this.tvshows = tvshows;
        notifyDataSetChanged();
    }


}
