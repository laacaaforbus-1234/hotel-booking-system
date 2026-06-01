package services;

import java.time.LocalDate;

public class PricingCalculator implements SeasonalPricing {
    
    @Override
    public double getMultiplier(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        
        // ===== OUR SEASONAL RULES =====
        
        // RULE 1: January to May - Normal price (no change)
        if (month >= 1 && month <= 6) {
            return 1.0;  // Normal price
        }
        
        // RULE 2: June 1 to December 31 - 25% discount
        if (month > 8 && month < 12){ 
                return 0.75;  // 25% discount
        }
        if (month==12 && day<20){
            return 0.75;
        }
        // ===== EXTRA SEASONS WE CAN ADD =====
        
        // Increased price season (Dec 20-31) - Extra charge during these times
        // This OVERRIDES the 25% discount for Christmas week
        if (month == 12 && day >= 20) {
            return 1.5;  // 50% more expensive during these seasons
        }
        
        // Summer peak (June 1 - August 31) - Already covered by 25% discount
        
        if (month >= 7 && month <= 8) {
            return 0.85;  // 15% discount instead of 25%
         }
        
        return 1.0;
    }
    
    // Get season name for display
    public String getSeasonName(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        
        if (month == 12 && day >= 20) {
            return "Increased price (50% up)";
        } else if (month >= 7 && month <= 8) {
            return "Discount Season (15% off)";
        } else if (month >8 && month <=12){
            return "Discount season 25% off";
        } else {
            return "Regular Season";
        }
    }
    
    // Calculate total price for a stay crossing multiple seasons
    public double calculateTotalPrice(double baseRate, LocalDate startDate, LocalDate endDate) {
        double total = 0;
        LocalDate currentDate = startDate;
        
        while (currentDate.isBefore(endDate)) {
            double multiplier = getMultiplier(currentDate);
            double dailyPrice = baseRate * multiplier;
            total += dailyPrice;
            currentDate = currentDate.plusDays(1);
        }
        
        return total;
    }
    
    // Get daily breakdown as a formatted string
    public String getDailyBreakdown(double baseRate, LocalDate startDate, LocalDate endDate) {
        StringBuilder breakdown = new StringBuilder();
        LocalDate currentDate = startDate;
        int dayCount = 1;
        
        while (currentDate.isBefore(endDate)) {
            double multiplier = getMultiplier(currentDate);
            double dailyPrice = baseRate * multiplier;
            String seasonName = getSeasonName(currentDate);
            double discountPercent = (1 - multiplier) * 100;
            
            if (multiplier < 1) {
                breakdown.append(String.format("Day %d (%s): $%.2f (%.0f%% discount)\n", 
                    dayCount, currentDate, dailyPrice, discountPercent));
            } else if (multiplier > 1) {
                breakdown.append(String.format("Day %d (%s): $%.2f (%.0f%% surcharge)\n", 
                    dayCount, currentDate, dailyPrice, (multiplier - 1) * 100));
            } else {
                breakdown.append(String.format("Day %d (%s): $%.2f (Regular price)\n", 
                    dayCount, currentDate, dailyPrice));
            }
            
            currentDate = currentDate.plusDays(1);
            dayCount++;
        }
        
        return breakdown.toString();
    }
}