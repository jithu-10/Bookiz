package driver;

import hotel.Amenity;
import hotel.AmenityDB;
import hotel.Hotel;
import hotel.HotelDB;
import utility.InputHelper;
import utility.Validator;

import java.util.ArrayList;

public class HotelDriver implements Driver{

    static final HotelDriver hotelDriver=new HotelDriver();


    private HotelDriver(){

    }

    static HotelDriver getInstance(){
        return hotelDriver;
    }

    @Override
    public void startDriver() {
        System.out.println(" Hotel Driver ");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("Enter Input : ");
        int choice = InputHelper.getIntegerInput();

        switch (choice){
            case 1:
                signIn();
                break;
            case 2:
                register();
                break;

        }

    }

    //------------------------------------------------ Hotel Login ---------------------------------------------------//
    @Override
    public boolean signIn() {

        return false;
    }

    @Override
    public void menu() {

    }


    //------------------------------------------------Hotel Registration----------------------------------------------//
    public void register(){
        Hotel hotel=hotelDetails();
        roomDetails(hotel);
        addHotelAmenities(hotel);
        HotelDB.registerHotel(hotel);
    }

    public Hotel hotelDetails(){
        System.out.println("Enter Phone Number : ");
        long phoneNumber=InputHelper.getPhoneNumber();
        String password,confirmPassword;
        while(true){
            System.out.println("Enter Password : ");
            password=InputHelper.getStringInput();
            System.out.println("Confirm Password : ");
            confirmPassword=InputHelper.getStringInput();


            if(Validator.confirmPasswordValidatator(password,confirmPassword)){
                break;
            }
            System.out.println("Password Not Matching Try Again ");
        }

        System.out.println("Hotel Name : ");
        String hotelName=InputHelper.getStringInput();
        System.out.println("Address : ");
        String address=InputHelper.getStringInput();
        System.out.println("Locality (City) : ");
        String locality=InputHelper.getStringInput();
        return new Hotel(phoneNumber,password,hotelName,address,locality);
    }

    public void roomDetails(Hotel hotel){

        /*TODO Base Price should be lesser than Max Price */
        System.out.println("Room Details");

        System.out.println("Single Bed Count : ");
        int singleBedCount=InputHelper.getIntegerInput();
        System.out.println("Base Price : ");
        double basePrice=InputHelper.getDoubleInput();
        System.out.println("Max Price : ");
        double maxPrice=InputHelper.getDoubleInput();
        hotel.addSingleBedRooms(singleBedCount,basePrice,maxPrice);


        System.out.println("Double Bed Count : ");
        int doubleBedCount=InputHelper.getIntegerInput();
        System.out.println("Base Price : ");
        basePrice=InputHelper.getDoubleInput();
        System.out.println("Max Price : ");
        maxPrice=InputHelper.getDoubleInput();
        hotel.addDoubleBedRooms(doubleBedCount,basePrice,maxPrice);

        System.out.println("Suite Room Count : ");
        int suiteRoomCount=InputHelper.getIntegerInput();
        System.out.println("Base Price : ");
        basePrice=InputHelper.getDoubleInput();
        System.out.println("Max Price : ");
        maxPrice=InputHelper.getDoubleInput();
        hotel.addSuiteRooms(suiteRoomCount,basePrice,maxPrice);

    }

    public void addHotelAmenities(Hotel hotel){
        System.out.println("Add Hotel Amenities");
        ArrayList<Amenity> amenities= AmenityDB.getAmenities();
        for(Amenity amenity: amenities){
            System.out.println("Does your hotel have "+amenity.getName()+" ?");
            System.out.println("1.YES");
            System.out.println("2.NO");
            System.out.println("Enter Input : ");
            int choice = InputHelper.getIntegerInput();

            /*TODO - make getIntegerInput into getTwoOptions in Input Helper
            *  Which will be helpful to make get only 2 inputs and handles when
            *  user inputs greater or lesser than that */

            if(choice==1){
                hotel.addAmenity(amenity);
            }

        }
    }
}
