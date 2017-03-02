package moviesearch.example.com.moviesearch.Utilities;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

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


    private static final String QUERY_PARAM = "t";
    private static final String QUERY_FORMAT = "r";
    private static final String ROTTEN_TOMATO_RATING = "tomatoes";
    private static final  String ENTERTAINING_TYPE = "type";

    /*The method returns you a BASE URL from which we can get the response*/
    public URL buildURL(String movieToSearch){

        Uri buildUri = Uri.parse(BASE_URL).buildUpon().
                        appendQueryParameter(QUERY_PARAM,movieToSearch).
                        appendQueryParameter(ENTERTAINING_TYPE,type).
                        appendQueryParameter(QUERY_FORMAT,format).
                        appendQueryParameter(ROTTEN_TOMATO_RATING,rating).build();
        URL url = null;
            try {
                url = new URL(buildUri.toString());
            }catch (MalformedURLException exception){
                Log.e(TAG, exception.getLocalizedMessage());
            }

        return url;
    }


}
