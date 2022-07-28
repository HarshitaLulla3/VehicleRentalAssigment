package com.rental.service.commandExecutor;

import com.rental.persistence.PersistenceService;


public class DisplayVehiclesExecutor implements CommandExecutor {
    @Override
    public void execute(String[] args, PersistenceService persistenceService) {
        String branchId = args[1];
        int startTime = Integer.parseInt(args[2]);
        int endTime = Integer.parseInt(args[3]);
        System.out.println(persistenceService.displayVehicles(branchId,startTime, endTime));
    }
}
