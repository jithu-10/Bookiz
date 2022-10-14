package hotel;


import java.util.ArrayList;

public class HotelDB {

    private static int idHelper=0;
    private static ArrayList<Hotel> hotelsRegisteredForApproval=new ArrayList<>();
    private static ArrayList<Hotel> approvedHotelList=new ArrayList<>();

    public static void registerHotel(Hotel hotel){
        hotelsRegisteredForApproval.add(hotel);
    }

    public static ArrayList<Hotel> getHotelsRegisteredForApproval(){
        return hotelsRegisteredForApproval;
    }

    public static ArrayList<Hotel> getRegisteredHotelList(){
        return approvedHotelList;
    }

    public static void addApprovedHotelList(Hotel hotel){
        approvedHotelList.add(hotel);
    }

    public static int generateID(){
        return ++idHelper;
    }


}
