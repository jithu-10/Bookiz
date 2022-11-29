package hotelbooking;

import java.util.ArrayList;

public class BookingDB {
    private static final BookingDB BOOKING_DB =new BookingDB();
    private int idHelper=99999;
    private final ArrayList<Booking> bookings=new ArrayList<>();

    private BookingDB(){

    }
    public static BookingDB getInstance(){
        return BOOKING_DB;
    }
    int generateId(){
        return ++idHelper;
    }


    public void addBooking(CustomerBooking customerBooking){
        Helper.validateNull(customerBooking,"Booking can't be null");
        customerBooking.getHotel().addRoomBooking(customerBooking.getCheckInDate(),customerBooking.getCheckOutDate(),customerBooking.getBookedRoomIDs());
        customerBooking.getCustomer().addBookings(customerBooking);
        bookings.add(customerBooking);


    }


    public ArrayList<Booking> getBookings(){
        return bookings;
    }


    public void removeBooking(CustomerBooking customerBooking,Customer customer){
        Helper.validateNull(customerBooking,"Booking can't be null");
        Helper.validateNull(customer,"Customer can't be null");
        bookings.remove(customerBooking);
        customer.removeBookings(customerBooking);
        customer.addCancelledBookings(customerBooking);
        Hotel hotel=customerBooking.getHotel();
        hotel.cancelRoomBooking(customerBooking.getCheckInDate(),customerBooking.getCheckOutDate(),customerBooking.getBookedRoomIDs());
    }

    public ArrayList<CustomerBooking> getHotelBookings(int hotelID){
        ArrayList<CustomerBooking> hotelBookings=new ArrayList<>();
        for(Booking booking: bookings){
            if(booking.getHotel().getHotelID()==hotelID){
                hotelBookings.add((CustomerBooking) booking);
            }
        }
        return hotelBookings;
    }


}
