public interface Bookable {
    String getBookingCode();
    String getCustomerName();
    int getPassengerCount();
    double calculateTotalPrice();
    String getBookingDetails();

}
