package tuomolaukkanen.example.com.finalproject.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tuomolaukkanen.example.com.finalproject.R;
import tuomolaukkanen.example.com.finalproject.adapters.EventListAdapter;
import tuomolaukkanen.example.com.finalproject.models.EventModel;
import tuomolaukkanen.example.com.finalproject.utils.VolleySingleton;

import static tuomolaukkanen.example.com.finalproject.models.EventModel.*;

public class EventListActivity extends AppCompatActivity {

    private static final String TAG = "EventListActivity";
    private static final String BASEURL = "https://api.hel.fi/linkedevents/v1/";
    private ArrayList<EventModel> mArrayList;
    private ListView mListView;
    private EventListAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private String mStartDate;
    private String mEndDate;
    private String mKeyword;
    private String mSearchUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        setMemberVariables();
        setListeners();
        createSearchUrl();
        eventVolleyRequest();
    }

    private void setMemberVariables() {
        mArrayList = new ArrayList();
        mListView = findViewById(R.id.eventlistListView);
        mAdapter = new EventListAdapter(EventListActivity.this, R.layout.event_list_row, mArrayList);
        mListView.setAdapter(mAdapter);

        mProgressDialog = new ProgressDialog(EventListActivity.this);
        mProgressDialog.setMessage("Haetaan...");
        mProgressDialog.show();

        Bundle bundle = getIntent().getExtras();
        mStartDate = bundle.getString("startDate");
        mEndDate = bundle.getString("endDate");
        mKeyword = bundle.getString("keyword");
    }

    private void setListeners() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EventListActivity.this, EventActivity.class);
                intent.putExtra("event", mArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    private void createSearchUrl() {
        mSearchUrl = "search/?type=event&input=" + mKeyword;
        if (mStartDate != null) {
            mSearchUrl += ("&start=" + mStartDate);
        }
        if (mEndDate != null) {
            mSearchUrl += ("&end=" + mEndDate);
        }
        mSearchUrl = BASEURL + mSearchUrl;
        Log.d(TAG, "Requesting URL: " + mSearchUrl);
    }

    private void eventVolleyRequest() {
        JsonObjectRequest request = new JsonObjectRequest(mSearchUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String eventName;
                String eventLocationId;
                String eventStart;
                String eventEnd;
                String eventImageUrl;
                String eventDescription;
                String eventInfoUrl;
                String eventPrice;

                try {
                    JSONArray data = response.getJSONArray("data");
                    if (data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject event = data.getJSONObject(i);
                            String locationUrl = event.getJSONObject("location").getString("@id");
                            eventLocationId = Uri.parse(locationUrl).getLastPathSegment();
                            eventName = event.getJSONObject("name").getString("fi");
                            eventStart = event.getString("start_time");
                            eventEnd = event.getString("end_time");
                            eventImageUrl = event.getJSONArray("images").getJSONObject(0).getString("url");
                            eventDescription = event.getJSONObject("description").getString("fi");

                            if (!event.isNull("info_url")) {
                                eventInfoUrl = event.getJSONObject("info_url").getString("fi");
                            } else {
                                eventInfoUrl = "";
                            }

                            JSONArray offers = event.getJSONArray("offers");
                            if (offers.length() > 0) {
                                JSONObject offer = event.getJSONArray("offers").getJSONObject(0);
                                if (!offer.isNull("price")) {
                                    eventPrice = offer.getJSONObject("price").getString("fi");
                                } else {
                                    eventPrice = "Vapaa pääsy";
                                }
                            } else {
                                eventPrice = "Vapaa pääsy";
                            }

                            EventModel eventModel = new EventModel();

                            eventModel.setEventName(eventName);
                            eventModel.setEventStart(eventStart);
                            eventModel.setEventEnd(eventEnd);
                            eventModel.setEventImageUrl(eventImageUrl);
                            eventModel.setEventDescription(eventDescription);
                            eventModel.setEventInfoUrl(eventInfoUrl);
                            eventModel.setEventPrice(eventPrice);

                            locationVolleyRequest(new VolleyCallback() {
                                @Override
                                public void onSuccess(EventModel model) {
                                    mArrayList.add(model);
                                    mProgressDialog.dismiss();
                                    mAdapter.notifyDataSetChanged();
                                }
                            }, eventLocationId, eventModel);
                        }
                    } else {
                        mProgressDialog.dismiss();
                        Toast.makeText(EventListActivity.this, "Tapahtumia ei löytynyt!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "eventVolleyRequest, parsing JSON: " + e.getMessage());
                    e.printStackTrace();
                }
                Log.d(TAG, "eventVolleyRequest, onResponse: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "eventVolleyRequest: onErrorResponse: " + error.toString());
                mProgressDialog.dismiss();
            }
        });
        VolleySingleton.getInstance(EventListActivity.this).addToRequestQueue(request);
    }

    private void locationVolleyRequest(final VolleyCallback callback, String eventLocationId, final EventModel eventModel) {

        String locationUrl = BASEURL + "place/" + eventLocationId;
        JsonObjectRequest request = new JsonObjectRequest(locationUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String name = "";
                String address = "";
                String city = "";

                try {
                    name = response.getJSONObject("name").getString("fi");
                    address = response.getJSONObject("street_address").getString("fi");
                    city = response.getJSONObject("address_locality").getString("fi");
                } catch (JSONException e) {
                    Log.d(TAG, "locationVolleyRequest, parsing JSON: " + e.getMessage());
                    e.printStackTrace();
                }

                eventModel.setEventLocationName(name);
                eventModel.setEventLocationAddress(address);
                eventModel.setEventLocationCity(city);

                callback.onSuccess(eventModel);

                Log.d(TAG, "locationVolleyRequest onResponse: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "locationVolleyRequest: onErrorResponse: " + error.toString());
            }
        });
        VolleySingleton.getInstance(EventListActivity.this).addToRequestQueue(request);
    }

    public interface VolleyCallback {
        void onSuccess(EventModel eventModel);
    }

}