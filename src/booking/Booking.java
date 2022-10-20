package booking;

import utility.InputHelper;

import java.util.Date;


public class Booking {

    private final Date checkInDate;
    private final Date checkOutDate;
    private final int noOfSingleBedroomsNeeded;
    private final int noOfDoubleBedroomsNeeded;
    private final int noOfSuiteRoomNeeded;
    private int bookingID;
    private double totalPriceOfSingleBedRooms;
    private double totalPriceOfDoubleBedRooms;
    private double totalPriceOfSuiteRooms;
    private int noOfDays;
    private int customerID;
    private boolean paid;
    private int hotelID;
    public Booking(Date checkInDate,Date checkOutDate,int noOfSingleBedroomsNeeded,int noOfDoubleBedroomsNeeded,int noOfSuiteRoomNeeded){
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
        this.noOfSingleBedroomsNeeded=noOfSingleBedroomsNeeded;
        this.noOfDoubleBedroomsNeeded=noOfDoubleBedroomsNeeded;
        this.noOfSuiteRoomNeeded=noOfSuiteRoomNeeded;
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
        return noOfSingleBedroomsNeeded+noOfDoubleBedroomsNeeded+noOfSuiteRoomNeeded;
    }

    public double getTotalPrice(){
        return totalPriceOfSingleBedRooms+totalPriceOfDoubleBedRooms+totalPriceOfSuiteRooms;
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
    public void setPaid(){
        paid=true;
    }
    public boolean getPaid(){
        return paid;
    }

    public void setHotelID(int hotelID){
        this.hotelID=hotelID;
    }

    public int getHotelID(){
        return hotelID;
    }

    public void setTotalPriceOfSingleBedRooms(double totalPriceOfSingleBedRooms) {
        this.totalPriceOfSingleBedRooms = totalPriceOfSingleBedRooms;
    }

    public void setTotalPriceOfDoubleBedRooms(double totalPriceOfDoubleBedRooms) {
        this.totalPriceOfDoubleBedRooms = totalPriceOfDoubleBedRooms;
    }

    public void setTotalPriceOfSuiteRooms(double totalPriceOfSuiteRooms) {
        this.totalPriceOfSuiteRooms = totalPriceOfSuiteRooms;
    }
}

