package tuomolaukkanen.example.com.finalproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import tuomolaukkanen.example.com.finalproject.R;
import tuomolaukkanen.example.com.finalproject.models.EventModel;
import tuomolaukkanen.example.com.finalproject.utils.CustomDateTime;
import tuomolaukkanen.example.com.finalproject.utils.VolleySingleton;

public class EventActivity extends AppCompatActivity {

    private static final String TAG = "EventActivity";
    private TextView mTextViewName;
    private TextView mTextViewLocation;
    private TextView mTextViewTime;
    private NetworkImageView mNetworkImageViewImage;
    private TextView mTextViewDescription;
    private TextView mTextViewInfoUrl;
    private TextView mTextViewPrice;
    private EventModel mEventModel;
    private ImageLoader mImageLoader;
    private String mEventTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        setMemberVariables();
        setData();
    }

    private void setMemberVariables() {
        mTextViewName = findViewById(R.id.eventTextView_Name);
        mTextViewLocation = findViewById(R.id.eventTextView_Location);
        mTextViewTime = findViewById(R.id.eventTextView_Time);
        mNetworkImageViewImage = findViewById(R.id.eventNetworkImageView_Image);
        mTextViewDescription = findViewById(R.id.eventTextView_Description);
        mTextViewInfoUrl = findViewById(R.id.eventTextView_InfoUrl);
        mTextViewPrice = findViewById(R.id.eventTextView_Price);
        mEventModel = getIntent().getParcelableExtra("event");
        mImageLoader = VolleySingleton.getInstance(EventActivity.this).getImageLoader();
        mEventTime = CustomDateTime.getDateTime(mEventModel);
    }

    private void setData() {
        mTextViewName.setText(mEventModel.getEventName());
        mTextViewLocation.setText(mEventModel.getFullAddress());
        mTextViewTime.setText(mEventTime);
        mNetworkImageViewImage.setImageUrl(mEventModel.getEventImageUrl(), mImageLoader);
        mNetworkImageViewImage.setDefaultImageResId(R.mipmap.ic_launcher);
        mTextViewDescription.setText(Html.fromHtml(mEventModel.getEventDescription(), Html.FROM_HTML_MODE_LEGACY));
        mTextViewInfoUrl.setText("Tapahtuman verkkosivut: " + mEventModel.getEventInfoUrl());
        mTextViewPrice.setText("Tapahtuman hinta: " + mEventModel.getEventPrice());
    }

}
