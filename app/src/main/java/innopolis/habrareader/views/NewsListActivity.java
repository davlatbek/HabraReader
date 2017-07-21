package innopolis.habrareader.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import innopolis.habrareader.R;
import innopolis.habrareader.models.News;
import innopolis.habrareader.presenters.NewsPresenter;
import innopolis.habrareader.utilities.GetNewsLoader;

/**
 * Class for showing list of retrieved news from url
 */
public class NewsListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks, OnNewsClickListener, INewsListView {
    private List<News> mNewsList;
    private RecyclerView mRecyclerViewNews;
    private NewsAdapter mNewsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private URL mRssUrl;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewsPresenter presenter;

    /**
     * Initialize variables on activity creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        presenter = new NewsPresenter(this);
        try {
            mRssUrl = new URL("https://habrahabr.ru/rss/hubs/all/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("error", "Specified string couldn't be parsed or has wrong protocol");
        }
        mNewsList = new ArrayList<>();
        initViews();
        getSupportLoaderManager().initLoader(0, null, this);
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    /**
     * Initialize elements of this view and set listeners
     */
    private void initViews() {
        mRecyclerViewNews = (RecyclerView) findViewById(R.id.recyclerViewNews);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mNewsAdapter = new NewsAdapter(mNewsList, this);
        mNewsAdapter.setOnNewsClickListener(this);
        mRecyclerViewNews.setLayoutManager(mLayoutManager);
        mRecyclerViewNews.setAdapter(mNewsAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSupportLoaderManager().getLoader(0).forceLoad();
                Toast.makeText(NewsListActivity.this, "Refreshed list", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Return newly created GetNewsLoader object
     * @param id Id of loader
     * @param args arguments
     * @return Loader
     */
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new GetNewsLoader(this, mRssUrl, mNewsList);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mNewsList = null;
    }

    /**
     * Started when clicked on news card
     * @param view
     * @param position
     */
    @Override
    public void onNewsClick(View view, int position) {
        goToNewsPage(position);
    }

    /**
     * This method opens new activity for clicked news
     * @param newsPosition
     */
    @Override
    public void goToNewsPage(int newsPosition) {
        Intent intent = new Intent(this, NewsPageActivity.class);
        intent.putExtra("news", mNewsList.get(newsPosition));
        startActivity(intent);
    }
}
