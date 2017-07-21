package innopolis.habrareader.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Asynchronous task for downloadig images for the specified url
 * Created by davlet on 7/20/17.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    /**
     * Getting image from specified url in param[0]
     * @param params
     * @return Bitmap
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("error", "Couldn't open connection or decode stream");
        }
        return image;
    }

    /**
     * Set bitmap to the specified imageView
     * @param bitmap
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }
}
