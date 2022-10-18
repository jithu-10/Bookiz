package booking;

import hotel.Room;
import hotel.RoomType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Booking {

    private Date checkInDate;
    private Date checkOutDate;
    private int noOfSingleBedroomsNeeded;
    private int noOfDoubleBedroomsNeeded;
    private int noOfSuiteRoomNeeded;
    private int totalNoOfRoomsNeeded;
    private int bookingID;
    private double totalPrice;
    private int noOfDays;
    private int customerID;
    public Booking(Date checkInDate,Date checkOutDate,int noOfSingleBedroomsNeeded,int noOfDoubleBedroomsNeeded,int noOfSuiteRoomNeeded){
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
        this.noOfSingleBedroomsNeeded=noOfSingleBedroomsNeeded;
        this.noOfDoubleBedroomsNeeded=noOfDoubleBedroomsNeeded;
        this.noOfSuiteRoomNeeded=noOfSuiteRoomNeeded;
        this.totalNoOfRoomsNeeded=noOfSingleBedroomsNeeded+noOfDoubleBedroomsNeeded+noOfSuiteRoomNeeded;
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

    public int getNoOfSingleBedroomsNeeded() {
        return noOfSingleBedroomsNeeded;
    }

    public int getNoOfDoubleBedroomsNeeded() {
        return noOfDoubleBedroomsNeeded;
    }

    public int getNoOfSuiteRoomNeeded() {
        return noOfSuiteRoomNeeded;
    }

    public int getTotalNoOfRoomsNeeded() {
        return totalNoOfRoomsNeeded;
    }
    public void setTotalPrice(double totalPrice){
        this.totalPrice=totalPrice;
    }
    public double getTotalPrice(){
        return totalPrice;
    }
    public void setNoOfDays(int noOfDays){
        this.noOfDays=noOfDays;
    }

    public int getNoOfDays(){
        return noOfDays;
    }

    public void setCustomerID(int customerID){
        this.customerID=customerID;
    }
    public int getCustomerID(){
        return customerID;
    }
}
