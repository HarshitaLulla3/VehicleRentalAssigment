package com.rental.persistence;

import com.rental.constants.VehicleType;
import com.rental.model.booking.vehicle.Vehicle;
import com.rental.model.booking.vehicle.VehicleFactory;
import com.rental.model.branch.VehicleRentalBranch;

import java.util.*;
import java.util.stream.Collectors;


public class PersistenceServiceImpl implements PersistenceService {

    private final Map<String, VehicleRentalBranch> branchIdToBranch;
    private final VehicleFactory vehicleFactory;
    private static final float SURGE_BOOKING_PERCENTAGE = 80f;
    private static final float SURGE_PRICE_RATE = 0.1f;

    public PersistenceServiceImpl() {
        branchIdToBranch = new HashMap<>();
        vehicleFactory = new VehicleFactory();
    }

    @Override
    public boolean addBranch(String branchId, Set<VehicleType> vehicleTypes) {
        // Branch was already onboarded in the system.
        if (branchIdToBranch.containsKey(branchId)) {
            return false;
        }
        VehicleRentalBranch branch = new VehicleRentalBranch(branchId,vehicleTypes);
        branchIdToBranch.put(branchId, branch);
        return true;
    }

    @Override
    public boolean addVehicle(String branchId, VehicleType vehicleType, String vehicleId, float price) {
        // Branch doesn't exist.
        if (!isValidBranch(branchId))
            return false;

        VehicleRentalBranch branch = branchIdToBranch.get(branchId);
        //Branch doesn't contain vehicle type.
        if (!branch.getVehicleTypes().contains(vehicleType)) {
            System.out.println("Branch " + branchId + " doesn't support vehicle type " + vehicleType.name());
            return false;
        }

        TreeSet<Vehicle> vehicles = branch.getVehicles();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)){
                System.out.println("Vehicle " + vehicleId + " already exists in branch " + branchId);
                return false;
            }
        }

        Vehicle vehicle = vehicleFactory.createVehicle(vehicleType, vehicleId, price);
        vehicles.add(vehicle);

        Map<VehicleType, Integer> vehicleTypeCounter  = branch.getVehicleTypeCounter();
        int totalVehicles = vehicleTypeCounter.getOrDefault(vehicleType, 0) + 1;
        vehicleTypeCounter.put(vehicleType, totalVehicles);
        return true;
    }

    private boolean isValidBranch(String branchId) {
        // Branch doesn't exist.
        if (!branchIdToBranch.containsKey(branchId)) {
            System.out.println("Branch " + branchId + " was not onboarded ");
            return false;
        }
        return true;
    }

    @Override
    public float bookVehicle(String branchId, VehicleType vehicleType, int startTime, int endTime) {
        // Branch doesn't exist.
        if (!isValidBranch(branchId))
            return -1;

        VehicleRentalBranch branch = branchIdToBranch.get(branchId);
        //Branch doesn't contain vehicle type.
        if (!branch.getVehicleTypes().contains(vehicleType)) {
            System.out.println("Branch " + branchId + " doesn't support vehicle type " + vehicleType.name());
            return -1;
        }

        TreeSet<Vehicle> vehicles = branch.getVehicles();

        List<Vehicle> availableVehicles = getAvailableVehicles(vehicleType, startTime, endTime, vehicles);

        Optional<Vehicle> vehicle = availableVehicles.stream().findFirst();
        // Branch doesn't have vehicle for given time range.
        if (!vehicle.isPresent())
            return -1;

        Map<VehicleType, Integer> vehicleTypeCounter  = branch.getVehicleTypeCounter();
        int totalVehicles = vehicleTypeCounter.getOrDefault(vehicleType, 0);

        bookVehicle(vehicle.get(), startTime, endTime);
        float fixedVehiclePrice = vehicle.get().getPrice() * (endTime - startTime);

        return shouldAddSurge(availableVehicles.size(),  totalVehicles)
                ? (1 + SURGE_PRICE_RATE) * fixedVehiclePrice : fixedVehiclePrice;
    }

    private boolean shouldAddSurge(int totalAvailableVehicles, int totalVehicles) {
        int totalBookedVehicles = totalVehicles - totalAvailableVehicles;
        if (totalAvailableVehicles == 0 && totalVehicles == 0)
            return false;
        float bookedPercentage = ((float) totalBookedVehicles/totalVehicles) * 100;
        return bookedPercentage >= SURGE_BOOKING_PERCENTAGE;
    }

    private void bookVehicle(Vehicle vehicle, int startTime, int endTime) {
        boolean[] availableSlots = vehicle.getAvailableSlots();

        for (int hour = startTime; hour <= endTime; hour++) {
            availableSlots[hour] = false;
        }
    }

    private List<Vehicle> getAvailableVehicles(VehicleType vehicleType, int startTime, int endTime, TreeSet<Vehicle> vehicles) {
        return vehicles.stream().
                filter(vehicle -> isVehicleAvailable(vehicle, vehicleType, startTime, endTime))
                .collect(Collectors.toList());
    }

    private boolean isVehicleAvailable(Vehicle vehicle, VehicleType vehicleType, int startTime, int endTime) {
        return VehicleType.getVehicleType(vehicle.getClass().getName()).equals(vehicleType)
                && vehicle.isAvailable(startTime, endTime);
    }

    @Override
    public String displayVehicles(String branchId, int startTime, int endTime) {
        // Branch doesn't exist.
        if (!isValidBranch(branchId))
            return "";
        VehicleRentalBranch branch = branchIdToBranch.get(branchId);
        TreeSet<Vehicle> vehicles = branch.getVehicles();
        return String.join(" ", getVehicleIds(vehicles, startTime, endTime));
    }

    private List<String> getVehicleIds(TreeSet<Vehicle> vehicles, int startTime, int endTime){
        return vehicles.stream()
                .filter(vehicle -> vehicle.isAvailable(startTime, endTime))
                .map(Vehicle::getId)
                .collect(Collectors.toList());
        }
}
