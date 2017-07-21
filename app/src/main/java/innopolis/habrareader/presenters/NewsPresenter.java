package innopolis.habrareader.presenters;

import innopolis.habrareader.views.INewsListView;

/**
 * Presenter for NewsListView, currently doesn't have methods
 * Created by davlet on 7/21/17.
 */

public class NewsPresenter {
    INewsListView newsListView;

    public NewsPresenter(INewsListView newsListView) {
        this.newsListView = newsListView;
    }
}
