package lukesterlee.c4q.nyc.asynctaskpractice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Luke Lee on 6/9/15.
 */

// TODO : Step 4 - implement ImageAdapter.
// The reason we need to pass Context : http://stackoverflow.com/questions/12137680/getting-the-android-context-in-an-adapter

public class ImageAdapter extends BaseAdapter {

    List<String> imageList;

    private Context mContext;

    public ImageAdapter(Context c, List<String> imageList) {
        mContext = c;
        this.imageList = imageList;
    }

    public int getCount() {
        return imageList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

        } else {
            imageView = (ImageView) convertView;
        }


        Picasso.with(mContext).load(imageList.get(position)).resize(300,300).centerCrop().into(imageView);

        return imageView;
    }



}