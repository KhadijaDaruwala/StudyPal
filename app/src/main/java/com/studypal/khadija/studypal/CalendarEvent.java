package com.studypal.khadija.studypal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CalendarEvent implements Parcelable {
    private Long id;
    private Date date;
    private String title;
    private String description;
    private boolean allDay;

    public CalendarEvent(Long id, Date date, String title, String description, boolean allDay) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.allDay = allDay;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean getAllDay() {
        return allDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalendarEvent event = (CalendarEvent) o;

        if (allDay != event.allDay) return false;
        if (date != null ? !date.equals(event.date) : event.date != null) return false;
        if (description != null ? !description.equals(event.description) : event.description != null)
            return false;
        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (title != null ? !title.equals(event.title) : event.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (allDay ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeLong(date != null ? date.getTime() : -1);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeByte(allDay ? (byte) 1 : (byte) 0);
    }

    private CalendarEvent(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.title = in.readString();
        this.description = in.readString();
        this.allDay = in.readByte() != 0;
    }

    public static final Creator<CalendarEvent> CREATOR = new Creator<CalendarEvent>() {
        public CalendarEvent createFromParcel(Parcel source) {
            return new CalendarEvent(source);
        }

        public CalendarEvent[] newArray(int size) {
            return new CalendarEvent[size];
        }
    };
}
