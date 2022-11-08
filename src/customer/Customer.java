package customer;

import booking.CustomerBooking;
import user.User;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Customer extends User {

    private final LinkedHashSet<Integer> favoriteHotelList =new LinkedHashSet<>();
    private final ArrayList<CustomerBooking> bookings = new ArrayList<>();
    private final ArrayList<CustomerBooking> cancelledBookings=new ArrayList<>();

    public Customer(String userName){
        super(userName);
    }

    public void addFavoriteHotels(int hotelID){
        favoriteHotelList.add(hotelID);
    }

    public void removeFavoriteHotels(int hotelID){
        favoriteHotelList.remove(hotelID);
    }

    public void addBookings(CustomerBooking booking){
        bookings.add(booking);
    }

    public void removeBookings(CustomerBooking booking){
        bookings.remove(booking);
    }

    public void addCancelledBookings(CustomerBooking booking){
        cancelledBookings.add(booking);
    }

    public ArrayList<CustomerBooking> getBookings() {
        return bookings;
    }

    public ArrayList<CustomerBooking> getCancelledBookings() {
        return cancelledBookings;
    }

    public ArrayList<Integer> getFavoriteHotelList(){
        return new ArrayList<>(favoriteHotelList);
    }
}
