public abstract class JetSkiBooking implements Bookable {
    private int bookingId;
    private String customerName;
    private int passengerCount;

    public JetSkiBooking(int bookingId, String customerName, int passengerCount) {
        setBookingId(bookingId);
        setCustomerName(customerName);
        setPassengerCount(passengerCount);
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        if (passengerCount != 1 && passengerCount != 2) {
            throw new IllegalArgumentException("Passenger count must be 1 or 2!!!");
        } else {
            this.passengerCount = passengerCount;
        }
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        } else {
            this.customerName = customerName;
        }
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Booking ID must be positive.");
        } else {
            this.bookingId = bookingId;
        }
    }

    @Override
    public final String getBookingCode() {
        return "BK-" + bookingId;
    }

    @Override
    public abstract double calculateTotalPrice();

    @Override
    public abstract String getBookingDetails();

}

