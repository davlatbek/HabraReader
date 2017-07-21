package innopolis.habrareader.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import innopolis.habrareader.R;
import innopolis.habrareader.models.News;
import innopolis.habrareader.utilities.DownloadImageTask;

public class NewsPageActivity extends AppCompatActivity implements INewsPageView {
    ImageView imageViewNews;
    TextView textViewNewsTitle;
    TextView textViewNewsDesc;
    TextView textViewNewsPublicationDate;
    TextView textViewNewsCreator;
    TextView textViewNewsLink;
    TextView textViewNewsCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);
        initViews();
        setViews();
    }

    /**
     * Set values for every view element from parcel object
     */
    private void setViews() {
        News news = getIntent().getParcelableExtra("news");
        if (news != null){
            new DownloadImageTask(imageViewNews).execute(news.getImageUrl());
            textViewNewsTitle.setText(news.getTitle());
            String s = news.getNewsDescription();
            s = s.replaceAll("\\<.*?>","");
            textViewNewsDesc.setText(s);
            textViewNewsPublicationDate.setText(news.getPublicationDate());
            textViewNewsCreator.setText(news.getCreatorName());
            textViewNewsLink.setText(news.getLink());
            textViewNewsCategory.setText(news.getCategory());
        }
    }

    /**
     * Initialize view of this activity
     */
    private void initViews() {
        imageViewNews = (ImageView) findViewById(R.id.imageNews);
        textViewNewsTitle = (TextView) findViewById(R.id.textViewNewsTitle);
        textViewNewsDesc = (TextView) findViewById(R.id.textViewDescription);
        textViewNewsPublicationDate = (TextView) findViewById(R.id.textViewPublicationDate);
        textViewNewsCreator = (TextView) findViewById(R.id.textViewCreatorName);
        textViewNewsLink = (TextView) findViewById(R.id.textViewLink);
        textViewNewsCategory = (TextView) findViewById(R.id.textViewCategories);
    }
}
