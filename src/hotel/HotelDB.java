package hotel;


import customer.Customer;
import utility.InputHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class HotelDB {
    private static int idHelper=0;
    private static LinkedList<Hotel> hotelsRegisteredForApproval=new LinkedList<>();
    private static ArrayList<Hotel> approvedHotelList=new ArrayList<>();
    private static ArrayList<String> availableLocalities=new ArrayList<>();
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
        addLocality(hotel.getLocality());
    }

    private static int generateID(){
        return ++idHelper;
    }

    public static Hotel checkAuthentication(long phoneNumber,String password){
        for(Hotel hotel: hotelsRegisteredForApproval){
            if(hotel.getPhoneNumber()==phoneNumber&&hotel.getPassword().equals(password)){
                return hotel;
            }
        }
        for(Hotel hotel:approvedHotelList){
            if(hotel.getPhoneNumber()==phoneNumber&&hotel.getPassword().equals(password)){
                return hotel;
            }
        }
        return null;
    }

    public static Hotel getHotelByID(int id){
        for(Hotel hotel: approvedHotelList){
            if(hotel.getHotelID()==id){
                return hotel;
            }
        }
        return null;
    }

    public static void addLocality(String locality){
        locality= InputHelper.modifyString(locality);
        availableLocalities.add(locality);
    }
    public static void removeLocality(String locality){
        locality=InputHelper.modifyString(locality);
        availableLocalities.remove(locality);
    }

    public static boolean isLocalityAvailable(String locality){
        locality=InputHelper.modifyString(locality);
        for(int i=0;i<availableLocalities.size();i++){
            if(availableLocalities.get(i).equals(locality)){
                return true;
            }
        }
        return false;
    }

    public static boolean isPhoneNumberExist(long phoneNumber){
        ArrayList<Hotel>hotels =approvedHotelList;
        for(int i=0;i<hotels.size();i++){
            if(hotels.get(i).getPhoneNumber()==phoneNumber){
                return true;
            }
        }
        return false;
    }

    public static boolean removeHotels(int hotelID){
        ArrayList<Hotel> registeredHotels=getRegisteredHotelList();
        for(int i=0;i<registeredHotels.size();i++){
            Hotel hotel=registeredHotels.get(i);
            if(hotelID==hotel.getHotelID()){
                HotelDB.removeLocality(hotel.getLocality());
                registeredHotels.remove(i);
                return true;
            }
        }
        return false;

    }


}
