package lukesterlee.c4q.nyc.asynctaskpractice;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getBitmapList() throws JSONException, IOException {
        List<String> imageList = new ArrayList<String>();

        String jsonString = getJsonString();
        if (jsonString != null) {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);
                JSONObject media = item.getJSONObject("media");
                String imageUrl = media.getString("m");

                if (imageUrl != null) {
                    imageList.add(imageUrl);
                }
            }
        }
        return imageList;
    }
}