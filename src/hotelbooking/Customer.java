package hotelbooking;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Customer extends User {

    private final LinkedHashSet<Integer> favoriteHotelList =new LinkedHashSet<>();
    private final ArrayList<CustomerBooking> bookings = new ArrayList<>();
    private final ArrayList<CustomerBooking> cancelledBookings=new ArrayList<>();



    public Customer(String userName,long phoneNumber,String mailID){
        super(userName,phoneNumber,mailID);
    }


    public void addFavoriteHotels(Hotel hotel){
        Helper.validateNull(hotel,"Hotel can't be null");
        favoriteHotelList.add(hotel.getHotelID());
    }



    public void removeFavoriteHotels(Hotel hotel){
        Helper.validateNull(hotel,"Hotel can't be null");
        favoriteHotelList.remove(hotel.getHotelID());
    }

    void addBookings(CustomerBooking booking){
        Helper.validateNull(booking,"Booking can't be null");
        bookings.add(booking);
    }

    public void removeBookings(CustomerBooking booking){
        Helper.validateNull(booking,"Booking can't be null");
        bookings.remove(booking);
    }

    public void addCancelledBookings(CustomerBooking booking){
        Helper.validateNull(booking,"Booking can't be null");
        cancelledBookings.add(booking);
    }

    public ArrayList<CustomerBooking> getBookings() {
        return bookings;
    }

    public ArrayList<CustomerBooking> getCancelledBookings() {
        return cancelledBookings;
    }

    public ArrayList<Hotel> getFavoriteHotelList(){
        ArrayList<Hotel> favoriteHotels=new ArrayList<>();
        ArrayList<Integer> favoriteHotelsIDs=new ArrayList<>(favoriteHotelList);
        for(int i=0;i< favoriteHotelList.size();i++){
            Hotel hotel=HotelDB.getInstance().getHotelByID(favoriteHotelsIDs.get(i));
            if(hotel.getHotelApprovalStatus()== HotelApprovalStatus.APPROVED){
                favoriteHotels.add(hotel);
            }
        }
        return favoriteHotels;
    }



}
