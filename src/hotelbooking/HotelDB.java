package hotelbooking;

import java.util.*;


public class HotelDB {
    private int idHelper=999999;
    private static final HotelDB HOTEL_DB =new HotelDB();
    private final AddressDB addressDB =AddressDB.getInstance();
    private final ArrayList<Hotel> hotelList =new ArrayList<>();
    private HotelDB(){}
    public static HotelDB getInstance(){
        return HOTEL_DB;
    }

    public void approveHotel(Hotel hotel){
        Helper.validateNull(hotel,"Hotel can't be null");
        hotel.setHotelApprovalStatus(HotelApprovalStatus.APPROVED);
        addressDB.addLocality(hotel.getAddress().getLocality());
        addressDB.addCity(hotel.getAddress().getCity());
    }

    public void rejectHotel(Hotel hotel){
        Helper.validateNull(hotel,"Hotel can't be null");
        if(hotel.getHotelApprovalStatus()== HotelApprovalStatus.ON_PROCESS) {
            hotel.setHotelApprovalStatus(HotelApprovalStatus.REJECTED);
        }
        else{
            hotel.setHotelApprovalStatus(HotelApprovalStatus.REMOVED);
        }
    }



    int generateID(){
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
        ArrayList<Integer> approvedHotels= getHotelListByStatus(HotelApprovalStatus.APPROVED);
        for(int i=0;i<approvedHotels.size();i++){
            Hotel hotel=getHotelByID(approvedHotels.get(i));
            if(hotelID==hotel.getHotelID()){
                addressDB.removeLocality(hotel.getAddress().getLocality());
                addressDB.removeCity(hotel.getAddress().getCity());
                hotel.setHotelApprovalStatus(HotelApprovalStatus.REMOVED);
                return true;
            }
        }
        return false;

    }

    public Hotel getHotelByUserID(int userID){
        for(Hotel hotel: hotelList){
            if(hotel.getHotelOwner().getUserID()==userID){
                return hotel;
            }
        }
        return null;
    }

    public HotelApprovalStatus getHotelStatusByUserID(int userID){
        for(Hotel hotel: hotelList){
            if(hotel.getHotelOwner().getUserID()==userID){
                return hotel.getHotelApprovalStatus();
            }
        }
        return null;
    }

    public void registerHotel(Hotel hotel){
        Helper.validateNull(hotel,"Hotel can't be null");
        hotelList.add(hotel);

    }


    public ArrayList<Integer> getHotelListByStatus(HotelApprovalStatus hotelApprovalStatus){
        Helper.validateNull(hotelApprovalStatus,"Hotel Approval Status can't be null");
        ArrayList<Integer> hotelsID=new ArrayList<>();
        for(Hotel hotel:hotelList){
            if(hotel.getHotelApprovalStatus()== hotelApprovalStatus){
                hotelsID.add(hotel.getHotelID());
            }
        }
        return hotelsID;
    }

    public ArrayList<Integer> getRequestedHotelList(){
        ArrayList<Integer> hotelsID=getHotelListByStatus(HotelApprovalStatus.ON_PROCESS);
        hotelsID.addAll(getHotelListByStatus(HotelApprovalStatus.REMOVED_RE_PROCESS));
        return hotelsID;
    }


    public LinkedHashMap<Integer,ArrayList<Integer>> filterHotels(String locality,Date checkInDate,Date checkOutDate,int totalNoOfRoomsNeeded,ArrayList<Integer> noOfGuestsInEachRoom){
        Helper.validateString(locality,"Locality can't be null");
        Helper.validateCheckInDate(checkInDate);
        Helper.validateCheckOutDate(checkInDate,checkOutDate);
        validateTotalRoomsNeeded(totalNoOfRoomsNeeded);
        Helper.validateNull(noOfGuestsInEachRoom,"Guests in each room can't be null");
        ArrayList<Date> datesInRange= Helper.getDatesBetweenTwoDates(checkInDate,checkOutDate);
        datesInRange.add(checkOutDate);
        ArrayList<Integer> hotelIDs= getHotelListByStatus(HotelApprovalStatus.APPROVED);
        LinkedHashMap<Integer,ArrayList<Integer>> hotelAndRoomsMap=new LinkedHashMap<>();

        for(int i=0;i<hotelIDs.size();i++){
            Hotel hotel=getHotelByID(hotelIDs.get(i));
            if(!Helper.modifyString(hotel.getAddress().getLocality()).equals(locality)&&!Helper.modifyString(hotel.getAddress().getCity()).equals(locality)){
                continue;
            }
            if(hotel.getTotalNumberOfRooms()<totalNoOfRoomsNeeded){
                continue;
            }

            ArrayList<Integer> filteredRooms= filterRooms(hotel,datesInRange,totalNoOfRoomsNeeded,noOfGuestsInEachRoom);

            if(filteredRooms==null){
                continue;
            }


            hotelAndRoomsMap.put(hotel.getHotelID(),filteredRooms);

        }

        return hotelAndRoomsMap;
    }




    private ArrayList<Integer> filterRooms( Hotel hotel, ArrayList<Date> datesInRange,int noOfRoomsNeeded,ArrayList<Integer> noOfGuestsInEachRoom){
        Collections.sort(noOfGuestsInEachRoom,Collections.reverseOrder());
        ArrayList<Room> rooms=hotel.getRooms();
        ArrayList<Integer> selectedRooms=new ArrayList<>();

        for(int i=0;i< noOfGuestsInEachRoom.size();i++){
            int noOfGuests=noOfGuestsInEachRoom.get(i);
            ArrayList<Integer> availRoomsIDs=new ArrayList<>();
            for(int j=0;j<rooms.size();j++){
                if(selectedRooms.contains(rooms.get(j).getId())){
                    continue;
                }
                if(noOfGuests>rooms.get(j).getRoomCapacity()){
                    continue;
                }
                if(!dateAvailabilityCheck(datesInRange,hotel,rooms.get(j))){
                    continue;
                }
                availRoomsIDs.add(rooms.get(j).getId());
            }
            if(availRoomsIDs.isEmpty()){
                return null;
            }
            int selectedRoomID=availRoomsIDs.get(0);
            for(int k=1;k<availRoomsIDs.size();k++){
                int previous=hotel.getRoomByID(selectedRoomID).getRoomCapacity()-noOfGuests;
                int current=hotel.getRoomByID(availRoomsIDs.get(k)).getRoomCapacity()-noOfGuests;
                if(current<previous){
                    selectedRoomID=availRoomsIDs.get(k);
                }
            }
            selectedRooms.add(selectedRoomID);
        }
        if(selectedRooms.size()==noOfRoomsNeeded){
            return selectedRooms;
        }
        return null;
    }





    private boolean dateAvailabilityCheck(ArrayList<Date> datesInRange,Hotel hotel,Room room){
        for(int i=0;i<datesInRange.size();i++){

            if(hotel.checkBookedByDate(room.getId(),datesInRange.get(i))){
                return false;
            }
        }
        return true;
    }

    private static void validateTotalRoomsNeeded(int totalRoomsNeeded){
        if(totalRoomsNeeded<0){
            throw new RuntimeException("Total rooms needed should be greater than 0");
        }
    }



}
