package booking;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingDB {
    private static final BookingDB bookingDB=new BookingDB();
    private int idHelper=99999;
    private final ArrayList<Booking> bookings=new ArrayList<>();

    private BookingDB(){

    }
    public static BookingDB getInstance(){
        return bookingDB;
    }
    private int generateId(){
        return ++idHelper;
    }
    public void addBooking(Booking booking){
        booking.setBookingID(generateId());
        bookings.add(booking);
    }


    public ArrayList<Booking> getBookings(){
        return bookings;
    }




    public CustomerBooking getCustomerBookingWithID(int ID){
        for(int i=0;i<bookings.size();i++){
            if(bookings.get(i).getBookingID()==ID){
                return (CustomerBooking) bookings.get(i);
            }
        }
        return null;
    }

    public void removeBookingWithBookingID(int bookingID){
        for(int i=0;i<bookings.size();i++){
            if(bookings.get(i).getBookingID()==bookingID){
                bookings.remove(bookings.get(i));
            }
        }
    }

    public ArrayList<Integer> getHotelBookingIDs(int hotelID){
        ArrayList<Integer> hotelBookings=new ArrayList<>();
        for(Booking booking: bookings){
            if(booking.getHotelID()==hotelID){
                hotelBookings.add(booking.getBookingID());
            }
        }
        return hotelBookings;
    }


}
