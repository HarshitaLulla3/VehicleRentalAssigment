package com.rental;

import com.rental.service.VehicleRentalService;
import com.rental.persistence.PersistenceService;
import com.rental.persistence.PersistenceServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class VehicleRentalDriver {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Please specify input file path as argument");
            System.exit(1);
        }
        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));

        PersistenceService persistenceService = new PersistenceServiceImpl();
        String command;

        while ((command = br.readLine()) != null){
            System.out.println(command);
            VehicleRentalService.executeCommand(command, persistenceService);
        }
    }
}
