package models;

import java.time.LocalDate;

// INHERITANCE - StandardRoom IS-A Room
public class StandardRoom extends Room {
    
    public StandardRoom(int roomNumber) {
        super(roomNumber, "Standard", 150.0);
    }
    
    // POLYMORPHISM - Overriding parent method
    @Override
    public double calculatePrice(LocalDate startDate, LocalDate endDate) {
        return pricingCalculator.calculateTotalPrice(baseRate, startDate, endDate);
    }
}