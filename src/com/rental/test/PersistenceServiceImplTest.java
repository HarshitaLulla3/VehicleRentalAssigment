package com.rental.test;


import com.rental.constants.VehicleType;
import com.rental.persistence.PersistenceServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.rental.constants.VehicleType.*;
import static org.junit.jupiter.api.Assertions.*;

class PersistenceServiceImplTest {
    PersistenceServiceImpl persistenceService = new PersistenceServiceImpl();
    private static final String branchId1 = "B1";
    private static final String branchId2 = "B2";

    private static final String vehicleID1 = "V1";
    private static final String vehicleID2 = "V2";
    private static final String vehicleID3 = "V3";
    private static final String vehicleID4 = "V4";
    private static final String vehicleID5 = "V5";

    private static final int vehiclePrice1 = 400;
    private static final int vehiclePrice2 = 500;
    private static final int vehiclePrice3 = 600;
    private static final int vehiclePrice4 = 600;
    private static final int vehiclePrice5 = 700;

    private static final int startTime = 2;
    private static final int endTime = 3;
    private static Set<VehicleType> vehicleTypes;

    @BeforeAll
    static void init() {
        vehicleTypes = new HashSet<>();
        vehicleTypes.add(CAR);
        vehicleTypes.add(BIKE);
        vehicleTypes.add(BUS);
    }

    @BeforeEach
    void setUp() {
        persistenceService.addBranch(branchId1, vehicleTypes);
        persistenceService.addVehicle(branchId1, CAR, vehicleID1, vehiclePrice1);
        persistenceService.addVehicle(branchId1, BUS, vehicleID2, vehiclePrice2);
        persistenceService.addVehicle(branchId1, BIKE, vehicleID3, vehiclePrice3);
        persistenceService.addVehicle(branchId1, CAR, vehicleID5, vehiclePrice5);
    }
    //...........Tests for addBranch method...........................................

    @Test
    @DisplayName("Check if we are adding a new branch")
    void addNewBranch() {
        boolean result2 = persistenceService.addBranch(branchId2, vehicleTypes);
        assertTrue(result2);
    }

    @DisplayName("Check if we are adding a existing branch again")
    @Test
    void addExistingBranch() {
        boolean result2 = persistenceService.addBranch(branchId1, vehicleTypes);
        assertFalse(result2);
    }

    //...........Tests for addVehicle method...........................................

    @DisplayName("Add valid type vehicle to a branch")
    @Test
    void addSupportedVehicle() {
        boolean result = persistenceService.addVehicle(branchId1, BUS, vehicleID4, vehiclePrice4);
        assertTrue(result);
    }

    @DisplayName("Check prevention of adding vehicle to non-existent branch")
    @Test
    void addVehicleToInvalidBranch() {
        boolean result = persistenceService.addVehicle(branchId2, CAR, vehicleID1, vehiclePrice1);
        assertFalse(result);
    }

    @DisplayName("Check prevention of duplicate vehicle to existing branch")
    @Test
    void addDuplicateVehicleToBranch() {
        boolean result = persistenceService.addVehicle(branchId1, CAR, vehicleID1, vehiclePrice1);
        assertFalse(result);
    }

    @DisplayName("Add vehicle of invalid type")
    @Test
    void addUnsupportedVehicle() {
        boolean result = persistenceService.addVehicle(branchId1, VAN, vehicleID1, vehiclePrice1);
        assertFalse(result);
    }

    //...........Tests for bookVehicle method...........................................

    @DisplayName("Book vehicle for non-existent branch")
    @Test
    void bookVehicleForInvalidBranch() {
        float result = persistenceService.bookVehicle(branchId2, BIKE, startTime, endTime);
        assertEquals(-1, result);
    }

    @DisplayName("Book vehicle of unsupported type in branch")
    @Test
    void bookInvalidVehicle() {
        float result = persistenceService.bookVehicle(branchId1, VAN, startTime, endTime);
        assertEquals(-1, result);
    }

    @DisplayName("Book already booked vehicle")
    @Test
    void bookUnavailableVehicle() {
        float result1 = persistenceService.bookVehicle(branchId1, BUS, startTime, endTime);
        float result2 = persistenceService.bookVehicle(branchId1, BUS, startTime, endTime);
        assertEquals(vehiclePrice2,result1);
        assertEquals(-1, result2);
    }

    @DisplayName("Book available vehicle for branch")
    @Test
    void bookAvailableVehicle() {
        float result = persistenceService.bookVehicle(branchId1, CAR, startTime, endTime);
        assertEquals(vehiclePrice1, result);
    }

    //...........Tests for displayVehicle method...........................................


    @DisplayName("Display vehicles present in branch for a given time window in sorted price order")
    @Test
    void displayVehicles() {
        float book = persistenceService.bookVehicle(branchId1, CAR, startTime, endTime);
        String result = persistenceService.displayVehicles(branchId1, startTime, endTime);
        assertEquals(book, vehiclePrice1);
        System.out.println(result);
        String expectedResult = vehicleID2 + " " + vehicleID3 + " " + vehicleID5;
        assertEquals(result, expectedResult );
    }
}