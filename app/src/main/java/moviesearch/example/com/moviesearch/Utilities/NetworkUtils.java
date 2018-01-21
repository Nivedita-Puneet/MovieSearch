package moviesearch.example.com.moviesearch.utilities;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import moviesearch.example.com.moviesearch.MovieNotFoundException;
import moviesearch.example.com.moviesearch.data.Movie;

/**
 * Created by Nivedita Nilugal on 01-03-2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.themoviedb.org/3/search/movie?";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w92/";

    /*The following are parameters used in order to build base URL using Android URL Builder class*/

    private static int number = 1;
    private static final String tmdb_key = "7b4412b478e1878545a11d9d1beeb3b6";

    private static final String API_KEY = "api_key";
    private static final String QUERY_PARAM = "query";
    private static final String PAGE = "page";

    public static final String MOVIE_DETAIL_URL = "https://api.themoviedb.org/3/movie/";


    /*The following details will be used to Build Url for tv shows*/
    private static final String TV_SHOW_BASE_URL = "https://api.themoviedb.org/3/search/tv?";

    /*The method returns you a BASE URL from which we can get the response*/
    public static URL buildURL(String movieToSearch) {

        Uri buildUri = Uri.parse(BASE_URL).buildUpon().
                appendQueryParameter(API_KEY, tmdb_key).
                appendQueryParameter(QUERY_PARAM, movieToSearch).
                appendQueryParameter(PAGE, String.valueOf(number))
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException exception) {
            Log.e(TAG, exception.getLocalizedMessage());
        }

        return url;
    }

    public static URL buildMovieDetailUrl(String imdbId) {

        //https://api.themoviedb.org/3/movie/332835?api_key=7b4412b478e1878545a11d9d1beeb3b6&language=en-US

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org").appendPath("3")
                .appendPath("movie").appendPath(imdbId).
                appendQueryParameter(API_KEY, tmdb_key).build();
        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        Log.i(TAG, url.toString());
        return url;
    }

    public static URL buildTvShowsUrl(String searchTvShow) {
        Uri buildUri = Uri.parse(TV_SHOW_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY, tmdb_key)
                .appendQueryParameter(QUERY_PARAM, searchTvShow).build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return url;
    }
    /*Write a method to stream and get the results from server in JSON format;
      Also check the http status code to see if you get an appropriate result*/

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(150000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            /*check the response code to identify if response was obtained successfully*/
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.i(TAG, "Error response code" + ":" + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retriving the results of movie" + e.getLocalizedMessage());
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }

        }
        return output.toString();
    }

    public static List<Movie> getMoviesFromJSON(String json) throws MovieNotFoundException {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        List<Movie> movies = new ArrayList<Movie>();
        try {
            JSONObject baseJSONResponse = new JSONObject(json);
            String validateResults = baseJSONResponse.getString("total_results");
            Log.i(TAG, "" + validateResults);
            if (validateResults.equals(String.valueOf(0)))
                throw new MovieNotFoundException(baseJSONResponse.getString("Error"));
            JSONArray searchArray = baseJSONResponse.getJSONArray("results");
            for (int i = 0; i < searchArray.length(); i++) {
                JSONObject extractMovieAttributes = searchArray.getJSONObject(i);
                String title = extractMovieAttributes.getString("title");
                String year = extractMovieAttributes.getString("release_date");
                String imdbID = extractMovieAttributes.getString("id");
                String poster = extractMovieAttributes.getString("poster_path");
                Movie movie = new Movie(title, year, imdbID, poster);
                movies.add(movie);
            }


        } catch (JSONException exception) {

            Log.e(TAG, exception.getLocalizedMessage());
        }
        return movies;
    }


}
