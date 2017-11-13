package moviesearch.example.com.moviesearch;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

import moviesearch.example.com.moviesearch.data.Movie;
import moviesearch.example.com.moviesearch.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    String query;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    MovieAdapter movieAdapter;
    private static final int MOVIE_TASK_CODE = 101;
    private static final String MOVIE_TO_SEARCH = "movieToSearch";
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MOVIE_TO_SEARCH)) {
                MainActivity.this.query = savedInstanceState.getString(MOVIE_TO_SEARCH);
            }
        }

        initializeControls();

    }

    private void initializeControls() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movie);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(new MovieAdapter.MovieAdapterOnclickHandler() {
            @Override
            public void listItemClickListener(String imdbId) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("imdbId", imdbId);
                startActivity(intent);
            }
        }, MainActivity.this);
        getSupportLoaderManager().initLoader(MOVIE_TASK_CODE, null, MainActivity.this);
        mRecyclerView.setAdapter(movieAdapter);
    }

    private void showMoviesData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        getSearchViewFromMenu(menu);
        return true;
    }

    private void getSearchViewFromMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (!TextUtils.isEmpty(MainActivity.this.query)) {
            searchView.setQuery(MainActivity.this.query, false);
            searchView.clearFocus();
        }
        handleSearchQuery(searchView);
    }


    private void handleSearchQuery(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.this.query = query;
                movieAdapter.setMovies(null);
                getSupportLoaderManager().restartLoader(MOVIE_TASK_CODE, null, MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                MainActivity.this.query = newText;
                return true;
            }
        });
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(MainActivity.this, MainActivity.this.query);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (data != null) {
            showMoviesData();
            movieAdapter.setMovies(data);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        loader.startLoading();
    }

    @Override
    protected void onSaveInstanceState(Bundle output) {
        super.onSaveInstanceState(output);
        output.putString(MOVIE_TO_SEARCH, MainActivity.this.query);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
       /* if(state != null){
            if(state.containsKey(MOVIE_TO_SEARCH)){
                query = state.getString(MOVIE_TO_SEARCH);
            }
        }*/
    }

}
