package com.studypal.khadija.studypal.Calendar;

public class GridItem {

    private final Integer day;
    private final Boolean isToday;
    private final Boolean hasEvents;

    public GridItem(Integer day, Boolean isToday, Boolean hasEvents) {

        this.day = day;
        this.isToday = isToday;
        this.hasEvents = hasEvents;
    }

    public Integer getDay() {
        return day;
    }

    public Boolean getIsToday() {
        return isToday;
    }

    public Boolean getHasEvents() {
        return hasEvents;
    }
}
