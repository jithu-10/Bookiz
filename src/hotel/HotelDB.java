package hotel;

import hotel.subutil.AddressDB;
import utility.InputHelper;

import java.util.ArrayList;
import java.util.LinkedList;

public class HotelDB {
    private int idHelper=0;
    private static HotelDB hotelDB=new HotelDB();
    private AddressDB addressDB =AddressDB.getInstance();
    private LinkedList<Hotel> hotelsRegisteredForApproval=new LinkedList<>();
    private ArrayList<Hotel> approvedHotelList=new ArrayList<>();

    private HotelDB(){}
    public static HotelDB getInstance(){
        return hotelDB;
    }
    public void registerHotel(Hotel hotel){
        hotelsRegisteredForApproval.add(hotel);
    }

    public LinkedList<Hotel> getHotelsRegisteredForApproval(){
        return hotelsRegisteredForApproval;
    }

    public ArrayList<Hotel> getRegisteredHotelList(){
        return approvedHotelList;
    }

    public void addApprovedHotelList(Hotel hotel){
        approvedHotelList.add(hotel);
        hotel.approve();
        hotel.setHotelId(generateID());
        addressDB.addLocality(hotel.getAddress().getLocality());
        addressDB.addCity(hotel.getAddress().getCity());
    }

    private int generateID(){
        return ++idHelper;
    }


    public Hotel getHotelByID(int id){
        for(Hotel hotel: approvedHotelList){
            if(hotel.getHotelID()==id){
                return hotel;
            }
        }
        return null;
    }

    public boolean removeHotels(int hotelID){
        ArrayList<Hotel> registeredHotels=getRegisteredHotelList();
        for(int i=0;i<registeredHotels.size();i++){
            Hotel hotel=registeredHotels.get(i);
            if(hotelID==hotel.getHotelID()){
                addressDB.removeLocality(hotel.getAddress().getLocality());
                addressDB.removeCity(hotel.getAddress().getCity());
                registeredHotels.remove(i);
                return true;
            }
        }
        return false;

    }

    public Hotel getHotelByPhoneNumber(long phoneNumber){
        for(Hotel hotel: approvedHotelList){
            if(hotel.getPhoneNumber()==phoneNumber){
                return hotel;
            }
        }
        for(Hotel hotel: hotelsRegisteredForApproval){
            if(hotel.getPhoneNumber()==phoneNumber){
                return hotel;
            }
        }
        return null;

    }


}
