package lukesterlee.c4q.nyc.asynctaskpractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Luke on 6/10/2015.
 */
public class FlickrGetter {

    public static final String TAG = "FlickrGetter";

    public static final String FLICKR_JSON_API = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";

    public String getJsonString() throws IOException {
        URL jsonUrl = new URL(FLICKR_JSON_API);
        HttpURLConnection connection = (HttpURLConnection) jsonUrl.openConnection();
        connection.setConnectTimeout(0);
        connection.setReadTimeout(0);
        try {
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            return builder.toString();
        } catch (IOException ioe) {
            Log.e(TAG, "failed to get Json string");
        }
        return null;
    }

    public List<Bitmap> getBitmapList() throws JSONException, IOException {
        List<Bitmap> imageList = new ArrayList<Bitmap>();

        String jsonString = getJsonString();
        if (jsonString != null) {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                int num = jsonArray.length();
                JSONObject item = jsonArray.getJSONObject(i);
                JSONObject media = item.getJSONObject("media");
                String imageUrl = media.getString("m");

                if (imageUrl != null) {
                    URL url = null;
                    try {
                        url = new URL(imageUrl);
                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                        connection.setConnectTimeout(0);
                        connection.setReadTimeout(0);


                        Bitmap bmp = BitmapFactory.decodeStream(connection.getInputStream());
                        imageList.add(bmp);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException ioe) {
                        Log.e(TAG, "couldn't get json image");
                    }
                }
            }
        }
        return imageList;
    }
}