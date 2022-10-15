package hotel;

import hotel.subutil.Price;
import user.User;
import utility.Printer;

import java.util.ArrayList;

public class Hotel extends User {

    private String hotelAdminName;
    private int hotelID;
    private long phoneNumber;
    private String password;
    private String hotelName;
    private String address;
    private String locality;
    private HotelType hotelType;
    private ArrayList<Amenity> amenities=new ArrayList<>();

    private ArrayList<Room> rooms=new ArrayList<>();
    private Price singleBedRoomPrice;
    private Price doubleBedRoomPrice;
    private Price suiteRoomPrice;

    private int totalAmenityPoints;
    private int totalSingleBedRooms;
    private int totalDoubleBedRooms;
    private int totalSuiteRooms;
    private boolean approved;

    public Hotel(String hotelAdminName,long phoneNumber,String password,String hotelName,String address,String locality){
        this.hotelAdminName=hotelAdminName;
        this.phoneNumber=phoneNumber;
        this.password=password;
        this.hotelName=hotelName;
        this.address=address;
        this.locality=locality;
    }

    public void addSingleBedRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++){

            Room room=new Room(RoomType.SINGLEBEDROOM);
            rooms.add(room);
            totalSingleBedRooms++;
        }
        singleBedRoomPrice=new Price(basePrice,maxPrice);
    }

    public void addSingleBedRooms(int count){
        for(int i=0;i<count;i++){
            Room room=new Room(RoomType.SINGLEBEDROOM);
            rooms.add(room);
            totalSingleBedRooms++;
        }
    }

    public void addDoubleBedRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++){
            Room room=new Room(RoomType.DOUBLEBEDROOM);
            rooms.add(room);
            totalDoubleBedRooms++;
        }
        doubleBedRoomPrice=new Price(basePrice,maxPrice);
    }

    public void addDoubleBedRooms(int count){
        for(int i=0;i<count;i++){
            Room room=new Room(RoomType.DOUBLEBEDROOM);
            rooms.add(room);
            totalDoubleBedRooms++;
        }

    }

    public void addSuiteRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++) {

            Room room=new Room(RoomType.SUITEROOM);
            rooms.add(room);
            totalSuiteRooms++;
        }
        suiteRoomPrice=new Price(basePrice,maxPrice);
    }

    public void addSuiteRooms(int count){
        for(int i=0;i<count;i++) {

            Room room=new Room(RoomType.SUITEROOM);
            rooms.add(room);
            totalSuiteRooms++;
        }

    }
    /*TODO make a common add rooms like remove rooms method below*/
    public void removeRooms(int count,RoomType roomType){
        int value=count;
        ArrayList<Room>rooms=this.rooms;
        for(int i=0;i<rooms.size();i++) {
            if(rooms.get(i).roomType==roomType){
                rooms.remove(i);
                --count;
            }
            if(count==0){
                break;
            }
        }
        switch(roomType){
            case SINGLEBEDROOM:
                totalSingleBedRooms-=value;
                break;
            case DOUBLEBEDROOM:
                totalDoubleBedRooms-=value;
                break;
            case SUITEROOM:
                totalSuiteRooms-=value;
                break;
        }
    }

    public void addAmenity(Amenity amenity){
        totalAmenityPoints+=amenity.getPoints();
        amenities.add(amenity);
        setHotelType();
    }

    public void removeAmenity(int index){
        Amenity amenity=amenities.get(index);
        totalAmenityPoints-=amenity.getPoints();
        amenities.remove(amenity);
        setHotelType();
    }

    public void setHotelId(int hotelID){
        this.hotelID=hotelID;
    }
    public int getHotelID(){
        return this.hotelID;
    }
    public String getHotelAdminName(){
        return this.hotelAdminName;
    }
    public long getPhoneNumber() {
        return phoneNumber;
    }
    public String getPassword(){return password;}

    public String getHotelName() {
        return hotelName;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality() {
        return locality;
    }

    public int getTotalNumberofRooms(){
        return rooms.size();
    }

    public int getNumberofSingleBedRooms(){
        return totalSingleBedRooms;
    }

    public int getNumberofDoubleBedRooms(){
        return totalDoubleBedRooms;
    }

    public int getNumberofSuiteRooms(){
        return totalSuiteRooms;
    }

    public int getTotalAmenityPoints() {
        return totalAmenityPoints;
    }
    public ArrayList<Amenity> getAmenities(){
        return amenities;
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

    public void setSingleBedRoomCurrentPrice(double currentPrice){
        this.singleBedRoomPrice.setCurrentPrice(currentPrice);
    }

    public void setDoubleBedRoomCurrentPrice(double currentPrice){
        this.doubleBedRoomPrice.setCurrentPrice(currentPrice);
    }

    public void setSuiteRoomCurrentPrice(double currentPrice){
        this.suiteRoomPrice.setCurrentPrice(currentPrice);
    }

    public void setHotelType() {
        if(totalAmenityPoints>=90){
            this.hotelType=HotelType.TOWNHOUSE;
        }
        else if(totalAmenityPoints>=70){
            this.hotelType=HotelType.SPOTZ;
        }
        else {
            this.hotelType=HotelType.COLLECTIONZ;
        }

    }

    public HotelType getHotelType(){
        return this.hotelType;
    }
    public void approve(){
        this.approved=true;
    }

    public void cancelApprove(){
        this.approved=false;
    }

    public boolean isApproved(){
        return approved;
    }


}
