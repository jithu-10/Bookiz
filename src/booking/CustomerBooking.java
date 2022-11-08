package booking;

import java.util.ArrayList;
import java.util.Date;

public class CustomerBooking extends Booking{



    private int noOfRoomsBooked;
    private ArrayList<Integer> noOfGuestsInEachRoom;
    private ArrayList<Integer> roomIDs;
    private double totalPrice;
    private boolean paid;

    public CustomerBooking(Date checkInDate, Date checkOutDate){
        super(checkInDate,checkOutDate);
    }


    public int getTotalNoOfRoomsBooked() {
        return noOfRoomsBooked;
    }

    public void setNoOfRoomsBooked(int noOfRoomsBooked){
        this.noOfRoomsBooked=noOfRoomsBooked;
    }



    public void setTotalPrice(double totalPrice){
        this.totalPrice=totalPrice;
    }
    public double getTotalPrice(){
        return totalPrice;
    }

    public void setNoOfGuestsInEachRoom(ArrayList<Integer> noOfGuestsInEachRoom) {
        this.noOfGuestsInEachRoom = noOfGuestsInEachRoom;
    }

    public ArrayList<Integer> getNoOfGuestsInEachRoom() {
        return noOfGuestsInEachRoom;
    }

    public void setPaid(){
        paid=true;
    }
    public boolean getPaid(){
        return paid;
    }

    public ArrayList<Integer> getRoomIDs() {
        return roomIDs;
    }

    public void setRoomIDs(ArrayList<Integer> roomIDs) {
        this.roomIDs = roomIDs;
    }
}
