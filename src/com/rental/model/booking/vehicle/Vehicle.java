package com.rental.model.booking.vehicle;

import com.rental.model.booking.BookingItem;

public abstract class Vehicle extends BookingItem {
    public Vehicle(String id, Float price) {
        super(id, price);
    }
}
