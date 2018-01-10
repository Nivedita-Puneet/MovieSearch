package moviesearch.example.com.moviesearch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import moviesearch.example.com.moviesearch.data.MovieDetail;

/**
 * Created by PUNEETU on 07-03-2017.
 */

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieDetail> {

    String movie_id = null;
    private static final String LOG = DetailActivity.class.getSimpleName();
    private static final int MOVIE_DETAIL_TASK_CODE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        movie_id = getIntent().getExtras().getString("imdbId");
        Log.i(LOG, movie_id);

        initializeControls();


    }

    private void initializeControls() {
        getSupportLoaderManager().initLoader(MOVIE_DETAIL_TASK_CODE, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<MovieDetail> onCreateLoader(int id, Bundle args) {
        return new MovieDetailLoader(DetailActivity.this, DetailActivity.this.movie_id);
    }

    @Override
    public void onLoadFinished(Loader<MovieDetail> loader, MovieDetail data) {

        Log.i(LOG, data.getTitle() + " " + data.getDescription() + " " + data.getYear());
    }

    @Override
    public void onLoaderReset(Loader<MovieDetail> loader) {
        loader.startLoading();

    }
}
