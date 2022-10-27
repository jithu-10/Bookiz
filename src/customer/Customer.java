package customer;

import user.User;

import java.util.ArrayList;


public class Customer extends User {

    private ArrayList<Integer> favoriteHotels=new ArrayList<>();
    private ArrayList<Integer> bookingIDs=new ArrayList<>();
    public Customer(String userName,long phoneNumber,String mailID){
        setUserName(userName);
        setMailID(mailID);
        setPhoneNumber(phoneNumber);
    }

    public void addFavoriteHotels(int ID){
        favoriteHotels.add(ID);
    }

    public void removeFavoriteHotels(int ID){
        favoriteHotels.remove(Integer.valueOf(ID));
    }

    public ArrayList<Integer> getFavoriteHotels(){
        return favoriteHotels;
    }

    public void addBookingIDs(int ID){
        bookingIDs.add(ID);
    }
    public void removeBookingIDs(int ID){
        Integer id=ID;
        bookingIDs.remove(id);
    }

    public ArrayList<Integer> getBookingIDs(){
        return bookingIDs;
    }

    public void setCustomerID(int customerID){
        setUserID(customerID);
    }

    public int getCustomerID(){
        return getUserID();
    }



    public String getUserName(){
        return super.getUserName();
    }

    public long getPhoneNumber(){
        return super.getPhoneNumber();
    }

    public String getMailID(){
        return super.getMailID();
    }

}
