package services;

import java.time.LocalDate;

// INTERFACE - models "CAN-DO" relationship
public interface SeasonalPricing {
    double getMultiplier(LocalDate date);
}