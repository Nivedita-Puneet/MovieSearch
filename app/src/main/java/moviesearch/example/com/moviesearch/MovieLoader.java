package moviesearch.example.com.moviesearch;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import moviesearch.example.com.moviesearch.data.Movie;
import moviesearch.example.com.moviesearch.utilities.NetworkUtils;

/**
 * Created by PUNEETU on 03-03-2017.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {


    private static final String LOG_TAG = MovieLoader.class.getSimpleName();
    private String movieToSearch;
    List<Movie> movies = null;
    ProgressBar mLoadingIndicator;
    Activity activity;


    public MovieLoader(Context context, String movieToSearch, Activity activity) {
        super(context);
        this.activity = activity;
        this.movieToSearch = movieToSearch;
        mLoadingIndicator = (ProgressBar) activity.findViewById(R.id.pb_loading_indicator);

    }

    @Override
    protected void onStartLoading() {
        if (movies != null) {
            deliverResult(movies);
        } else {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            forceLoad();

        }

    }

    @Override
    public List<Movie> loadInBackground() {
        if (movieToSearch == null) {
            return null;
        }
        URL url = NetworkUtils.buildURL(movieToSearch);
        Log.d(MovieLoader.class.getSimpleName(), url.toString());
        String
                serverResponse = null;
        try {
            serverResponse = NetworkUtils.makeHttpRequest(url);
            Log.d(MovieLoader.class.getSimpleName(), serverResponse);
            movies = NetworkUtils.getMoviesFromJSON(serverResponse);

            if (movies != null)
                return movies;
        } catch (IOException exception) {
            Log.e(LOG_TAG, exception.getLocalizedMessage());
        } catch (MovieNotFoundException exception) {
            Log.e(LOG_TAG, exception.getLocalizedMessage());
        }
        return null;
    }

    public void deliverResult(List<Movie> movies) {
        super.deliverResult(movies);
        this.movies = movies;
    }

    public interface showProgressbar {

    }
}
