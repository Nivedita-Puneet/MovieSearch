package moviesearch.example.com.moviesearch.Utilities;

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
import java.util.ArrayList;
import java.util.List;

import moviesearch.example.com.moviesearch.MovieNotFoundException;
import moviesearch.example.com.moviesearch.POJO.Movie;

/**
 * Created by PUNEETU on 01-03-2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL = "http://www.omdbapi.com/?";

    /*The following are parameters used in order to build base URL using Android URL Builder class*/

    private static final String format = "json";
    private static final String rating = "true";
    private static final String type = "Movie";


    private static final String QUERY_PARAM = "s";
    private static final String QUERY_FORMAT = "r";
    private static final String ROTTEN_TOMATO_RATING = "tomatoes";
    private static final String ENTERTAINING_TYPE = "type";



    /*The method returns you a BASE URL from which we can get the response*/
    public static URL buildURL(String movieToSearch) {

        Uri buildUri = Uri.parse(BASE_URL).buildUpon().
                appendQueryParameter(QUERY_PARAM, movieToSearch).
                appendQueryParameter(ENTERTAINING_TYPE, type).
                appendQueryParameter(QUERY_FORMAT, format).
                appendQueryParameter(ROTTEN_TOMATO_RATING, rating).build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException exception) {
            Log.e(TAG, exception.getLocalizedMessage());
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
        }catch (IOException e) {
            Log.e(TAG, "Problem retriving the results of movie" + e.getLocalizedMessage());
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }

        }
        return output.toString();
    }

    public static List<Movie> getMoviesFromJSON(String json) throws MovieNotFoundException{

        if(TextUtils.isEmpty(json)){
            return null;
        }

        List<Movie> movies = new ArrayList<Movie>();
        try{
            JSONObject baseJSONResponse = new JSONObject(json);
            boolean validateResults = baseJSONResponse.getBoolean("Response");
            Log.i(TAG, ""+ validateResults);
                if(!validateResults)
                    throw new MovieNotFoundException(baseJSONResponse.getString("Error"));
            JSONArray searchArray = baseJSONResponse.getJSONArray("Search");
            for(int i=0; i<searchArray.length(); i++){
                JSONObject extractMovieAttributes = searchArray.getJSONObject(i);
                String title = extractMovieAttributes.getString("Title");
                String year = extractMovieAttributes.getString("Year");
                String imdbID = extractMovieAttributes.getString("imdbID");
                String poster = extractMovieAttributes.getString("Poster");
                Movie movie = new Movie(title,year,imdbID,poster);
                movies.add(movie);
            }


        }catch (JSONException exception){

            Log.e(TAG, exception.getLocalizedMessage());
        }
            return movies;
    }



}
