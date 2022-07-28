package com.rental.model.branch;

import com.rental.constants.VehicleType;
import com.rental.model.booking.vehicle.Vehicle;

import java.util.*;


public class VehicleRentalBranch extends Branch {
    private final TreeSet<Vehicle> vehicles; // sorted based on vehicle price
    private final Set<VehicleType> vehicleTypes;

    public Map<VehicleType, Integer> getVehicleTypeCounter() {
        return vehicleTypeCounter;
    }

    private final Map<VehicleType, Integer> vehicleTypeCounter;

    // for sorting in ascending order
    static class VehicleComparator implements Comparator<Vehicle> {
        @Override public int compare(Vehicle v1, Vehicle v2) {
            if (v1.getPrice().equals(v2.getPrice())) {
                return v1.getId().compareTo(v2.getId());
            }
            return v1.getPrice().compareTo(v2.getPrice());
        }
    }

    public VehicleRentalBranch(String branchId, Set<VehicleType> vehicleTypes) {
        this.id = branchId;
        this.vehicleTypes = vehicleTypes;
        this.vehicles = new TreeSet<>(new VehicleComparator());
        this.vehicleTypeCounter = new HashMap<>();
    }

    public TreeSet<Vehicle> getVehicles() {
        return vehicles;
    }

    public Set<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }
}

