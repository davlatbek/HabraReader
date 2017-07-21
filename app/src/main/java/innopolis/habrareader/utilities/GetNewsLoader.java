package innopolis.habrareader.utilities;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import innopolis.habrareader.models.News;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

/**
 * Loader class that retrieves news from url asynchronously
 * Created by davlet on 7/21/17.
 */
public class GetNewsLoader extends AsyncTaskLoader {
    private URL url;
    private List<News> newsList;

    public GetNewsLoader(Context context, URL url, List<News> newsList) {
        super(context);
        this.url = url;
        this.newsList = newsList;
    }

    /**
     * Actual method for getting news in background
     * @return list of news
     */
    @Override
    public Object loadInBackground() {
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);
            XmlPullParser newsParser = xmlPullParserFactory.newPullParser();
            newsParser.setInput(getInputStream(url), "UTF-8");

            int eventType = newsParser.getEventType();
            News news = null;

            while (eventType != XmlPullParser.END_DOCUMENT){
                String name;
                switch (eventType){
                    case START_TAG:
                        name = newsParser.getName();
                        if (name.equalsIgnoreCase("item")) {
                            news = new News();
                        } else if (news != null) {
                            if (name.equalsIgnoreCase("title"))
                                news.setTitle(newsParser.nextText());
                            if (name.equalsIgnoreCase("description"))
                                news.setNewsDescription(newsParser.nextText());
                            if (name.equalsIgnoreCase("category"))
                                news.setCategory(
                                        news.getCategory() + " | " + newsParser.nextText());
                            if (name.equalsIgnoreCase("pubdate"))
                                news.setPublicationDate(newsParser.nextText());
                            if (name.equalsIgnoreCase("link"))
                                news.setLink(newsParser.nextText());
                            if (name.equalsIgnoreCase("dc:creator"))
                                news.setCreatorName(newsParser.nextText());
                        }
                        break;
                    case END_TAG:
                        name = newsParser.getName();
                        if (name.equalsIgnoreCase("item")) {
                            newsList.add(news);
                        }
                        break;
                    default: break;
                }
                eventType = newsParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            Log.e("error", "Couldn't parse xml");
        }
        return newsList;
    }

    @Override
    public void deliverResult(Object data) {
        super.deliverResult(data);
    }

    /**
     * Getting input stream from specified url
     * @param url link
     * @return InputStream
     */
    public InputStream getInputStream(URL url){
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("erro", "Unable to open connection and get input stream");
        }
        return null;
    }
}
