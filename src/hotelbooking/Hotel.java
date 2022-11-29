package hotelbooking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Hotel{
    private final int hotelID;
    private final User hotelOwner;
    private final String hotelName;
    private final Address address;
    private HotelType hotelType;
    private HotelApprovalStatus hotelApprovalStatus;
    private final ArrayList<Integer> amenities=new ArrayList<>();
    private final ArrayList<Room> rooms=new ArrayList<>();
    private final HashMap<Integer,ArrayList<Date>> roomBookedStatus=new HashMap<>();// HashMap<RoomID,ArrayList<Date>>
    int roomIDHelper=1;


    public Hotel(User hotelOwner,String hotelName,Address address){
        Helper.validateNull(hotelOwner,"Hotel Owner can't be null");
        Helper.validateString(hotelName,"Hotel Name can't be null or empty");
        Helper.validateNull(address,"Address can't be null");
        this.hotelID=HotelDB.getInstance().generateID();
        this.hotelOwner=hotelOwner;
        this.hotelName=hotelName;
        this.address=address;
        this.hotelApprovalStatus=HotelApprovalStatus.ON_PROCESS;
        this.hotelType=HotelType.PREMIUM;
    }

    private void updateRoomBookedStatus(int roomID,Date checkInDate,Date checkOutDate,boolean book){
        if(book){
            ArrayList<Date> bookedDates= Helper.getDatesBetweenTwoDates(checkInDate,checkOutDate);
            bookedDates.add(checkOutDate);
            for(int i=0;i<bookedDates.size();i++){
                if(checkBookedByDate(roomID,bookedDates.get(i))){
                    throw new RuntimeException("Already selected rooms are booked on this date for selected hotel");
                }
            }
            ArrayList<Date> previouslyBookedDates=roomBookedStatus.get(roomID);
            if(previouslyBookedDates!=null){
                bookedDates.addAll(previouslyBookedDates);
            }

            roomBookedStatus.put(roomID,bookedDates);
        }
        else{
            ArrayList<Date> unBookedDates=Helper.getDatesBetweenTwoDates(checkInDate,checkOutDate);
            unBookedDates.add(checkOutDate);
            ArrayList<Date> dates=roomBookedStatus.get(roomID);
            dates.removeAll(unBookedDates);

        }
    }

    boolean checkBookedByDate(int roomID,Date date){

        ArrayList<Date> bookedDates=roomBookedStatus.get(roomID);
        return bookedDates != null && bookedDates.contains(date);
    }


    User getHotelOwner(){
        return hotelOwner;
    }



    void addRoomBooking(Date checkInDate,Date checkOutDate,ArrayList<Integer> roomIDs){
        for(int i=0;i<roomIDs.size();i++){
            updateRoomBookedStatus(roomIDs.get(i),checkInDate,checkOutDate,true);
        }
    }

    void cancelRoomBooking(Date checkInDate,Date checkOutDate,ArrayList<Integer> roomIDs){
        for(int i=0;i<roomIDs.size();i++){
            updateRoomBookedStatus(roomIDs.get(i),checkInDate,checkOutDate,false);
        }
    }
    public Address getAddress(){
        return address;
    }
    public long getPhoneNumber(){
        return getHotelOwner().getPhoneNumber();
    }


    public void addRoom(Room room){
        Helper.validateNull(room,"Room can't be null");
        rooms.add(room);
    }




    public void removeRooms(Room room){
        Helper.validateNull(room,"Room can't be null");
        rooms.remove(room);
    }

    public void addAmenity(Amenity amenity){
        Helper.validateNull(amenity,"Amenity can't be null");
        amenities.add(amenity.getAmenityID());
    }


    public void removeAmenity(Amenity amenity){
        Helper.validateNull(amenity,"Amenity can't be null");
        amenities.remove((Integer)amenity.getAmenityID());
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
            totalAmenityPoints+= AmenityDB.getInstance().getAmenityPointsByID(amenityID);
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
            Amenity amenity= AmenityDB.getInstance().getAmenityByID(amenityId);
            if(amenity!=null){
                hotelAmenities.add(AmenityDB.getInstance().getAmenityByID(amenityId));
            }
        }
        return hotelAmenities;
    }


    public void setHotelType(){

        int amenityPercent=(int)getTotalAmenityPercent();
        if(amenityPercent>=90){
            this.hotelType=HotelType.ELITE;
        }
        else if (amenityPercent>=50){
            this.hotelType=HotelType.PREMIUM;
        }
        else{
            this.hotelType=HotelType.STANDARD;
        }

    }

    public void setHotelType(HotelType hotelType){
        Helper.validateNull(hotelType,"Hotel type can't be null");
        this.hotelType=hotelType;
    }

    public HotelType getHotelType(){
        return this.hotelType;
    }

    public void setHotelApprovalStatus(HotelApprovalStatus hotelApprovalStatus){
        Helper.validateNull(hotelApprovalStatus,"Hotel Approval Status can't be null");
        this.hotelApprovalStatus = hotelApprovalStatus;
    }

    public HotelApprovalStatus getHotelApprovalStatus() {
        return hotelApprovalStatus;
    }
    public int getNoOfRoomsBookedByDate(Date date){
        Helper.validateNull(date,"Date can't be null");
        int value=0;
        for(Room room : rooms){
            if(checkBookedByDate(room.getId(),date)){
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
