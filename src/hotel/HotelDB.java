package hotel;

import hotel.subutil.AddressDB;
import utility.InputHelper;

import java.util.ArrayList;
import java.util.LinkedList;

public class HotelDB {
    private static int idHelper=0;
    private static AddressDB addressDB =AddressDB.getInstance();
    private static LinkedList<Hotel> hotelsRegisteredForApproval=new LinkedList<>();
    private static ArrayList<Hotel> approvedHotelList=new ArrayList<>();
    public static void registerHotel(Hotel hotel){
        hotelsRegisteredForApproval.add(hotel);
    }

    public static LinkedList<Hotel> getHotelsRegisteredForApproval(){
        return hotelsRegisteredForApproval;
    }

    public static ArrayList<Hotel> getRegisteredHotelList(){
        return approvedHotelList;
    }

    public static void addApprovedHotelList(Hotel hotel){
        approvedHotelList.add(hotel);
        hotel.approve();
        hotel.setHotelId(generateID());
        addressDB.addLocality(hotel.getAddress().getLocality());
        addressDB.addCity(hotel.getAddress().getCity());
    }

    private static int generateID(){
        return ++idHelper;
    }


    public static Hotel getHotelByID(int id){
        for(Hotel hotel: approvedHotelList){
            if(hotel.getHotelID()==id){
                return hotel;
            }
        }
        return null;
    }

    public static boolean removeHotels(int hotelID){
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

    public static Hotel getHotelByPhoneNumber(long phoneNumber){
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
