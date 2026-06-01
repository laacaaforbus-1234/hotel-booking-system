package models;

import java.time.LocalDate;
import services.PricingCalculator;

public abstract class Room {
    protected int roomNumber;
    protected String roomType;
    protected double baseRate;
    protected boolean isAvailable;
    protected PricingCalculator pricingCalculator;
    
    public Room(int roomNumber, String roomType, double baseRate) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.baseRate = baseRate;
        this.isAvailable = true;
        this.pricingCalculator = new PricingCalculator();
    }
    
    public abstract double calculatePrice(LocalDate startDate, LocalDate endDate);
    
    // Get daily breakdown for display
    public String getPriceBreakdown(LocalDate startDate, LocalDate endDate) {
        return pricingCalculator.getDailyBreakdown(baseRate, startDate, endDate);
    }
    
    // Getters and Setters
    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public double getBaseRate() { return baseRate; }
    public boolean isAvailable() { return isAvailable; }
    public PricingCalculator getPricingCalculator() { return pricingCalculator; }
    
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public void setBaseRate(double baseRate) { this.baseRate = baseRate; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setPricingCalculator(PricingCalculator pricingCalculator) { 
        this.pricingCalculator = pricingCalculator; 
    }
    
    @Override
    public String toString() {
        return roomType + " Room #" + roomNumber + " - $" + baseRate + "/night";
    }
}