package com.example.chronosapp.ui.itemList;

public class Recurrence {
    private String dayOfTheWeek;
    private boolean isSet;

    public Recurrence(String dayOfTheWeek, boolean isSet)
    {
        this.dayOfTheWeek = dayOfTheWeek;
        this.isSet = isSet;
    }

    String getDayOfTheWeek() {return dayOfTheWeek;}
    boolean isSet() {return isSet;}
    void set(boolean setValue) {isSet = setValue;}
}
