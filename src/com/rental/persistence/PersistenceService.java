package com.rental.persistence;

import com.rental.constants.VehicleType;

import java.util.Set;


public interface PersistenceService {
    boolean addBranch(String branchId, Set<VehicleType> vehicleTypes);
    boolean addVehicle(String branchId, VehicleType vehicleType, String vehicleId, float price);
    float bookVehicle(String branchId, VehicleType vehicleType, int startTime, int endTime);
    String displayVehicles(String branchId, int startTime, int endTime);
}
