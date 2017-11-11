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

/**
 * Created by PUNEETU on 06-03-2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> movies;
    private final MovieAdapterOnclickHandler mOnclickHandler;
    private Context context;

    public MovieAdapter(MovieAdapterOnclickHandler mOnclickHandler, Context context){
        this.mOnclickHandler = mOnclickHandler;
        this.context = context;
    }

    public interface MovieAdapterOnclickHandler{

        void listItemClickListener(String imdbId);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView year;
        ImageView poster;

        public MovieAdapterViewHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.movie_title);
            year = (TextView)view.findViewById(R.id.year);
            poster = (ImageView)view.findViewById(R.id.list_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){

            mOnclickHandler.listItemClickListener(movies.get(getAdapterPosition()).getImdbID());
        }

    }

        @Override
        public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

            Context context = parent.getContext();
            int id = R.layout.list_item;
            boolean shouldAttachToParentImmediately = false;
            View view = LayoutInflater.from(context).inflate(id, parent, shouldAttachToParentImmediately);
            return new MovieAdapterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieAdapter.MovieAdapterViewHolder holder, int position){
            holder.title.setText(movies.get(position).getTitle());
            holder.year.setText(movies.get(position).getYear());
            Picasso.with(this.context).load(movies.get(position).getPoster()).into(holder.poster);
        }


    @Override
    public int getItemCount(){
        if(null == movies){
            return 0;
        }else{
            return movies.size();
        }
    }

    public void setMovies(List<Movie> movies){

        this.movies = movies;
        notifyDataSetChanged();
    }



}
