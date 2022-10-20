package booking;

import java.util.Date;


public class Booking {

    private final Date checkInDate;
    private final Date checkOutDate;
    private final String checkInDateString;
    private final String checkOutDateString;
    private final int noOfSingleBedroomsNeeded;
    private final int noOfDoubleBedroomsNeeded;
    private final int noOfSuiteRoomNeeded;
    private final int totalNoOfRoomsNeeded;
    private int bookingID;
    private double totalPrice;
    private double totalPriceOfSingleBedRooms;
    private double totalPriceOfDoubleBedRooms;
    private double totalPriceOfSuiteRooms;
    private int noOfDays;
    private int customerID;
    private boolean paid;
    private int hotelID;
    public Booking(Date checkInDate,Date checkOutDate,String checkInDateString,String checkOutDateString,int noOfSingleBedroomsNeeded,int noOfDoubleBedroomsNeeded,int noOfSuiteRoomNeeded){
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
        this.noOfSingleBedroomsNeeded=noOfSingleBedroomsNeeded;
        this.noOfDoubleBedroomsNeeded=noOfDoubleBedroomsNeeded;
        this.noOfSuiteRoomNeeded=noOfSuiteRoomNeeded;
        this.totalNoOfRoomsNeeded=noOfSingleBedroomsNeeded+noOfDoubleBedroomsNeeded+noOfSuiteRoomNeeded;
        this.checkInDateString=checkInDateString;
        this.checkOutDateString=checkOutDateString;
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
        return checkInDateString;
    }

    public String getCheckOutDateString(){
        return checkOutDateString;
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

