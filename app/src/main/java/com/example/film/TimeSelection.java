package com.example.film;

public class TimeSelection {

    private String time;
    private boolean isSelected;

    public TimeSelection(String time) {
        this.time = time;
        isSelected = false;
    }

    public TimeSelection(String time, boolean isSelected) {
        this.time = time;
        this.isSelected = isSelected;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
