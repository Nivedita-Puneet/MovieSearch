package moviesearch.example.com.moviesearch;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import java.util.List;

import moviesearch.example.com.moviesearch.POJO.Movie;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    String query;
    private static final int MOVIE_TASK_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
        getSearchViewFromMenu(menu);
        return true;
    }

    private void getSearchViewFromMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        handleSearchQuery(searchView);
    }


    private void handleSearchQuery(SearchView searchView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MainActivity.this.query = query;
                getSupportLoaderManager().initLoader(MOVIE_TASK_CODE, null, MainActivity.this);
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
        return new MovieLoader(MainActivity.this,MainActivity.this.query);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        if(data != null){
            for(int i=0; i<data.size(); i++){
                Log.i(TAG, data.get(i).getTitle());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
