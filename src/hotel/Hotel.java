package hotel;

import hotel.subutil.Address;
import hotel.subutil.Price;
import user.User;
import utility.InputHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Hotel extends User {

    private String hotelAdminName;
    private int hotelID;
    private long phoneNumber;
    private String password;
    private String hotelName;
    private Address address;
    private String locality;
    private HotelType hotelType;
    private ArrayList<Amenity> amenities=new ArrayList<>();
    private ArrayList<Room> rooms=new ArrayList<>();
    private ArrayList<Integer> bookingIDs=new ArrayList<>();
    private Price singleBedRoomPrice;
    private Price doubleBedRoomPrice;
    private Price suiteRoomPrice;
    private int totalAmenityPoints;
    private int totalSingleBedRooms;
    private int totalDoubleBedRooms;
    private int totalSuiteRooms;
    private boolean approved;
    private HashMap<Date, Integer> singleBedroomsBookedByDate=new HashMap<>();
    private HashMap<Date, Integer> doubleBedroomsBookedByDate=new HashMap<>();
    private HashMap<Date, Integer> suiteRoomsBookedByDate=new HashMap<>();

    public void updateHashMap(int noOfSingleBedRoomsBooked,int noOfDoubleBedRoomsBooked,int noOfSuiteRoomBooked,Date checkInDate,Date checkOutDate){
        ArrayList<Date> datesInRange= InputHelper.getDatesBetweenTwoDates(checkInDate,checkOutDate);
        datesInRange.add(checkOutDate);
        for(int i=0;i<datesInRange.size();i++){
            if(singleBedroomsBookedByDate.containsKey(datesInRange.get(i))){
               int value=singleBedroomsBookedByDate.get(datesInRange.get(i));
               singleBedroomsBookedByDate.put(datesInRange.get(i),value+noOfSingleBedRoomsBooked);
            }
            else{
                singleBedroomsBookedByDate.put(datesInRange.get(i),noOfSingleBedRoomsBooked);
            }
            if(doubleBedroomsBookedByDate.containsKey(datesInRange.get(i))){
                int value=doubleBedroomsBookedByDate.get(datesInRange.get(i));
                doubleBedroomsBookedByDate.put(datesInRange.get(i),value+noOfDoubleBedRoomsBooked);
            }
            else{
                doubleBedroomsBookedByDate.put(datesInRange.get(i),noOfDoubleBedRoomsBooked);
            }
            if(suiteRoomsBookedByDate.containsKey(datesInRange.get(i))){
                int value=suiteRoomsBookedByDate.get(datesInRange.get(i));
                suiteRoomsBookedByDate.put(datesInRange.get(i),value+noOfSuiteRoomBooked);
            }
            else{
                suiteRoomsBookedByDate.put(datesInRange.get(i),noOfSuiteRoomBooked);
            }
        }
    }


    public Hotel(String hotelAdminName,long phoneNumber,String password,String hotelName,Address address,String locality){
        this.hotelAdminName=hotelAdminName;
        this.phoneNumber=phoneNumber;
        this.password=password;
        this.hotelName=hotelName;
        this.address =address;
        this.locality=locality;
    }
    public void setAddress(Address address){
        this.address = address;
    }
    public Address getAddress(){
        return address;
    }
    public void addSingleBedRooms(int count,double basePrice,double maxPrice){
        for(int i=0;i<count;i++){

            Room room=new Room(RoomType.SINGLEBEDROOM);
            rooms.add(room);
            totalSingleBedRooms++;
        }
        setSingleBedRoomPrice(basePrice,maxPrice);
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
        setDoubleBedRoomPrice(basePrice,maxPrice);
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
        setSuiteRoomPrice(basePrice,maxPrice);
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
            if(rooms.get(i).getRoomType()==roomType){
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

    public int getNoOfSingleBedRoomsBookedByDate(Date date){
        if(singleBedroomsBookedByDate.containsKey(date)){
            return singleBedroomsBookedByDate.get(date);
        }
        else{
            return 0;
        }
    }

    public int getNoOfDoubleBedRoomsBookedByDate(Date date){
        if(doubleBedroomsBookedByDate.containsKey(date)){
            return doubleBedroomsBookedByDate.get(date);
        }
        else{
            return 0;
        }
    }

    public int getNoOfSuiteRoomsBookedByDate(Date date){
        if(suiteRoomsBookedByDate.containsKey(date)){
            return suiteRoomsBookedByDate.get(date);
        }
        else{
            return 0;
        }
    }

    public void addBookingIDs(int ID){
        bookingIDs.add(ID);
    }

    public void removeBookingIDs(int ID){
        Integer id=ID;
        bookingIDs.remove(id);
    }


    public ArrayList<Integer> getBookingIDs(){
        return bookingIDs;
    }

    public HashMap<Date, Integer> getSingleBedroomsBooked() {
        return singleBedroomsBookedByDate;
    }

    public HashMap<Date, Integer> getDoubleBedroomsBooked() {
        return doubleBedroomsBookedByDate;
    }

    public HashMap<Date, Integer> getSuiteRoomsBooked() {
        return suiteRoomsBookedByDate;
    }
    public void cancelSingleBedRoomsBooked(Date date,int rooms){
        if(singleBedroomsBookedByDate.containsKey(date)){
            int value=singleBedroomsBookedByDate.get(date);
            value-=rooms;
            singleBedroomsBookedByDate.put(date,value);
        }
    }

    public void cancelDoubleBedRoomsBooked(Date date,int rooms){
        if(doubleBedroomsBookedByDate.containsKey(date)){
            int value=doubleBedroomsBookedByDate.get(date);
            value-=rooms;
            doubleBedroomsBookedByDate.put(date,value);
        }
    }

    public void cancelSuiteRoomsBooked(Date date,int rooms){
        if(suiteRoomsBookedByDate.containsKey(date)){
            int value=suiteRoomsBookedByDate.get(date);
            value-=rooms;
            suiteRoomsBookedByDate.put(date,value);
        }
    }
}
