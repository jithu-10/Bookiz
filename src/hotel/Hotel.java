package hotel;

import user.User;
import user.UserDB;
import user.UserType;

import java.util.ArrayList;
import java.util.Date;

public class Hotel{
    private int hotelOwnerID;
    private int hotelID;
    private String hotelName;
    private Address address;
    private HotelType hotelType;
    private HotelStatus hotelApproveStatus;
    private ArrayList<Integer> amenities=new ArrayList<>();
    private ArrayList<Room> rooms=new ArrayList<>();
    private Price singleBedRoomPrice;
    private Price doubleBedRoomPrice;
    private Price suiteRoomPrice;
    private AmenityDB amenityDB=AmenityDB.getInstance();

    public Hotel(int hotelOwnerID,String hotelName,Address address){
        this.hotelOwnerID=hotelOwnerID;
        this.hotelName=hotelName;
        this.address=address;
    }

    public Hotel(int hotelOwnerID,String hotelName,Address address,HotelType hotelType,HotelStatus hotelApproveStatus,ArrayList<Integer> amenities,ArrayList<Room> rooms,Price singleBedRoomPrice,Price doubleBedRoomPrice,Price suiteRoomPrice){
        this.hotelOwnerID=hotelOwnerID;
        this.hotelName=hotelName;
        this.address=address;
        this.hotelType=hotelType;
        this.hotelApproveStatus=hotelApproveStatus;
        this.amenities=amenities;
        this.rooms=rooms;
        this.singleBedRoomPrice=singleBedRoomPrice;
        this.setSingleBedRoomPrice(singleBedRoomPrice.getBasePrice(),singleBedRoomPrice.getMaxPrice());
        this.doubleBedRoomPrice=doubleBedRoomPrice;
        this.setDoubleBedRoomPrice(doubleBedRoomPrice.getBasePrice(),doubleBedRoomPrice.getMaxPrice());
        this.suiteRoomPrice=suiteRoomPrice;
        this.setSuiteRoomPrice(suiteRoomPrice.getBasePrice(),suiteRoomPrice.getMaxPrice());
    }

    public int getHotelOwnerID() {
        return hotelOwnerID;
    }

    public void setHotelOwnerID(int hotelOwnerID) {
        this.hotelOwnerID = hotelOwnerID;
    }

    public User getHotelOwnerDetails(){
        return UserDB.getInstance().getUserByID(hotelOwnerID, UserType.HOTEL_OWNER);
    }

    public void updateRoomBooking(int noOfSingleBedRoomsBooked,int noOfDoubleBedRoomsBooked,int noOfSuiteRoomBooked,Date checkInDate,Date checkOutDate,boolean book){
        for(Room room: rooms){
            if(room.getRoomType()==RoomType.SINGLE_BED_ROOM&&noOfSingleBedRoomsBooked!=0){
                room.updateBookings2(checkInDate,checkOutDate,book);
                noOfSingleBedRoomsBooked--;
            }
            else if(room.getRoomType()==RoomType.DOUBLE_BED_ROOM&&noOfDoubleBedRoomsBooked!=0){
                room.updateBookings2(checkInDate,checkOutDate,book);
                noOfDoubleBedRoomsBooked--;
            }
            else if(room.getRoomType()==RoomType.SUITE_ROOM&&noOfSuiteRoomBooked!=0){
                room.updateBookings2(checkInDate,checkOutDate,book);
                noOfSuiteRoomBooked--;
            }

        }
    }

    public void setAddress(Address address){
        this.address = address;
    }
    public Address getAddress(){
        return address;
    }
    public long getPhoneNumber(){
        return getHotelOwnerDetails().getPhoneNumber();
    }
    public void addSingleBedRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++){

            Room room=new Room(RoomType.SINGLE_BED_ROOM);
            rooms.add(room);
        }
        setSingleBedRoomPrice(basePrice,maxPrice);
    }


    public void addDoubleBedRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++){
            Room room=new Room(RoomType.DOUBLE_BED_ROOM);
            rooms.add(room);
        }
        setDoubleBedRoomPrice(basePrice,maxPrice);
    }


    public void addSuiteRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++) {

            Room room=new Room(RoomType.SUITE_ROOM);
            rooms.add(room);

        }
        setSuiteRoomPrice(basePrice,maxPrice);
    }



    public void addRooms(int count,RoomType roomType){
        for(int i=0;i<count;i++){
            Room room=new Room(roomType);
            rooms.add(room);
        }
    }
    public void removeRooms(int count,RoomType roomType){
        ArrayList<Room>rooms=this.rooms;
        for(int i=0;i<rooms.size();i++) {
            if(rooms.get(i).getRoomType()==roomType){
                rooms.remove(i);
                --count;
            }
            if(count==0){
                break;
            }
        }
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

    public int getTotalNumberOfRooms(RoomType roomType){
        int count=0;
        for(Room room: rooms){
            if(room.getRoomType()==roomType){
                count++;
            }
        }
        return count;
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

    public double getSingleBedRoomBasePrice(){
        if(singleBedRoomPrice!=null){
            return singleBedRoomPrice.getBasePrice();
        }
        return -1;
    }

    public double getDoubleBedRoomBasePrice(){
        if(doubleBedRoomPrice!=null){
            return doubleBedRoomPrice.getBasePrice();
        }
        return -1;
    }

    public double getSuiteRoomBasePrice(){
        if(suiteRoomPrice!=null){
            return suiteRoomPrice.getBasePrice();
        }
        return -1;
    }


    public double getSingleBedRoomMaxPrice(){
        if(singleBedRoomPrice!=null){
            return singleBedRoomPrice.getMaxPrice();
        }
        return -1;
    }

    public double getDoubleBedRoomMaxPrice(){
        if(doubleBedRoomPrice!=null){
            return doubleBedRoomPrice.getMaxPrice();
        }
        return -1;
    }

    public double getSuiteRoomMaxPrice(){
        if(suiteRoomPrice!=null){
            return suiteRoomPrice.getMaxPrice();
        }
        return -1;
    }

    public double getSingleBedRoomListPrice(){
        if(singleBedRoomPrice!=null){
            return singleBedRoomPrice.getListPrice();
        }
        return -1;
    }

    public double getDoubleBedRoomListPrice(){
        if(doubleBedRoomPrice!=null){
            return doubleBedRoomPrice.getListPrice();
        }
        return -1;
    }

    public double getSuiteRoomListPrice(){
        if(suiteRoomPrice!=null){
            return suiteRoomPrice.getListPrice();
        }
        return -1;
    }

    public void setSingleBedRoomPrice(double basePrice,double maxPrice){
        if(singleBedRoomPrice==null){
            singleBedRoomPrice=new Price(basePrice,maxPrice);
        }
        else{
            singleBedRoomPrice.setBasePrice(basePrice);
            singleBedRoomPrice.setMaxPrice(maxPrice);
        }
        if(singleBedRoomPrice.getListPrice()<basePrice||singleBedRoomPrice.getListPrice()>maxPrice){
            singleBedRoomPrice.setListPrice(basePrice);
        }
    }


    public void setDoubleBedRoomPrice(double basePrice,double maxPrice){
        if(doubleBedRoomPrice==null){
            doubleBedRoomPrice=new Price(basePrice,maxPrice);
        }
        else{
            doubleBedRoomPrice.setBasePrice(basePrice);
            doubleBedRoomPrice.setMaxPrice(maxPrice);
        }

        if(doubleBedRoomPrice.getListPrice()<basePrice||doubleBedRoomPrice.getListPrice()>maxPrice){
            doubleBedRoomPrice.setListPrice(basePrice);
        }
    }

    public void setSuiteRoomPrice(double basePrice,double maxPrice){
       if(suiteRoomPrice==null){
           suiteRoomPrice=new Price(basePrice,maxPrice);
       }
       else{
           suiteRoomPrice.setBasePrice(basePrice);
           suiteRoomPrice.setMaxPrice(maxPrice);
       }
        if(suiteRoomPrice.getListPrice()<basePrice||suiteRoomPrice.getListPrice()>maxPrice){
            suiteRoomPrice.setListPrice(basePrice);
        }
    }

    public void setSingleBedRoomListPrice(double currentPrice){
        this.singleBedRoomPrice.setListPrice(currentPrice);
    }

    public void setDoubleBedRoomListPrice(double currentPrice){
        this.doubleBedRoomPrice.setListPrice(currentPrice);
    }

    public void setSuiteRoomListPrice(double currentPrice){
        this.suiteRoomPrice.setListPrice(currentPrice);
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

    public void setHotelApproveStatus(HotelStatus hotelApproveStatus){
        this.hotelApproveStatus=hotelApproveStatus;
    }

    public HotelStatus getHotelApproveStatus() {
        return hotelApproveStatus;
    }

    public int getNoOfRoomsBookedByDate(Date date,RoomType roomType){
        int value=0;
        for(Room room: rooms){
            if(room.getRoomType()==roomType&& room.checkBookedByDate2(date)){
                value++;
            }
        }
        return value;
    }


}
