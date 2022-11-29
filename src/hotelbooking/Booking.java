package hotelbooking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Booking {

    private final Date checkInDate;
    private final Date checkOutDate;
    private final int bookingID;
    private final Customer customer;
    private final Hotel hotel;


    Booking(Date checkInDate,Date checkOutDate,Customer customer,Hotel hotel){
        Helper.validateCheckInDate(checkInDate);
        Helper.validateCheckOutDate(checkInDate,checkOutDate);
        Helper.validateNull(customer,"Customer can't be null");
        Helper.validateNull(hotel,"Hotel can't be null");
        this.bookingID=BookingDB.getInstance().generateId();
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
        this.customer=customer;
        this.hotel=hotel;
    }

    public int getBookingID(){
        return bookingID;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }

    public Date getCheckOutDate(){
        return checkOutDate;
    }


    public int getNoOfDays(){
        return Helper.getDatesBetweenTwoDates(checkInDate,checkOutDate).size();
    }


    public Customer getCustomer(){
        return customer;
    }

    public Hotel getHotel(){
        return hotel;
    }


    

}

