package hotel;

import booking.CustomerBooking;
import utility.InputHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class HotelDB {
    private int idHelper=999999;
    private static HotelDB hotelDB=new HotelDB();
    private AddressDB addressDB =AddressDB.getInstance();
    private ArrayList<Hotel> hotelList =new ArrayList<>();
    private HashMap<Integer,ArrayList<Integer>> favoriteHotels=new HashMap<>();

    private HotelDB(){}
    public static HotelDB getInstance(){
        return hotelDB;
    }

    public void approveHotel(Hotel hotel){
        hotel.setHotelApproveStatus(HotelStatus.APPROVED);
        if(hotel.getHotelID()==0){
            hotel.setHotelId(generateID());
        }
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
        ArrayList<Hotel> approvedHotels=getHotelListByStatus(HotelStatus.APPROVED);
        for(int i=0;i<approvedHotels.size();i++){
            Hotel hotel=approvedHotels.get(i);
            if(hotelID==hotel.getHotelID()){
                addressDB.removeLocality(hotel.getAddress().getLocality());
                addressDB.removeCity(hotel.getAddress().getCity());
                hotel.setHotelApproveStatus(HotelStatus.REMOVED);
                return true;
            }
        }
        return false;

    }


    public Hotel getHotelByUserID(int userID){
        for(Hotel hotel: hotelList){
            if(hotel.getHotelOwnerID()==userID){
                return hotel;
            }
        }
        return null;
    }

    public HotelStatus getHotelStatusByUserID(int userID){
        for(Hotel hotel: hotelList){
            if(hotel.getHotelOwnerID()==userID){
                return hotel.getHotelApproveStatus();
            }
        }
        return null;
    }

    public void registerHotel(Hotel hotel){
        hotel.setHotelApproveStatus(HotelStatus.ON_PROCESS);
        hotelList.add(hotel);
    }


    /*TODO Try to change to ArrayList<Integer>*/
    public ArrayList<Hotel> getHotelListByStatus(HotelStatus hotelStatus){
        ArrayList<Hotel> hotels=new ArrayList<>();
        for(Hotel hotel: hotelList){
            if(hotel.getHotelApproveStatus()==hotelStatus){
                hotels.add(hotel);
            }
        }
        return hotels;
    }




    public void addFavoriteHotels(int userID,int hotelID){
        if(favoriteHotels.containsKey(userID)){
            favoriteHotels.get(userID).add(hotelID);
        }
        else{
            ArrayList<Integer> hotelIDs=new ArrayList<>();
            hotelIDs.add(hotelID);
            favoriteHotels.put(userID,hotelIDs);
        }
    }

    public void removeFavoriteHotels(int userID,int hotelID){
        if(favoriteHotels.containsKey(userID)){
            favoriteHotels.get(userID).remove(Integer.valueOf(hotelID));
        }
    }

    public ArrayList<Integer> getFavoriteHotels(int userID){
        if(favoriteHotels.containsKey(userID)){
            return favoriteHotels.get(userID);
        }
        return new ArrayList<>();
    }



    public ArrayList<Integer> filterHotels(CustomerBooking customerBooking,String locality){
        ArrayList<Date> datesInRange= InputHelper.getDatesBetweenTwoDates(customerBooking.getCheckInDate(),customerBooking.getCheckOutDate());
        datesInRange.add(customerBooking.getCheckOutDate());
        ArrayList<Hotel> hotels=hotelDB.getHotelListByStatus(HotelStatus.APPROVED);
        ArrayList<Integer> filteredHotelsID=new ArrayList<>();
        loop:for(int i=0;i<hotels.size();i++){
            Hotel hotel=hotels.get(i);
            if(!InputHelper.modifyString(hotel.getAddress().getLocality()).equals(locality)&&!InputHelper.modifyString(hotel.getAddress().getCity()).equals(locality)){
                continue;
            }

            for(int j=0;j<datesInRange.size();j++){

                int noOfRemSingleBedRoomsBookedByDate=hotel.getTotalNumberOfRooms(RoomType.SINGLE_BED_ROOM)-hotel.getNoOfRoomsBookedByDate(datesInRange.get(j),RoomType.SINGLE_BED_ROOM);
                int noOfRemDoubleBedRoomsBookedByDate=hotel.getTotalNumberOfRooms(RoomType.DOUBLE_BED_ROOM)-hotel.getNoOfRoomsBookedByDate(datesInRange.get(j),RoomType.DOUBLE_BED_ROOM);
                int noOfRemSuiteRoomsBookedByDate=hotel.getTotalNumberOfRooms(RoomType.SUITE_ROOM)-hotel.getNoOfRoomsBookedByDate(datesInRange.get(j),RoomType.SUITE_ROOM);
                int totalNoOfRemRoomsBookedByDate=noOfRemSuiteRoomsBookedByDate+noOfRemDoubleBedRoomsBookedByDate+noOfRemSingleBedRoomsBookedByDate;
                if(customerBooking.getTotalNoOfRoomsBooked()>totalNoOfRemRoomsBookedByDate){
                    continue loop;
                }
                else if(customerBooking.getNoOfSingleBedroomsBooked()>noOfRemSingleBedRoomsBookedByDate){
                    continue loop;
                }
                else if(customerBooking.getNoOfDoubleBedRoomsBooked()>noOfRemDoubleBedRoomsBookedByDate){
                    continue loop;
                }
                else if(customerBooking.getNoOfSuiteRoomsBooked()>noOfRemSuiteRoomsBookedByDate){
                    continue loop;
                }

            }
            filteredHotelsID.add(hotel.getHotelID());

        }
        return filteredHotelsID;

    }


}
