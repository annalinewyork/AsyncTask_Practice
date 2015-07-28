package lukesterlee.c4q.nyc.asynctaskpractice;

import android.graphics.Bitmap;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

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

    public static final String
            FLICKR_JSON_API = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";

    OkHttpClient client = new OkHttpClient();

    // TODO : Step 1 - complete this method,return the entire json string.
    public String getJsonString() throws IOException {

        Request request = new Request.Builder()
                .url(FLICKR_JSON_API)
                .build();

        Response response = client.newCall(request).execute();
        //store jason files into jsonString.
        String jsonString = response.body().string();
        Log.d("FlickrGetter",jsonString);
        return jsonString;
    }


    // TODO : Step 2 - by using Step 1's result, get 20 images' url addresses and save into ArrayList in String.
    public List<String> getBitmapList() throws JSONException, IOException {
        List<String> imageUrlList = new ArrayList<String>();

        JSONObject object = new JSONObject(getJsonString());
        JSONArray item = object.getJSONArray("items");

        for (int i=0; i<20; i++){
            //to get the date into an jsonArray, just need to put the index number into ();
            // since this is a FOR LOOP!
            JSONObject myObject = item.getJSONObject(i);
            JSONObject media = myObject.getJSONObject("media");
            String url = media.getString("m");
            //since this is a list<String>, so do not need "/n"
            imageUrlList.add(url);
        }
        Log.d("FlickrGetter",imageUrlList.size()+"");
        return imageUrlList;
    }
}