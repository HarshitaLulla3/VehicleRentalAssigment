package com.rental.service.commandExecutor;

import com.rental.persistence.PersistenceService;


public interface CommandExecutor {
    void execute (String[] args, PersistenceService vehicleRentalService);
}
