package com.rental.service;

import com.rental.constants.CommandType;
import com.rental.service.commandExecutor.*;
import com.rental.persistence.PersistenceService;

import java.util.HashMap;
import java.util.Map;


public class VehicleRentalService {

    public static Map<CommandType, CommandExecutor> commandExecutorMap;

    static {
        commandExecutorMap = new HashMap<>();
        commandExecutorMap.put(CommandType.ADD_VEHICLE, new AddVehicleExecutor());
        commandExecutorMap.put(CommandType.ADD_BRANCH, new AddBranchExecutor());
        commandExecutorMap.put(CommandType.BOOK, new BookVehicleExecutor());
        commandExecutorMap.put(CommandType.DISPLAY_VEHICLES, new DisplayVehiclesExecutor());
    }


    public static void executeCommand(String input, PersistenceService persistenceService) {
        String[] args = input.split(" ", 0);
        String command = args[0];
        CommandType commandType = CommandType.valueOf(command);
        CommandExecutor commandExecutor = commandExecutorMap.get(commandType);
        commandExecutor.execute(args, persistenceService);
    }
}
