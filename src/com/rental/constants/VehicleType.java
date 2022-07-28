package com.rental.constants;


public enum VehicleType {
    CAR, BIKE, VAN, BUS;

    public static VehicleType getVehicleType(String vehicleClass) {
        String[] classPath = vehicleClass.split("\\.", 0);
        return VehicleType.valueOf(classPath[classPath.length -1].toUpperCase());
    }
}

