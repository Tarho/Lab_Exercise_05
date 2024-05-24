package com.example.clockapplication;

public class AlarmItem {
    private String time;
    private String days;
    private String label;

    public AlarmItem(String time, String days, String label) {
        this.time = time;
        this.days = days;
        this.label = label;
    }

    public String getTime() {
        return time;
    }

    public String getDays() {
        return days;
    }

    public String getLabel() {
        return label;
    }
}
