package tuomolaukkanen.example.com.finalproject.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tuomolaukkanen.example.com.finalproject.models.EventModel;

public class CustomDateTime {

    private static final String TAG = "CustomDateTime";

    public static String getDateTime(EventModel mEventModel) {
        DateFormat parserFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String startTime = mEventModel.getEventStart();
        String endTime = mEventModel.getEventEnd();
        String eventTime = "";
        Date date;

        try {
            date = parserFormat.parse(startTime);
            eventTime = dateFormat.format(date);
        } catch (ParseException e) {
            Log.d(TAG, "getDateTime, startTime error: " + e.getMessage());
        }
        if (endTime != null) {
            try {
                date = parserFormat.parse(endTime);
                eventTime += (" - " + dateFormat.format(date));
            } catch (ParseException e) {
                Log.d(TAG, "getDateTime, endTime error: " + e.getMessage());
            }
        }

        return eventTime;
    }

}
