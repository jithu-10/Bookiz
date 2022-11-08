package hotel;

import user.User;
import user.UserDB;

import java.util.ArrayList;
import java.util.Date;

public class Hotel{
    private int hotelOwnerID;
    private int hotelID;
    private String hotelName;
    private Address address;
    private HotelType hotelType;
    private HotelApprovalStatus hotelApprovalStatus;
    private ArrayList<Integer> amenities=new ArrayList<>();
    private ArrayList<Room> rooms=new ArrayList<>();
    private AmenityDB amenityDB=AmenityDB.getInstance();
    private int roomIDHelper=0;

    public Hotel(int hotelOwnerID,String hotelName,Address address){
        this.hotelOwnerID=hotelOwnerID;
        this.hotelName=hotelName;
        this.address=address;
    }


    /********Second Constructor USED for INIT************/
    public Hotel(int hotelOwnerID, String hotelName, Address address, HotelType hotelType, HotelApprovalStatus hotelApproveStatus, ArrayList<Integer> amenities, ArrayList<Room> rooms){
        this.hotelOwnerID=hotelOwnerID;
        this.hotelName=hotelName;
        this.address=address;
        this.hotelType=hotelType;
        this.hotelApprovalStatus =hotelApproveStatus;
        this.amenities=amenities;
        this.rooms=rooms;
    }

    public int getHotelOwnerID() {
        return hotelOwnerID;
    }

    public void setHotelOwnerID(int hotelOwnerID) {
        this.hotelOwnerID = hotelOwnerID;
    }

    public User getHotelOwnerDetails(){
        return UserDB.getInstance().getHotelAdminByID(hotelOwnerID);
    }



    public void updateRoomBooking(Date checkInDate,Date checkOutDate,boolean book,ArrayList<Integer> roomIndex){
        for(int i=0;i<roomIndex.size();i++){
            Room room=getRoomByID(roomIndex.get(i));
            room.updateBookings(checkInDate,checkOutDate,book);
        }

    }
    public Address getAddress(){
        return address;
    }
    public long getPhoneNumber(){
        return getHotelOwnerDetails().getPhoneNumber();
    }


    public void addRooms(int count,int maxCapacity,double minRoomPrice,double maxRoomPrice,double maxBedPrice){
        for(int i=0;i<count;i++){
            Price roomPrice=new Price(minRoomPrice,maxRoomPrice);
            Price bedPrice=new Price(maxBedPrice,maxBedPrice);
            Room room=new Room(++roomIDHelper,maxCapacity,roomPrice,bedPrice);
            rooms.add(room);
        }
    }




    public void removeRooms(Room room){
        rooms.remove(room);
    }

    public void addAmenity(Amenity amenity){;
        amenities.add(amenity.getAmenityID());
    }

    public void removeAmenity(Amenity amenity){
        amenities.remove((Integer)amenity.getAmenityID());
    }

    public void setHotelId(int hotelID){
        this.hotelID=hotelID;
    }
    public int getHotelID(){
        return this.hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getTotalNumberOfRooms(){
        return rooms.size();
    }

    public int getTotalAmenityPoints() {
        int totalAmenityPoints=0;

        for(int amenityID: amenities){
            totalAmenityPoints+=amenityDB.getAmenityPointsByID(amenityID);
        }
        return totalAmenityPoints;
    }

    public double getTotalAmenityPercent(){
        double totalAmenityPoints=AmenityDB.getInstance().getTotalAmenityPoints();
        double totalHotelAmenityPoints=getTotalAmenityPoints();
        return (totalHotelAmenityPoints/totalAmenityPoints)*100;

    }
    public ArrayList<Amenity> getAmenities(){
        ArrayList<Amenity> hotelAmenities=new ArrayList<>();
        for(int amenityId: amenities){
            Amenity amenity=amenityDB.getAmenityByID(amenityId);
            if(amenity!=null){
                hotelAmenities.add(amenityDB.getAmenityByID(amenityId));
            }
        }
        return hotelAmenities;
    }


    public void setHotelType(){

        int amenityPercent=(int)getTotalAmenityPercent();
        if(amenityPercent>=90){
            this.hotelType=HotelType.TOWNHOUSE;
        }
        else if (amenityPercent>=50){
            this.hotelType=HotelType.COLLECTIONZ;
        }
        else{
            this.hotelType=HotelType.SPOTZ;
        }

    }

    public void setHotelType(HotelType hotelType){
        this.hotelType=hotelType;
    }

    public HotelType getHotelType(){
        return this.hotelType;
    }

    public void setHotelApprovalStatus(HotelApprovalStatus hotelApprovalStatus){
        this.hotelApprovalStatus = hotelApprovalStatus;
    }

    public HotelApprovalStatus getHotelApprovalStatus() {
        return hotelApprovalStatus;
    }
    public int getNoOfRoomsBookedByDate(Date date){
        int value=0;
        for(Room room : rooms){
            if(room.checkBookedByDate(date)){
                value++;
            }
        }
        return value;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Room getRoomByID(int id){
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).getId()==id){
                return rooms.get(i);
            }
        }
        return null;
    }
}
