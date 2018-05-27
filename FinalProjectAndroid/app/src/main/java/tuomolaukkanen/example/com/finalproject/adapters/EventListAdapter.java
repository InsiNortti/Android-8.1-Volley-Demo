package tuomolaukkanen.example.com.finalproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.ParseException;
import java.util.ArrayList;

import tuomolaukkanen.example.com.finalproject.R;
import tuomolaukkanen.example.com.finalproject.utils.CustomDateTime;
import tuomolaukkanen.example.com.finalproject.utils.VolleySingleton;
import tuomolaukkanen.example.com.finalproject.models.EventModel;

public class EventListAdapter extends ArrayAdapter {

    private Context mContext;
    private int mResource;
    private ArrayList<EventModel> mList;
    private ImageLoader mImageLoader;
    private String mEventTime;

    private static class ViewHolder {
        TextView Event;
        TextView Location;
        TextView Time;
        NetworkImageView Image;
    }

    public EventListAdapter(Context context, int resource, ArrayList<EventModel> list) {
        super(context, resource, list);
        mContext = context;
        mResource = resource;
        mList = list;
        mImageLoader = VolleySingleton.getInstance(mContext).getImageLoader();
    }

    @Override
    public EventModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.Event = convertView.findViewById(R.id.listrowTextView_Event);
            holder.Location = convertView.findViewById(R.id.listrowTextView_Location);
            holder.Time = convertView.findViewById(R.id.listrowTextView_Time);
            holder.Image = convertView.findViewById(R.id.listrowNetworkImageView_Image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        mEventTime = CustomDateTime.getDateTime(getItem(position));

        holder.Event.setText(getItem(position).getEventName());
        holder.Location.setText(getItem(position).getEventLocationName());
        holder.Time.setText(mEventTime);
        holder.Image.setImageUrl(getItem(position).getEventImageUrl(), mImageLoader);
        holder.Image.setDefaultImageResId(R.mipmap.ic_launcher);

        return convertView;
    }

}
