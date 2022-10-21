package admin;


import hotel.Hotel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class AdminDB {

    private LinkedList<Hotel> hotelsRegisteredForApproval=new LinkedList<>();
    private LinkedHashSet<Integer> priceUpdatedHotelList=new LinkedHashSet<>();
    private ArrayList<String> termsAndConditions=new ArrayList<>();
    private static AdminDB adminDB=new AdminDB();
    private AdminDB(){

    }

    public static AdminDB getInstance(){
        return adminDB;
    }

    public void registerHotel(Hotel hotel){
        hotelsRegisteredForApproval.add(hotel);
    }

    public LinkedList<Hotel> getHotelsRegisteredForApproval(){
        return hotelsRegisteredForApproval;
    }


    public void addPriceUpdatedHotelList(int id){
        priceUpdatedHotelList.add(id);
    }

    public LinkedHashSet<Integer> getPriceUpdatedHotelList(){
        return priceUpdatedHotelList;
    }

    public void removeHotelfromPriceUpdatedHotelList(int id){
        if(priceUpdatedHotelList.contains(id)){
            priceUpdatedHotelList.remove(id);
        }
    }

    public Hotel getHotelByPhoneNumber(long phoneNumber){

        for(Hotel hotel: hotelsRegisteredForApproval){
            if(hotel.getPhoneNumber()==phoneNumber){
                return hotel;
            }
        }
        return null;

    }

    public void addTermsAndConditions(ArrayList<String> termsAndConditions){
        this.termsAndConditions=termsAndConditions;
    }

    public ArrayList<String> getTermsAndConditions(){
        return this.termsAndConditions;
    }



}
