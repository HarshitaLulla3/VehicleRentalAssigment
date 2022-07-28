package com.rental.service.commandExecutor;

import com.rental.constants.VehicleType;
import com.rental.persistence.PersistenceService;


public class AddVehicleExecutor implements CommandExecutor {

    @Override
    public void execute(String[] args, PersistenceService persistenceService) {
        String branchId = args[1];
        VehicleType vehicleType = VehicleType.valueOf(args[2]);
        String vehicleId = args[3];
        float price = Float.parseFloat(args[4]);

        System.out.println(persistenceService.addVehicle(branchId, vehicleType, vehicleId, price));
    }
}
