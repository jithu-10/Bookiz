package customer;

import user.User;

import java.util.ArrayList;


public class Customer extends User {

    private String fullName;
    private long phoneNumber;
    private int customerID;
    private String mailID;
    private String password;
    private ArrayList<Integer> favoriteHotels=new ArrayList<>();
    private ArrayList<Integer> bookingIDs=new ArrayList<>();
    public Customer(String fullName,long phoneNumber,String mailID,String password){
        this.fullName=fullName;
        this.phoneNumber=phoneNumber;
        this.mailID=mailID;
        this.password=password;
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
        this.customerID=customerID;
    }

    public int getCustomerID(){
        return customerID;
    }
    public String getFullName(){
        return fullName;
    }

    public long getPhoneNumber(){
        return phoneNumber;
    }

    public String getPassword(){
        return password;
    }

}
