package moviesearch.example.com.moviesearch;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.List;
import java.util.concurrent.Callable;

import moviesearch.example.com.moviesearch.data.Tvshow;
import moviesearch.example.com.moviesearch.utilities.JSONUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PUNEETU on 18-01-2018.
 */

public class Displaytvshows extends AppCompatActivity {

    SearchView searchTvShows;
    String searchQuery;
    private RecyclerView mtvshowsview;
    ProgressBar progressBar;
    private Subscription showSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.display_tv_shows);
        initializeControls();
        createObservable();
    }

    private void initializeControls() {

        mtvshowsview = (RecyclerView) findViewById(R.id.recyclerview_tv_shows);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator_tv_shows);


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
        searchTvShows = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (!TextUtils.isEmpty(Displaytvshows.this.searchQuery)) {
            searchTvShows.setQuery(Displaytvshows.this.searchQuery, false);
            searchTvShows.clearFocus();
        }
        handleSearchQuery(searchTvShows);
    }

    private void createObservable() {
        Observable<List<Tvshow>> tvShowObservable
                = Observable.fromCallable(new Callable<List<Tvshow>>() {
            @Override
            public List<Tvshow> call() throws Exception {
                return JSONUtil.getPopularTvShows(searchQuery);
            }
        });
        showSubscription = tvShowObservable.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Tvshow>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Tvshow> tvshows) {
                        /*Populate recycler view and bind it to an adapter*/
                    }
                });
    }

    private void handleSearchQuery(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (showSubscription != null && !showSubscription.isUnsubscribed()) {
            showSubscription.unsubscribe();
        }
    }


}
