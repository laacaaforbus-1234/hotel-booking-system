package models;

import java.time.LocalDate;
import java.util.UUID;

public class Booking {
    private String bookingId;
    private User customer;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private String status; // "ACTIVE" or "CANCELLED"
    
    public Booking(User customer, Room room, LocalDate startDate, LocalDate endDate) {
        this.bookingId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = room.calculatePrice(startDate, endDate);
        this.status = "ACTIVE";
    }
    
    // Getters
    public String getBookingId() { return bookingId; }
    public User getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    
    // Setters
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public void setCustomer(User customer) { this.customer = customer; }
    public void setRoom(Room room) { this.room = room; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "Booking #" + bookingId + " | " + customer.getName() + " | " + 
               room.getRoomType() + " #" + room.getRoomNumber() + " | $" + totalPrice + 
               " | " + status;
    }
}