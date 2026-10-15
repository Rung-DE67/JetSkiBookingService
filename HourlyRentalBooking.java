public class HourlyRentalBooking extends JetSkiBooking {
    private int rentalMinutes;

    public static final int BLOCK_MINUTES = 30;
    public static final double RATE_ONE_PASSENGER = 1800;
    public static final double RATE_TWO_PASSENGERS = 2000;

    public HourlyRentalBooking(int bookingId, String customerName, int passengerCount, int rentalMinutes) {
        super(bookingId, customerName, passengerCount);
        setRentalMinutes(rentalMinutes);
    }

    public int getBillableBlocks() {
        return ((rentalMinutes + BLOCK_MINUTES - 1) / BLOCK_MINUTES);
    }

    @Override
    public double calculateTotalPrice() {
        int blocks = getBillableBlocks();
        double ratePerBlock = (getPassengerCount() == 1) ? RATE_ONE_PASSENGER : RATE_TWO_PASSENGERS;
        return blocks * ratePerBlock;
    }

    @Override
    public String getBookingDetails() {
        return String.format("[%s] Hourly Rental - %s | %d mins (%d blocks) | %d Pax | Total: %.2f THB",
                getBookingCode(), getCustomerName(), rentalMinutes, getBillableBlocks(), getPassengerCount(),
                calculateTotalPrice());
    }

    public int getRentalMinutes() {
        return rentalMinutes;
    }

    public void setRentalMinutes(int rentalMinutes) {
        if (rentalMinutes < 1 || rentalMinutes > 480) {
            throw new IllegalArgumentException("Rental minutes must be between 1 and 480.");
        } else {
            this.rentalMinutes = rentalMinutes;
        }
    }

}