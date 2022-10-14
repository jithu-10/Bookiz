package hotel;

import hotel.utils.Price;

import javax.swing.*;
import java.util.ArrayList;

public class Hotel {

    /* TODO Add Hotel Owner Name Field*/
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

    public Hotel(long phoneNumber,String password,String hotelName,String address,String locality){
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

    public void addDoubleBedRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++){
            Room room=new Room(RoomType.DOUBLEBEDROOM);
            rooms.add(room);
            totalDoubleBedRooms++;
        }
        doubleBedRoomPrice=new Price(basePrice,maxPrice);
    }

    public void addSuiteRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++) {

            Room room=new Room(RoomType.SUITEROOM);
            rooms.add(room);
            totalSuiteRooms++;
        }
        suiteRoomPrice=new Price(basePrice,maxPrice);
    }

    public void addAmenity(Amenity amenity){
        totalAmenityPoints+=amenity.getPoints();
        amenities.add(amenity);
    }

    public void setHotelId(int hotelID){
        this.hotelID=hotelID;
    }
    public int getHotelID(){
        return this.hotelID;
    }
    public long getPhoneNumber() {
        return phoneNumber;
    }

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


}
