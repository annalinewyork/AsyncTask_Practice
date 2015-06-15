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

    // TODO : Step 1 - complete this method,return the entire json string.
    public String getJsonString() throws IOException {

        return null;
    }

    // TODO : Step 2 - by using Step 1's result, get 20 images' url addresses and save into ArrayList in String.
    public List<String> getBitmapList() throws JSONException, IOException {
        List<String> imageUrlList = new ArrayList<String>();


        return imageUrlList;
    }
}