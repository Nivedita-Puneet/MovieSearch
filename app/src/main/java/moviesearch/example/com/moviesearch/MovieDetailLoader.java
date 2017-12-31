package moviesearch.example.com.moviesearch;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import moviesearch.example.com.moviesearch.data.MovieDetail;
import moviesearch.example.com.moviesearch.utilities.JSONUtil;
import moviesearch.example.com.moviesearch.utilities.NetworkUtils;

/**
 * Created by PUNEETU on 25-12-2017.
 */

public class MovieDetailLoader extends AsyncTaskLoader<MovieDetail> {

    private String TAG = MovieDetailLoader.class.getSimpleName();
    private String imdbId;
    MovieDetail movieDetail = null;

    public MovieDetailLoader(Context context, String imdbId) {
        super(context);
        this.imdbId = imdbId;
    }

    @Override
    protected void onStartLoading() {
        if (movieDetail != null) {
            deliverResult(movieDetail);
        } else {
            //mLoadingIndicator.setVisibility(View.VISIBLE);
            forceLoad();

        }

    }

    @Override
    public MovieDetail loadInBackground() {

        URL url = NetworkUtils.buildMovieDetailUrl(imdbId);
        String serverResponse = null;
        try {
            serverResponse = NetworkUtils.makeHttpRequest(url);
            movieDetail = JSONUtil.getMovieDetail(serverResponse);
            if (movieDetail != null)
                return movieDetail;
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    public void deliverResult(MovieDetail movieDetail) {
        super.deliverResult(movieDetail);
        this.movieDetail = movieDetail;
    }
}
