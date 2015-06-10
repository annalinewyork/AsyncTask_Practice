package lukesterlee.c4q.nyc.asynctaskpractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    GridView mGridView;
    Button buttonReload;

    ImageView test;


    AsyncLoading asyncImages;

    List<Bitmap> imageList;


    ImageAdapter adapter;

    public static final String FLICKR_JSON_API = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = (ImageView) findViewById(R.id.sample);


        initializeViews();
        initializeData();



        asyncImages = new AsyncLoading();
    }

    private void initializeData() {
        adapter = new ImageAdapter(getApplicationContext());
        mGridView.setAdapter(adapter);
    }

    private void refreshImages() {
        asyncImages.execute();
    }

    private void setUpListener(boolean isResumed) {
        if (!isResumed) {
            buttonReload.setOnClickListener(null);
        } else {
            buttonReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshImages();
                }
            });
        }
    }

    private void initializeViews() {
        mGridView = (GridView) findViewById(R.id.gridView);
        buttonReload = (Button) findViewById(R.id.button_reload);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpListener(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        setUpListener(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AsyncLoading extends AsyncTask<Void, Void, List<Bitmap>> {


        @Override
        protected List<Bitmap> doInBackground(Void... params) {

            String flickrJsonApi = FLICKR_JSON_API;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(flickrJsonApi);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                String jsonString = EntityUtils.toString(httpEntity);

                List<Bitmap> imageList = new ArrayList<Bitmap>();


                if (jsonString != null) {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        JSONObject media = item.getJSONObject("media");

                        String imageUrl = media.getString("m");

                        if (imageUrl != null) {
                            URL url = new URL(imageUrl);
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageList.add(bmp);

                        }



                    }

                }


                return imageList;
            }
            catch (Exception e) {
                Log.e("JSON", "couldn't get Json string.");
                return null;

            }


        }

        @Override
        protected void onPostExecute(List<Bitmap> imageList) {
            test.setImageBitmap(imageList.get(0));
            adapter.refreshImages(imageList);
        }
    }
}
