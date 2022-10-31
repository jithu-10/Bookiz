package hotel;

import java.util.ArrayList;


public class HotelDB {
    private int idHelper=999999;
    private static HotelDB hotelDB=new HotelDB();
    private AddressDB addressDB =AddressDB.getInstance();
    private ArrayList<Hotel> hotelList =new ArrayList<>();

    private HotelDB(){}
    public static HotelDB getInstance(){
        return hotelDB;
    }


    public ArrayList<Hotel> getRegisteredHotelList(){
        return hotelList;
    }

    public void addApprovedHotelList(Hotel hotel){
        hotelList.add(hotel);
        hotel.approve();
        hotel.setHotelId(generateID());
        addressDB.addLocality(hotel.getAddress().getLocality());
        addressDB.addCity(hotel.getAddress().getCity());
    }

    private int generateID(){
        return ++idHelper;
    }


    public Hotel getHotelByID(int id){
        for(Hotel hotel: hotelList){
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
        for(Hotel hotel: hotelList){
            if(hotel.getPhoneNumber()==phoneNumber){
                return hotel;
            }
        }
        return null;

    }

//    public Hotel getUnapprovedHotelByPhoneNumber(long phoneNumber){
//        return AdminDB.getInstance().getUnapprovedHotelByPhoneNumber(phoneNumber);
//    }
//
//    public ArrayList<String> getTermsAndConditions(){
//        return AdminDB.getInstance().getTermsAndConditions();
//    }


}
