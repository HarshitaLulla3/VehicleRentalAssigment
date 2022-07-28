package com.rental.service.commandExecutor;

import com.rental.constants.VehicleType;
import com.rental.persistence.PersistenceService;

import java.util.HashSet;
import java.util.Set;


public class AddBranchExecutor implements CommandExecutor {
    @Override
    public void execute(String[] args, PersistenceService persistenceService) {
        String branchId = args[1];
        Set<VehicleType> vehicleTypes = getVehicleTypes(args[2].split(",", 0));

        System.out.println(persistenceService.addBranch(branchId, vehicleTypes));
    }

    private Set<VehicleType> getVehicleTypes(String[] vehicles) {
        Set<VehicleType> vehicleTypes = new HashSet<>();
        for (String vehicle : vehicles) {
            vehicleTypes.add(VehicleType.valueOf(vehicle));
        }
        return vehicleTypes;
    }
}
