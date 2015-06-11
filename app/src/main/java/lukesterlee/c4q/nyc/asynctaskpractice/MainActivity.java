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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


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
            try {
                return new FlickrGetter().getBitmapList();
            } catch (JSONException e) {
                Log.e("JSON", "couldn't get Json image.");
            } catch (IOException e) {
                Log.e("JSON", "couldn't get Json string.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> imageList) {
            if (imageList != null) {
                adapter = new ImageAdapter(getApplicationContext(), imageList);
                mGridView.setAdapter(adapter);
            }
        }
    }
}
