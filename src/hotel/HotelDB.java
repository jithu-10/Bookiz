package hotel;


import java.util.ArrayList;
import java.util.LinkedList;

public class HotelDB {



    private static int idHelper=0;
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


}
