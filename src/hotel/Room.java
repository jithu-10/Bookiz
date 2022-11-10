package hotel;

public class Room {


    private final int id;
    private final int roomCapacity;
    private Price roomPrice;
    private Price bedPrice;



    public Room(int id,int roomCapacity,Price roomPrice,Price bedPrice){
        this.id=id;
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
        this.roomPrice=roomPrice;
    }

    public void changeBedPrice(Price bedPrice){
        this.bedPrice=bedPrice;
    }



}












