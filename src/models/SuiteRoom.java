package models;

import java.time.LocalDate;

// INHERITANCE - SuiteRoom IS-A Room
public class SuiteRoom extends Room {
    
    public SuiteRoom(int roomNumber) {
        super(roomNumber, "Suite", 300.0);
    }
    
    // POLYMORPHISM - Overriding parent method
    @Override
    public double calculatePrice(LocalDate startDate, LocalDate endDate) {
        return pricingCalculator.calculateTotalPrice(baseRate, startDate, endDate);
    }
}