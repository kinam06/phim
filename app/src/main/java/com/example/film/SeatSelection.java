package com.example.film;

public class SeatSelection {
    private int seat;
    private boolean isSelected;

    private boolean isBooked;

    public SeatSelection(int seat, boolean isSelected, boolean isBooked) {
        this.seat = seat;
        this.isSelected = isSelected;
        this.isBooked = isBooked;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}
