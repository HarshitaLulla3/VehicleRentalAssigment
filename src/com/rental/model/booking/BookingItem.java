package com.rental.model.booking;

import java.util.Arrays;


public abstract class BookingItem {
    private final String id;
    private Float price;
    private final boolean[] availableSlots;

    public BookingItem(String id, Float price) {
        this.id = id;
        this.price = price;
        this.availableSlots = new boolean[24];
        Arrays.fill(availableSlots, true);
    }

    public String getId() {
        return id;
    }

    public boolean[] getAvailableSlots() {
        return availableSlots;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvailable (int startTime, int endTime) {
        boolean[] availableSlots = this.getAvailableSlots();
        for (int hour=startTime; hour<=endTime; hour++) {
            if (!availableSlots[hour]){
                return false;
            }
        }
        return true;
    }
}
