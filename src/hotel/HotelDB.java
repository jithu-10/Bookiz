package hotel;

import booking.CustomerBooking;
import utility.InputHelper;

import java.util.*;


public class HotelDB {
    private int idHelper=999999;
    private static HotelDB hotelDB=new HotelDB();
    private AddressDB addressDB =AddressDB.getInstance();
    private ArrayList<Hotel> hotelList =new ArrayList<>();
    private HotelDB(){}
    public static HotelDB getInstance(){
        return hotelDB;
    }

    public void approveHotel(Hotel hotel){
        hotel.setHotelApprovalStatus(HotelApprovalStatus.APPROVED);
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
            if(hotel.getHotelOwnerID()==userID){
                return hotel;
            }
        }
        return null;
    }

    public HotelApprovalStatus getHotelStatusByUserID(int userID){
        for(Hotel hotel: hotelList){
            if(hotel.getHotelOwnerID()==userID){
                return hotel.getHotelApprovalStatus();
            }
        }
        return null;
    }

    public void registerHotel(Hotel hotel){
        hotel.setHotelApprovalStatus(HotelApprovalStatus.ON_PROCESS);
        hotelList.add(hotel);
    }


    public ArrayList<Integer> getHotelListByStatus(HotelApprovalStatus hotelApprovalStatus){
        ArrayList<Integer> hotelsID=new ArrayList<>();
        for(Hotel hotel:hotelList){
            if(hotel.getHotelApprovalStatus()== hotelApprovalStatus){
                hotelsID.add(hotel.getHotelID());
            }
        }
        return hotelsID;
    }


    public LinkedHashMap<Integer,ArrayList<Integer>> filterHotels(CustomerBooking customerBooking, String locality){
        ArrayList<Date> datesInRange= InputHelper.getDatesBetweenTwoDates(customerBooking.getCheckInDate(),customerBooking.getCheckOutDate());
        datesInRange.add(customerBooking.getCheckOutDate());
        ArrayList<Integer> hotelIDs= getHotelListByStatus(HotelApprovalStatus.APPROVED);
        LinkedHashMap<Integer,ArrayList<Integer>> hotelRoomMap=new LinkedHashMap<>();

        for(int i=0;i<hotelIDs.size();i++){
            Hotel hotel=getHotelByID(hotelIDs.get(i));
            if(!InputHelper.modifyString(hotel.getAddress().getLocality()).equals(locality)&&!InputHelper.modifyString(hotel.getAddress().getCity()).equals(locality)){
                continue;
            }
            if(hotel.getTotalNumberOfRooms()<customerBooking.getTotalNoOfRoomsBooked()){
                continue;
            }

            ArrayList<Integer> filteredRooms=filterRooms3(customerBooking,hotel,datesInRange);

            if(filteredRooms==null){
                continue;
            }


            hotelRoomMap.put(hotel.getHotelID(),filteredRooms);

        }
        System.out.println(hotelRoomMap);

        return hotelRoomMap;
    }



    public ArrayList<Integer> filterRooms(CustomerBooking customerBooking, Hotel hotel,ArrayList<Date> datesInRange){

        int noOfRoomsNeeded=customerBooking.getTotalNoOfRoomsBooked();
        ArrayList<Integer> guests=customerBooking.getNoOfGuestsInEachRoom();
        Collections.sort(guests,Collections.reverseOrder());
        ArrayList<Room> rooms=hotel.getRooms();
        ArrayList<Integer> selectedRooms=new ArrayList<>();
        int availableRooms=0;
        for(int i=0;i<noOfRoomsNeeded;i++){
            int noOfGuests=guests.get(i);
            for(int j=0;j<rooms.size();j++){
                if(selectedRooms.contains(rooms.get(j).getId())){
                    continue;
                }
                if(noOfGuests>rooms.get(j).getRoomCapacity()){
                    continue;
                }
                if(!dateAvailabilityCheck(datesInRange,rooms.get(j))){
                    continue;
                }


                selectedRooms.add(rooms.get(j).getId());
                availableRooms++;
                break;
            }
        }

        if(availableRooms==noOfRoomsNeeded){
            return selectedRooms;
        }
        return null;

    }

    public ArrayList<Integer> filterRooms3(CustomerBooking customerBooking,Hotel hotel,ArrayList<Date> datesInRange){
        int noOfRoomsNeeded=customerBooking.getTotalNoOfRoomsBooked();
        ArrayList<Integer> guests=customerBooking.getNoOfGuestsInEachRoom();
        Collections.sort(guests,Collections.reverseOrder());
        ArrayList<Room> rooms=hotel.getRooms();
        ArrayList<Integer> selectedRooms=new ArrayList<>();

        for(int i=0;i< guests.size();i++){
            int noOfGuests=guests.get(i);
            ArrayList<Integer> availRoomsIDs=new ArrayList<>();
            for(int j=0;j<rooms.size();j++){
                if(selectedRooms.contains(rooms.get(j).getId())){
                    continue;
                }
                if(noOfGuests>rooms.get(j).getRoomCapacity()){
                    continue;
                }
                if(!dateAvailabilityCheck(datesInRange,rooms.get(j))){
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

    public boolean dateAvailabilityCheck(ArrayList<Date> datesInRange,Room room){
        for(int i=0;i<datesInRange.size();i++){
            if(room.checkBookedByDate(datesInRange.get(i))){
                return false;
            }
        }
        return true;
    }



}
