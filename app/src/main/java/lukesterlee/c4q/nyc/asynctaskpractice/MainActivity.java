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
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// How it works : The user clicks button "Load" then get the latest 20 images from Flickr and display in GridView.

public class MainActivity extends ActionBarActivity {

    GridView mGridView;
    Button buttonReload;

    ImageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }


    private void setUpListener(boolean isResumed) {
        if (!isResumed) {
            buttonReload.setOnClickListener(null);
        } else {
            buttonReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO : Step 6 - start the AsyncTask!

                    new AsyncLoading().execute();
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

    private class AsyncLoading extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            // TODO : Step 3 - by using FlickrGetter.java, get latest 20 images' Urls from Flickr and return the result.
            //in order to use method getBitmapList() , need this instance.
            FlickrGetter flickrGetter = new FlickrGetter();
            //store the urls into a list.
            List<String> imageUrls = new ArrayList<String>();

            try {
                imageUrls = flickrGetter.getBitmapList();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Urls","doInBackground"+ imageUrls.size());
            return imageUrls;
        }

        @Override
        protected void onPostExecute(List<String> imageList) {
            // TODO : Step 5 - Now we have ImageAdapter and the data(list), post the picture!
            //list.size(), array.length()
            Log.d("Urls",imageList.size()+"");

        }
    }
}
