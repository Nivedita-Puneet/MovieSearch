package moviesearch.example.com.moviesearch;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import moviesearch.example.com.moviesearch.POJO.Movie;
import moviesearch.example.com.moviesearch.Utilities.NetworkUtils;

/**
 * Created by PUNEETU on 03-03-2017.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {


    private static final String LOG_TAG = MovieLoader.class.getSimpleName();
    private String movieToSearch;

    public MovieLoader(Context context, String movieToSearch){
        super(context);
        this.movieToSearch = movieToSearch;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground(){
        List<Movie> movies = null;
        if(movieToSearch == null){
            return null;
        }
        URL url = NetworkUtils.buildURL(movieToSearch);
        String 
             serverResponse  = null;
        try{
             serverResponse = NetworkUtils.makeHttpRequest(url);
             movies = NetworkUtils.getMoviesFromJSON(serverResponse);

            if(movies != null)
             return  movies;
        }catch (IOException exception){
            Log.e(LOG_TAG, exception.getLocalizedMessage());
        }catch (MovieNotFoundException exception){
            Log.e(LOG_TAG, exception.getLocalizedMessage());
        }
            return null;
    }
}
