package com.rental.model.booking.vehicle;

import com.rental.constants.VehicleType;

import java.util.HashMap;
import java.util.Map;

public class VehicleFactory {
    public static Map<VehicleType, VehicleCreator> vehicleTypeToVehicle;

    public VehicleFactory() {
        vehicleTypeToVehicle = new HashMap<>();
        vehicleTypeToVehicle.put(VehicleType.CAR, this::createCar);
        vehicleTypeToVehicle.put(VehicleType.VAN, this::createVan);
        vehicleTypeToVehicle.put(VehicleType.BIKE, this::createBike);
        vehicleTypeToVehicle.put(VehicleType.BUS, this::createBus);
    }

    public Vehicle createVehicle(VehicleType vehicleType, String vehicleId, Float price) {
        return vehicleTypeToVehicle.get(vehicleType).createVehicle(vehicleId, price);
    }

    @FunctionalInterface
    private interface VehicleCreator {
        Vehicle createVehicle(String vehicleId, Float price);
    }

    private Vehicle createCar(String vehicleId, Float price) {
         return new Car(vehicleId, price);
    }

    private Vehicle createBike(String vehicleId, Float price) {
        return new Bike(vehicleId, price);
    }

    private Vehicle createVan(String vehicleId, Float price) {
        return new Van(vehicleId, price);
    }

    private Vehicle createBus(String vehicleId, Float price) {
        return new Bus(vehicleId, price);
    }
}
