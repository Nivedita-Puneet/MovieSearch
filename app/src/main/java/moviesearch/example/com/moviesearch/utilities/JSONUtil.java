package moviesearch.example.com.moviesearch.utilities;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import moviesearch.example.com.moviesearch.MovieNotFoundException;
import moviesearch.example.com.moviesearch.data.Movie;
import moviesearch.example.com.moviesearch.data.MovieDetail;

/**
 * Created by PUNEETU on 25-12-2017.
 */

public class JSONUtil {

    private static String TITLE = "original_title";
    private static String DESCRIPTION = "overview";
    private static String POSTER_PATH = "poster_path";
    private static String YEAR = "release_date";

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

}
