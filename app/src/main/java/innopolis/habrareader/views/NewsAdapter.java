package innopolis.habrareader.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import innopolis.habrareader.R;
import innopolis.habrareader.models.News;
import innopolis.habrareader.utilities.DownloadImageTask;

/**
 * Adapter for News recycler view
 * Created by davlet on 7/21/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    List<News> newsList;
    LayoutInflater layoutInflater;
    Context context;
    OnNewsClickListener onNewsClickListener;

    public NewsAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        new DownloadImageTask(holder.imageViewNews).execute(newsList.get(position).getImageUrl());
//        newsList.get(position).setBitmapImage(holder.imageViewNews.getDrawingCache());
        holder.textViewNewsTitle.setText(newsList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setOnNewsClickListener(OnNewsClickListener onNewsClickListener) {
        this.onNewsClickListener = onNewsClickListener;
    }

    /**
     * Holder for News objects, required for News adapter
     */
    class NewsHolder extends RecyclerView.ViewHolder {
        CardView cardViewNews;
        ImageView imageViewNews;
        TextView textViewNewsTitle;

        public NewsHolder(View itemView) {
            super(itemView);
            cardViewNews = (CardView) itemView.findViewById(R.id.cardViewNews);
            imageViewNews = (ImageView) itemView.findViewById(R.id.imageViewNews);
            textViewNewsTitle = (TextView) itemView.findViewById(R.id.textViewNewsTitle);
            cardViewNews.setOnClickListener(onCardClickListener);
        }

        private View.OnClickListener onCardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNewsClickListener.onNewsClick(v, getAdapterPosition());
            }
        };
    }
}
