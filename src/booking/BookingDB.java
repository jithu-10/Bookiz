package booking;

import java.util.ArrayList;

public class BookingDB {
    private int idHelper=99999;
    private static BookingDB bookingDB=new BookingDB();
    private ArrayList<Booking> bookings=new ArrayList<>();

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

    public Booking getBookingWithID(int ID){
        for(int i=0;i<bookings.size();i++){
            if(bookings.get(i).getBookingID()==ID){
                return bookings.get(i);
            }
        }
        return null;
    }

    public void removeBooking(Booking booking){
        bookings.remove(booking);
    }

}
