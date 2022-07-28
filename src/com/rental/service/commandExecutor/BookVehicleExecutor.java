package com.rental.service.commandExecutor;

import com.rental.constants.VehicleType;
import com.rental.persistence.PersistenceService;


public class BookVehicleExecutor implements CommandExecutor {
    @Override
    public void execute(String[] args, PersistenceService persistenceService) {
        String branchId = args[1];
        VehicleType vehicleType = VehicleType.valueOf(args[2]);
        int startTime = Integer.parseInt(args[3]);
        int endTime = Integer.parseInt(args[4]);

        float bookingPrice = persistenceService.bookVehicle(branchId, vehicleType, startTime, endTime);
        System.out.println(bookingPrice < 0 ? "-1" : bookingPrice);
    }
}
