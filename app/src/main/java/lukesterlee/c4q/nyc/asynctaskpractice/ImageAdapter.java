package lukesterlee.c4q.nyc.asynctaskpractice;

import android.content.Context;
import android.view.LayoutInflater;
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

    private LayoutInflater layoutInflater;
    private List<String> urls;
    private Context context;

    public ImageAdapter(Context context, List<String> imageList) {

        this.layoutInflater = LayoutInflater.from(context);
        this.urls = imageList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    //position is each row's position. convertView is that row's view, partent is the whole listView.
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String url = (String) getItem(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.adapter_image_row, parent, false);

            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load(url).into(holder.image);

        return convertView;
    }

    public static class ViewHolder {
        public ImageView image;
    }
}