package hotelbooking;

public class Room {


    private final int id;
    private final int roomCapacity;
    private Price roomPrice;
    private Price bedPrice;



    public Room(Hotel hotel,int roomCapacity,Price roomPrice,Price bedPrice){
        Helper.validateNull(roomPrice,"Room Price can't be null");
        Helper.validateNull(bedPrice,"Bed Price can't be null");
        validateRoomCapacity(roomCapacity);
        this.id=hotel.roomIDHelper++;
        this.roomCapacity=roomCapacity;
        this.roomPrice=roomPrice;
        this.bedPrice=bedPrice;
    }

    public int getId() {
        return id;
    }

    public double getRoomBasePrice(){
        return roomPrice.getBasePrice();
    }

    public double getRoomMaxPrice(){
        return roomPrice.getMaxPrice();
    }
    public double getRoomListPrice(){
        return roomPrice.getListPrice();
    }

    public void setRoomListPrice(double listPrice){
        roomPrice.setListPrice(listPrice);
    }



    public double getBedPrice(){
        return bedPrice.getBasePrice();
    }

    public int getRoomCapacity(){
        return roomCapacity;
    }

    public void changeRoomPrice(Price roomPrice){
        Helper.validateNull(roomPrice,"Room Price can't be null");
        this.roomPrice=roomPrice;
    }

    public void changeBedPrice(Price bedPrice){
        this.bedPrice=bedPrice;
    }

    private static void validateRoomCapacity(int roomCapacity){
        if(roomCapacity<1){
            throw new RuntimeException("Room Capacity should be greater than 0");
        }
    }

}












