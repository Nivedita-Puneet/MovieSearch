package moviesearch.example.com.moviesearch.utilities;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import moviesearch.example.com.moviesearch.MovieLoader;
import moviesearch.example.com.moviesearch.MovieNotFoundException;
import moviesearch.example.com.moviesearch.data.Movie;
import moviesearch.example.com.moviesearch.data.MovieDetail;
import moviesearch.example.com.moviesearch.data.Tvshow;

/**
 * Created by PUNEETU on 25-12-2017.
 */

public class JSONUtil {

    private static String TITLE = "original_title";
    private static String DESCRIPTION = "overview";
    private static String POSTER_PATH = "poster_path";
    private static String YEAR = "release_date";

    /*The following attributes will be used to show details of tv shows*/
    private static final String NAME = "name";
    private static final String FIRST_AIR_DATE = "first_air_date";
    private static final String RESULTS = "results";
    private static final String OVERVIEW = "overview";

    private static String TAG = JSONUtil.class.getSimpleName();

    public static MovieDetail getMovieDetail(String json) {
        MovieDetail detail = null;
        if (TextUtils.isEmpty(json))
            return null;
        try {
            JSONObject baseJSONResponse = new JSONObject(json);
            String title = baseJSONResponse.getString(TITLE);
            String poster = baseJSONResponse.getString(POSTER_PATH);
            String description = baseJSONResponse.getString(DESCRIPTION);
            String release_date = baseJSONResponse.getString(YEAR);
            detail = new MovieDetail(title, poster, description, release_date);

        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return detail;
    }

    public static List<Tvshow> getTvShows(String json) {
        List<Tvshow> tvshows = new ArrayList<>();
        if (TextUtils.isEmpty(json))
            return null;
        try {
            JSONObject baseJSONResponse = new JSONObject(json);
            JSONArray jsonArray = baseJSONResponse.getJSONArray(RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString(NAME);
                String show_promo = jsonObject.getString(POSTER_PATH);
                String overview = jsonObject.getString(OVERVIEW);
                String airDate = jsonObject.getString(FIRST_AIR_DATE);
                Tvshow tvshow = new Tvshow(name, show_promo, airDate, overview);
                tvshows.add(tvshow);

            }
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        if (tvshows == null)
            return null;
        else
            return tvshows;
    }

    public static List<Tvshow> getPopularTvShows(String query) {
        List<Tvshow> tvshows = null;
        if (query == null)
            return null;
        URL url = NetworkUtils.buildTvShowsUrl(query);
        String
                serverResponse = null;
        try {
            serverResponse = NetworkUtils.makeHttpRequest(url);
            Log.d(MovieLoader.class.getSimpleName(), serverResponse);
            tvshows = getTvShows(serverResponse);

            if (tvshows != null)
                return tvshows;
        } catch (IOException exception) {
            Log.e(TAG, exception.getLocalizedMessage());
        }
        return null;
    }
}
