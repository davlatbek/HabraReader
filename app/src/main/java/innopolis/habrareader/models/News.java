package innopolis.habrareader.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by davlet on 7/21/17.
 */

public class News implements Parcelable {
    private String title;
    private String newsDescription;
    private String link;
    private String publicationDate;
    private String creatorName;
    private String category;
    private Bitmap bitmapImage;

    public News(String title, String newsDescription,
                String link, String publicationDate,
                String creatorName, String category) {
        this.title = title;
        this.newsDescription = newsDescription;
        this.link = link;
        this.publicationDate = publicationDate;
        this.creatorName = creatorName;
        this.category = category;
    }

    public News() {
        category = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getBitmapImage() {
        return bitmapImage;
    }

    public void setBitmapImage(Bitmap bitmapImage) {
        this.bitmapImage = bitmapImage;
    }

    public String getImageUrl(){
        String all = getNewsDescription();
        String s = "<img src=\"";
        int ix = all.indexOf(s)+s.length();
        Log.i("key", all.substring(ix, all.indexOf("\"", ix+1)));
        return all.substring(ix, all.indexOf("\"", ix+1));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.newsDescription);
        dest.writeString(this.link);
        dest.writeString(this.publicationDate);
        dest.writeString(this.creatorName);
        dest.writeString(this.category);
    }

    protected News(Parcel in) {
        this.title = in.readString();
        this.newsDescription = in.readString();
        this.link = in.readString();
        this.publicationDate = in.readString();
        this.creatorName = in.readString();
        this.category = in.readString();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
