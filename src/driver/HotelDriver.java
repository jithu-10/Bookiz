package driver;

import admin.Admin;
import hotel.*;
import user.User;
import utility.InputHelper;
import utility.Printer;
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
                Hotel hotel;
                if((hotel=(Hotel) signIn())!=null){
                    if(hotel.isApproved()){
                        menu(hotel);
                    }
                    else{
                        unApprovedHotel();
                    }
                }
                else{
                    System.out.println(Printer.INVALID_CREDENTIALS);
                }
                break;
            case 2:
                register();
                break;

        }

    }

    //------------------------------------------------ Hotel Login ---------------------------------------------------//
    @Override
    public User signIn() {
        System.out.println(Printer.SIGN_IN);
        System.out.println(Printer.ENTER_PHONE_NUMBER);
        long phoneNumber= InputHelper.getPhoneNumber();
        System.out.println(Printer.ENTER_PASSWORD);
        String passWord= InputHelper.getStringInput();
        User user=HotelDB.checkAuthentication(phoneNumber,passWord);
        return user;
    }

    @Override
    public void menu(User user) {
        Hotel hotel=(Hotel)user;
        do{
            System.out.println("1.Add Rooms");
            System.out.println("2.Remove Rooms");
            System.out.println("3.Add Amenities");
            System.out.println("4.Remove Amenities");
            System.out.println("5.Show Rooms which are booked and non booked by Date");
            System.out.println("6.Change Room Prices");
            System.out.println("7.List of Customers who booked rooms in their hotel");
            System.out.println("8.Sign Out");
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getIntegerInput();
            switch (choice){
                case 1:
                    addRooms(hotel);
                    break;
                case 2:
                    removeRooms(hotel);
                    break;
                case 3:
                    addHotelAmenities(hotel);
                    break;
                case 4:
                    removeHotelAmenities(hotel);
                    break;
                case 5:
                    /*TODO Show Rooms which are booked and non booked by Date*/
                    break;
                case 6:
                    changeRoomPrices(hotel);
                    break;
                case 7:
                    /*TODO List of Customers who booked Rooms*/
                    break;
                case 8:
                    System.out.println("Signing Out...");
                    return;
                default:
                    System.out.println("Enter Input only from given option");
            }


        }while (true);
    }



    public void unApprovedHotel(){
        System.out.println("Your Hotel yet to approved by the App Admin....!");
    }


    //------------------------------------------------Hotel Registration----------------------------------------------//
    public void register(){
        Hotel hotel=hotelDetails();
        roomDetails(hotel);
        addHotelAmenities(hotel);
        HotelDB.registerHotel(hotel);
        System.out.println("Hotel Successfully Registered");
    }

    public Hotel hotelDetails(){
        System.out.println("Enter Hotel Admin Name : ");
        String hotelAdminName=InputHelper.getStringInput();
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
        return new Hotel(hotelAdminName,phoneNumber,password,hotelName,address,locality);
    }

    public void roomDetails(Hotel hotel){

        /*TODO Base Price should be lesser than Max Price */
        System.out.println("Room Details");

        System.out.println("Single Bed Count : ");
        int singleBedCount=InputHelper.getIntegerInput();
        double basePrice=setBaseRoomPrice(null);
        double maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addSingleBedRooms(singleBedCount,basePrice,maxPrice);

        System.out.println("Double Bed Count : ");
        int doubleBedCount=InputHelper.getIntegerInput();
        basePrice=setBaseRoomPrice(null);
        maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addDoubleBedRooms(doubleBedCount,basePrice,maxPrice);

        System.out.println("Suite Room Count : ");
        int suiteRoomCount=InputHelper.getIntegerInput();
        basePrice=setBaseRoomPrice(null);
        maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addSuiteRooms(suiteRoomCount,basePrice,maxPrice);

    }

    /*
    public void addHotelAmenities(Hotel hotel){
        System.out.println("Add Hotel Amenities");
        ArrayList<Amenity> amenities= AmenityDB.getAmenities();
        for(Amenity amenity: amenities){
            System.out.println("Does your hotel have "+amenity.getName()+" ?");
            System.out.println("1.YES");
            System.out.println("2.NO");
            System.out.println("Enter Input : ");
            int choice = InputHelper.getInputWithinRange(2,null);
            if(choice==1){
                hotel.addAmenity(amenity);
            }

        }
    }*/

    //-------------------------------------------1.Add Rooms-----------------------------------------------------------//

    public void addRooms(Hotel hotel){
        System.out.println("Add Rooms");
        System.out.println("Enter Type of Room : ");
        System.out.println("1."+ RoomType.SINGLEBEDROOM);
        System.out.println("2."+RoomType.DOUBLEBEDROOM);
        System.out.println("3."+RoomType.SUITEROOM);
        System.out.println("4.Go Back");
        System.out.println("Enter Input : ");
        int choice=InputHelper.getInputWithinRange(4,null);
        System.out.println("Enter No of Rooms to add : ");
        int count=InputHelper.getInputWithinRange(10,"Only 1 to 10 rooms can add at a time");

        switch (choice){
            case 1:
                hotel.addSingleBedRooms(count);
                break;
            case 2:
                hotel.addDoubleBedRooms(count);
                break;
            case 3:
                hotel.addSuiteRooms(count);
                break;
            case 4:
                System.out.println("Back to Main Menu");
                return;
        }
        System.out.println("Rooms Successfully Added");
    }

    //-------------------------------------------2.Remove Rooms--------------------------------------------------------//

    public void removeRooms(Hotel hotel){
        System.out.println("Remove Rooms");
        System.out.println("Enter Type of Room : ");
        System.out.println("1."+ RoomType.SINGLEBEDROOM);
        System.out.println("2."+RoomType.DOUBLEBEDROOM);
        System.out.println("3."+RoomType.SUITEROOM);
        System.out.println("4.Go Back");
        System.out.println("Enter Input : ");
        int choice=InputHelper.getInputWithinRange(4,null);
        System.out.println("Enter No of Rooms to remove : ");
        int count=0;
        /*TODO Only 0 available to remove Problem*/
        switch (choice){
            case 1:
                count=InputHelper.getInputWithinRange(hotel.getNumberofSingleBedRooms(),"Only "+hotel.getNumberofSingleBedRooms()+" are available to remove");
                hotel.removeRooms(count,RoomType.SINGLEBEDROOM);
                break;
            case 2:
                count=InputHelper.getInputWithinRange(hotel.getNumberofDoubleBedRooms(),"Only "+hotel.getNumberofDoubleBedRooms()+" are available to remove");
                hotel.removeRooms(count,RoomType.DOUBLEBEDROOM);
                break;
            case 3:
                count=InputHelper.getInputWithinRange(hotel.getNumberofSuiteRooms(),"Only "+hotel.getNumberofSuiteRooms()+" are available to remove");
                hotel.removeRooms(count,RoomType.SUITEROOM);
                break;
            case 4:
                System.out.println("Back to Main Menu");
                return;
        }
        System.out.println("Rooms Successfully Removed");
    }

    //-----------------------------------------------3.Add Amenities---------------------------------------------------//

    void addHotelAmenities(Hotel hotel){
        System.out.println("Add Hotel Amenities");
        ArrayList<Amenity>totalAmenities=AmenityDB.getAmenities();
        ArrayList<Amenity>hotelAmenities=hotel.getAmenities();
        for(Amenity amenity: totalAmenities){
            if(!hotelAmenities.contains(amenity)){
                System.out.println("Does your hotel have "+amenity.getName()+" ?");
                System.out.println("1.YES");
                System.out.println("2.NO");
                System.out.println("Enter Input : ");
                int choice = InputHelper.getInputWithinRange(2,null);
                if(choice==1){
                    hotel.addAmenity(amenity);
                }
            }

        }
    }

    //-----------------------------------------------4.Remove Amenities------------------------------------------------//

    void removeHotelAmenities(Hotel hotel){
        System.out.println("Remove Hotel Amenities");
        ArrayList<Amenity>hotelAmenities=hotel.getAmenities();
        for(int i=0;i<hotelAmenities.size();i++){
            Amenity amenity=hotelAmenities.get(i);
            System.out.println((i+1)+" "+amenity.getName());
        }
        System.out.println("Enter S.No to Remove Amenity");
        int value=InputHelper.getInputWithinRange(hotelAmenities.size(),null);
        hotel.removeAmenity(value-1);
    }

    //----------------------------------------5.Show Rooms which are booked and non booked-----------------------------//

    //----------------------------------------------6.Change Price of Rooms--------------------------------------------//

    void changeRoomPrices(Hotel hotel){
        /*TODO update to HOTEL DB about the updation of prices so app admin can change list according to that*/
        System.out.println("Change Room Prices");
        System.out.println("Enter Type of Room : ");
        System.out.println("1."+ RoomType.SINGLEBEDROOM);
        System.out.println("2."+RoomType.DOUBLEBEDROOM);
        System.out.println("3."+RoomType.SUITEROOM);
        System.out.println("4.Go Back");
        System.out.println("Enter Input : ");
        int choice=InputHelper.getInputWithinRange(4,null);

        switch (choice){
            case 1:
                System.out.println(RoomType.SINGLEBEDROOM+" ->Current Base Price : "+hotel.getSingleBedRoomBasePrice()+" Current Max Price : "+hotel.getSingleBedRoomMaxPrice()+" Current List Price : "+hotel.getSingleBedRoomListPrice());
                double newBaseRoomPrice=setBaseRoomPrice("New");
                double newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setSingleBedRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 2:
                System.out.println(RoomType.DOUBLEBEDROOM+" ->Current Base Price : "+hotel.getDoubleBedRoomBasePrice()+" Current Max Price : "+hotel.getDoubleBedRoomMaxPrice()+" Current List Price : "+hotel.getDoubleBedRoomListPrice());
                newBaseRoomPrice=setBaseRoomPrice("New");
                newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setDoubleBedRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 3:
                System.out.println(RoomType.SUITEROOM+" ->Current Base Price : "+hotel.getSuiteRoomBasePrice()+" Current Max Price : "+hotel.getSuiteRoomMaxPrice()+" Current List Price : "+hotel.getSuiteRoomListPrice());
                newBaseRoomPrice=setBaseRoomPrice("New");
                newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setSuiteRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 4:
                System.out.println("Back to Main Menu");
                return;
        }
        System.out.println("Room Prices Changed Successfully");
    }

    double setBaseRoomPrice(String str){
        System.out.println((str==null?"":str+" ")+"Base Price : ");
        double basePrice=InputHelper.getDoubleInput();
        return basePrice;
    }

    double setMaxRoomPrice(double basePrice,String str){
        System.out.println((str==null?"":str+" ")+"Max Price : ");
        double maxPrice=InputHelper.getDoubleInput();
        do{
            if(maxPrice<=basePrice){
                System.out.println("Max Price Should be Greater than Base Price. Base Price : "+basePrice);
                return setMaxRoomPrice(basePrice,str);
            }
            else{
                return maxPrice;
            }
        }while(true);

    }


}
