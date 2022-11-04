package booking;

import utility.InputHelper;

import java.util.Date;


public class Booking {

    private int bookingID;
    private Date checkInDate;
    private Date checkOutDate;
    private int customerID;
    private int hotelID;

    public Booking(Date checkInDate,Date checkOutDate){
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
    }

    public void setBookingID(int id){
        bookingID=id;
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

    public String getCheckInDateString(){
        return InputHelper.getSimpleDateWithoutYear(checkInDate);
    }

    public String getCheckOutDateString(){
        return InputHelper.getSimpleDateWithoutYear(checkOutDate);
    }


    public int getNoOfDays(){
        return InputHelper.getDatesBetweenTwoDates(checkInDate,checkOutDate).size();
    }

    public void setCustomerID(int customerID){
        this.customerID=customerID;
    }
    public int getCustomerID(){
        return customerID;
    }

    public void setHotelID(int hotelID){
        this.hotelID=hotelID;
    }

    public int getHotelID(){
        return hotelID;
    }


}

