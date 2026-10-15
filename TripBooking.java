public class TripBooking extends JetSkiBooking {
    private int tripDurationHours;
    private String islandRoute;

    public static final double SECOND_PASSENGER_SURCHARGE = 1200;

    public TripBooking(int bookingId, String customerName, int passengerCount, int tripDurationHours,
            String islandRoute) {
        super(bookingId, customerName, passengerCount);
        setTripDurationHours(tripDurationHours);
        setIslandRoute(islandRoute);
    }

    public String getTourItinerary() {
        if (tripDurationHours == 4) {
            return "Visits 3 islands.";
        } else if (tripDurationHours == 6) {
            return "Visits 5 islands.";
        } else {
            return "Visits 6 island including Sunset View";
        }
    }

    @Override
    public double calculateTotalPrice() {
        double basePrice;
        if (tripDurationHours == 4) {
            basePrice = 5000;
        } else if (tripDurationHours == 6) {
            basePrice = 7000;
        } else {
            basePrice = 9000;
        }
        if (getPassengerCount() == 2) {
            basePrice += SECOND_PASSENGER_SURCHARGE;
        }
        return basePrice;
    }

    @Override
    public String getBookingDetails() {
        return String.format("[%s] Island Trip - %s | %d hrs (%s) | %d Pax | %s | Total: %,.2f THB", getBookingCode(),
                getCustomerName(), tripDurationHours, islandRoute, getPassengerCount(), getTourItinerary(),
                calculateTotalPrice());
    }

    public int getTripDurationHours() {
        return tripDurationHours;
    }

    public void setTripDurationHours(int tripDurationHours) {
        if (tripDurationHours != 4 && tripDurationHours != 6 && tripDurationHours != 8) {
            throw new IllegalArgumentException("Trip duration must be 4, 6, or 8 hours.");
        } else {
            this.tripDurationHours = tripDurationHours;
        }

    }

    public String getIslandRoute() {
        return islandRoute;
    }

    public void setIslandRoute(String islandRoute) {
        if (islandRoute == null || islandRoute.trim().isEmpty()) {
            throw new IllegalArgumentException("Island route cannot be empty!!");
        } else {
            this.islandRoute = islandRoute;
        }
    }
}
