package tuomolaukkanen.example.com.finalproject.models;

import android.os.Parcel;
import android.os.Parcelable;

public class EventModel implements Parcelable{

    private String eventName;
    private String eventLocationName;
    private String eventLocationAddress;
    private String eventLocationCity;
    private String eventStart;
    private String eventEnd;
    private String eventImageUrl;
    private String eventDescription;
    private String eventInfoUrl;
    private String eventPrice;

    public EventModel() {

    }

    public EventModel(String eventName, String eventLocationName, String eventLocationAddress,
                      String eventLocationCity, String eventStart, String eventEnd, String eventImageUrl,
                      String eventDescription, String eventInfoUrl, String eventPrice) {
        this.eventName = eventName;
        this.eventLocationName = eventLocationName;
        this.eventLocationAddress = eventLocationAddress;
        this.eventLocationCity = eventLocationCity;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.eventImageUrl = eventImageUrl;
        this.eventDescription = eventDescription;
        this.eventInfoUrl = eventInfoUrl;
        this.eventPrice = eventPrice;
    }

    public String getFullAddress() {
        return this.eventLocationName + ", " + this.eventLocationAddress + ", " + this.eventLocationCity;
    }

    protected EventModel(Parcel in) {
        eventName = in.readString();
        eventLocationName = in.readString();
        eventLocationAddress = in.readString();
        eventLocationCity = in.readString();
        eventStart = in.readString();
        eventEnd = in.readString();
        eventImageUrl = in.readString();
        eventDescription = in.readString();
        eventInfoUrl = in.readString();
        eventPrice = in.readString();
    }

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel in) {
            return new EventModel(in);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public String getEventLocationAddress() {
        return eventLocationAddress;
    }

    public void setEventLocationAddress(String eventLocationAddress) {
        this.eventLocationAddress = eventLocationAddress;
    }

    public String getEventLocationCity() {
        return eventLocationCity;
    }

    public void setEventLocationCity(String eventLocationCity) {
        this.eventLocationCity = eventLocationCity;
    }

    public String getEventStart() {
        return eventStart;
    }

    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }

    public String getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventInfoUrl() {
        return eventInfoUrl;
    }

    public void setEventInfoUrl(String eventInfoUrl) {
        this.eventInfoUrl = eventInfoUrl;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(eventLocationName);
        dest.writeString(eventLocationAddress);
        dest.writeString(eventLocationCity);
        dest.writeString(eventStart);
        dest.writeString(eventEnd);
        dest.writeString(eventImageUrl);
        dest.writeString(eventDescription);
        dest.writeString(eventInfoUrl);
        dest.writeString(eventPrice);
    }
}
