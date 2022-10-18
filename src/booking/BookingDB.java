package booking;

import java.util.ArrayList;

public class BookingDB {
    private static int idHelper=99999;
    private static ArrayList<Booking> bookings=new ArrayList<>();
    private static int generateId(){
        return ++idHelper;
    }
    public static void addBooking(Booking booking){
        booking.setBookingID(generateId());
        bookings.add(booking);
    }

    public static ArrayList<Booking> getBookings(){
        return bookings;
    }

    public static Booking getBookingWithID(int ID){
        for(int i=0;i<bookings.size();i++){
            if(bookings.get(i).getBookingID()==ID){
                return bookings.get(i);
            }
        }
        return null;
    }

}
