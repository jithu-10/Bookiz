package hotelbooking;

import java.util.ArrayList;
import java.util.Date;

public class CustomerBooking extends Booking{



    private final int noOfRoomsBooked;
    private final ArrayList<Integer> noOfGuestsInEachRoom;
    private final ArrayList<Integer> bookedRoomIDs;
    private boolean paid;



    public CustomerBooking(Date checkInDate,Date checkOutDate,Customer customer,Hotel hotel,int noOfRoomsBooked,ArrayList<Integer> noOfGuestsInEachRoom,ArrayList<Integer> bookedRoomIDs){
        super(checkInDate,checkOutDate,customer,hotel);
        Helper.validateNull(noOfGuestsInEachRoom,"Guest Capacity in each room can't be null");
        Helper.validateNull(bookedRoomIDs,"Rooms Can't Be null");
        this.noOfRoomsBooked=noOfRoomsBooked;
        this.noOfGuestsInEachRoom=noOfGuestsInEachRoom;
        this.bookedRoomIDs =bookedRoomIDs;

    }

    public int getTotalNoOfRoomsBooked() {
        return noOfRoomsBooked;
    }


    public double getTotalPrice(){
        return calculateTotalPrice();
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

    public ArrayList<Integer> getBookedRoomIDs() {
        return bookedRoomIDs;
    }



    private double calculateTotalPrice(){
        ArrayList<Room> bookedRooms=new ArrayList<>();
        for(int i = 0; i< bookedRoomIDs.size(); i++){
            bookedRooms.add(getHotel().getRoomByID(bookedRoomIDs.get(i)));
        }
        double totalPrice=0;
        for(int i = 0; i< bookedRooms.size(); i++){
            double roomPrice_oneDay=bookedRooms.get(i).getRoomListPrice()+(noOfGuestsInEachRoom.get(i)*bookedRooms.get(i).getBedPrice());
            double roomPrice_total=roomPrice_oneDay*getNoOfDays();
            totalPrice+=roomPrice_total;
        }
        return totalPrice;

    }

}
