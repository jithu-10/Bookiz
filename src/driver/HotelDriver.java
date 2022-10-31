package driver;

import admin.AdminDB;
import booking.Booking;
import booking.BookingDB;
import customer.Customer;
import customer.CustomerDB;
import hotel.*;
import hotel.Address;
import user.User;
import user.UserAuthenticationDB;
import utility.InputHelper;
import utility.Printer;
import utility.Validator;

import java.util.ArrayList;
import java.util.Date;

public class HotelDriver implements Driver{

    static final HotelDriver hotelDriver=new HotelDriver();
    private static final UserAuthenticationDB userAuthenticationDB=UserAuthenticationDB.getInstance();
    private final AdminDB adminDB=AdminDB.getInstance();
    private final HotelDB hotelDB=HotelDB.getInstance();
    private final CustomerDB customerDB=CustomerDB.getInstance();
    private final AmenityDB amenityDB=AmenityDB.getInstance();
    private final BookingDB bookingDB=BookingDB.getInstance();
    private HotelDriver(){

    }

    static HotelDriver getInstance(){
        return hotelDriver;
    }

    @Override
    public void startDriver() {
        System.out.println(Printer.LOGIN_REGISTER);
        System.out.println(Printer.ENTER_INPUT);
        int choice = InputHelper.getInputWithinRange(2,null);

        switch (choice){
            case 1:
                System.out.println(Printer.SIGNED_IN);
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
                if(acceptTermsAndConditions()){
                    register();
                }

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
        String password= InputHelper.getStringInput();
        User user=null;
        if(userAuthenticationDB.authenticateHotel(phoneNumber,password)){
            user=hotelDB.getHotelByPhoneNumber(phoneNumber);
            if(user==null){
                user= adminDB.getHotelByPhoneNumber(phoneNumber);
            }
        }

        return user;
    }

    @Override
    public void menu(User user) {
        Hotel hotel=(Hotel)user;
        System.out.println(Printer.WELCOME+hotel.getHotelAdminName()+Printer.SMILE);
        do{
            System.out.println(Printer.HOTEL_MENU);
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(9,null);
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
                    showRoomsBookedNonBooked(hotel);
                    InputHelper.pressEnterToContinue();
                    break;
                case 6:
                    changeRoomPrices(hotel);
                    break;
                case 7:
                    bookedCustomersList(hotel);
                    break;
                case 8:
                    verifyCustomer(hotel);
                    break;
                case 9:
                    System.out.println(Printer.SIGNED_OUT);
                    return;
            }


        }while (true);
    }



    public void unApprovedHotel(){
        System.out.println(Printer.HOTEL_YET_TO_APPROVED);
    }


    //------------------------------------------------Hotel Registration----------------------------------------------//

    public boolean acceptTermsAndConditions(){
        if(adminDB.getTermsAndConditions().isEmpty()){
            return true;
        }
        InputHelper.printFile(adminDB.getTermsAndConditions());
        System.out.println(Printer.ACCEPT_DECLINE_OPTION);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            return true;
        }
        return false;
    }


    public void register(){
        Hotel hotel=hotelDetails();
        if(hotel==null){
            return;
        }
        roomDetails(hotel);
        addHotelAmenities(hotel);
        adminDB.registerHotel(hotel);
        System.out.println(Printer.HOTEL_REGISTERED);
    }

    public Hotel hotelDetails(){
        System.out.println(Printer.ENTER_HOTEL_ADMIN_NAME);
        String hotelAdminName=InputHelper.getStringInput();
        long phoneNumber;
        do{
            System.out.println(Printer.ENTER_PHONE_NUMBER);
            phoneNumber=InputHelper.getPhoneNumber();
            if(userAuthenticationDB.isHotelPhoneNumberExist(phoneNumber)){
                System.out.println(Printer.PHONE_NUMBER_ALREADY_EXIST);
                System.out.println("1."+Printer.TRY_AGAIN);
                System.out.println("2."+Printer.GO_BACK);
                System.out.println(Printer.ENTER_INPUT);
                int choice=InputHelper.getInputWithinRange(2,null);
                if(choice==2){
                    return null;
                }
            }
            else{
                break;
            }
        }while(true);

        String password,confirmPassword;
        while(true){
            System.out.println(Printer.ENTER_PASSWORD);
            password=InputHelper.getStringInput();
            System.out.println(Printer.CONFIRM_PASSWORD);
            confirmPassword=InputHelper.getStringInput();


            if(Validator.confirmPasswordValidatator(password,confirmPassword)){
                break;
            }
            System.out.println(Printer.PASSWORD_NOT_MATCH);
        }
        userAuthenticationDB.addHotelAuth(phoneNumber,password);
        System.out.println(Printer.ENTER_HOTEL_NAME);
        String hotelName=InputHelper.getStringInput();
        Address address=getHotelAddress();
        return new Hotel(hotelAdminName,phoneNumber,hotelName,address);
    }

    public Address getHotelAddress(){
        System.out.println(Printer.ENTER_BUILDING_NO);
        int buildingNo=InputHelper.getIntegerInput();
        System.out.println(Printer.ENTER_STREET);
        String street=InputHelper.getStringInput();
        System.out.println(Printer.ENTER_LOCALITY);
        String locality=InputHelper.getStringInput();
        System.out.println(Printer.ENTER_CITY);
        String city=InputHelper.getStringInput();
        System.out.println(Printer.ENTER_STATE);
        String state=InputHelper.getStringInput();
        System.out.println(Printer.ENTER_POSTAL_CODE);
        int postalCode=InputHelper.getPostalCode();
        return new Address(buildingNo,street,locality,city,state,postalCode);
    }

    public void roomDetails(Hotel hotel){

        System.out.println(Printer.ROOM_DETAILS);

        System.out.println(Printer.SINGLE_BED_COUNT);
        int singleBedCount=InputHelper.getWholeNumberIntegerInput();
        double basePrice=setBaseRoomPrice(null);
        double maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addSingleBedRooms(singleBedCount,basePrice,maxPrice);
        System.out.println(Printer.DOUBLE_BED_COUNT);
        int doubleBedCount=InputHelper.getWholeNumberIntegerInput();
        basePrice=setBaseRoomPrice(null);
        maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addDoubleBedRooms(doubleBedCount,basePrice,maxPrice);

        System.out.println(Printer.SUITE_ROOM_COUNT);
        int suiteRoomCount=InputHelper.getWholeNumberIntegerInput();
        basePrice=setBaseRoomPrice(null);
        maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addSuiteRooms(suiteRoomCount,basePrice,maxPrice);

    }



    //-------------------------------------------1.Add Rooms-----------------------------------------------------------//

    public void addRooms(Hotel hotel){
        System.out.println(Printer.ADD_ROOMS);
        System.out.println(Printer.ENTER_TYPE_OF_ROOM);
        System.out.println("1."+ RoomType.SINGLE_BED_ROOM);
        System.out.println("2."+RoomType.DOUBLE_BED_ROOM);
        System.out.println("3."+RoomType.SUITE_ROOM);
        System.out.println("4."+Printer.EXIT);
        System.out.println(Printer.ENTER_INPUT);
        int choice=InputHelper.getInputWithinRange(4,null);
        System.out.println(Printer.ENTER_NO_OF_ROOMS_TO_ADD);
        int count=InputHelper.getInputWithinRange(10,"Only 1 to 10 rooms can add at a time");

        switch (choice){
            case 1:
                hotel.addRooms(count,RoomType.SINGLE_BED_ROOM);
                break;
            case 2:
                hotel.addRooms(count,RoomType.DOUBLE_BED_ROOM);
                break;
            case 3:
                hotel.addRooms(count,RoomType.SUITE_ROOM);
                break;
            case 4:
                System.out.println(Printer.BACK_TO_MAIN);
                return;
        }
        System.out.println(Printer.ROOMS_ADDED_SUCCESSFULLY);
    }

    //-------------------------------------------2.Remove Rooms--------------------------------------------------------//

    public void removeRooms(Hotel hotel){
        System.out.println(Printer.REMOVE_ROOMS);
        System.out.println(Printer.ENTER_TYPE_OF_ROOM);
        System.out.println("1."+ RoomType.SINGLE_BED_ROOM);
        System.out.println("2."+RoomType.DOUBLE_BED_ROOM);
        System.out.println("3."+RoomType.SUITE_ROOM);
        System.out.println("4."+Printer.GO_BACK);
        System.out.println(Printer.ENTER_INPUT);
        int choice=InputHelper.getInputWithinRange(4,null);
        System.out.println(Printer.ENTER_NO_OF_ROOMS_TO_REMOVE);
        int count=0;
        switch (choice){
            case 1:
                count=InputHelper.getInputWithinRange(hotel.getNumberofSingleBedRooms(),"Only "+hotel.getNumberofSingleBedRooms()+" are available to remove");
                hotel.removeRooms(count,RoomType.SINGLE_BED_ROOM);
                break;
            case 2:
                count=InputHelper.getInputWithinRange(hotel.getNumberofDoubleBedRooms(),"Only "+hotel.getNumberofDoubleBedRooms()+" are available to remove");
                hotel.removeRooms(count,RoomType.DOUBLE_BED_ROOM);
                break;
            case 3:
                count=InputHelper.getInputWithinRange(hotel.getNumberofSuiteRooms(),"Only "+hotel.getNumberofSuiteRooms()+" are available to remove");
                hotel.removeRooms(count,RoomType.SUITE_ROOM);
                break;
            case 4:
                System.out.println(Printer.BACK_TO_MAIN);
                return;
        }
        System.out.println(Printer.ROOMS_REMOVED_SUCCESSFULLY);
    }

    //-----------------------------------------------3.Add Amenities---------------------------------------------------//

    void addHotelAmenities(Hotel hotel){

        ArrayList<Amenity>totalAmenities=amenityDB.getAmenities();
        ArrayList<Amenity>hotelAmenities=hotel.getAmenities();
        if(totalAmenities.size()==hotelAmenities.size()){
            System.out.println(Printer.ALL_AMENITY_ALREADY_ADDED);
            return;
        }
        System.out.println(Printer.ADD_HOTEL_AMENITIES);
        for(Amenity amenity: totalAmenities){
            if(!hotelAmenities.contains(amenity)){
                System.out.println(Printer.DOES_HOTEL_HAVE_QUESTION+amenity.getName()+" ?");
                System.out.println(Printer.YES_NO_OPTION);
                System.out.println(Printer.ENTER_INPUT);
                int choice = InputHelper.getInputWithinRange(2,null);
                if(choice==1){
                    hotel.addAmenity(amenity);
                }
            }

        }
    }

    //-----------------------------------------------4.Remove Amenities------------------------------------------------//

    void removeHotelAmenities(Hotel hotel){
        System.out.println(Printer.REMOVE_HOTEL_AMENITIES);
        ArrayList<Amenity>hotelAmenities=hotel.getAmenities();
        for(int i=0;i<hotelAmenities.size();i++){
            Amenity amenity=hotelAmenities.get(i);
            System.out.println((i+1)+" "+amenity.getName());
        }
        System.out.println(Printer.ENTER_SNO_TO_REMOVE_AMENITY);
        int value=InputHelper.getInputWithinRange(hotelAmenities.size(),null);
        hotel.removeAmenity(value-1);
    }

    //----------------------------------------5.Show Rooms which are booked and non booked-----------------------------//

    void showRoomsBookedNonBooked(Hotel hotel){
        System.out.println(Printer.ENTER_DATE);
        Date date=InputHelper.getDate();
        Date currentDate=InputHelper.setTime(new Date());
        if(currentDate.compareTo(date)==1){
            System.out.println(Printer.CANT_SHOW_BOOKED_ROOMS_BEFORE_CURRENT_DATE);
            return;
        }
        
        int noOfSingleBedRoomsBookedByDate=hotel.getNoOfSingleBedRoomsBookedByDate(date);
        int noOfDoubleBedRoomsBookedByDate=hotel.getNoOfDoubleBedRoomsBookedByDate(date);
        int noOfSuiteRoomsBookedByDate=hotel.getNoOfSuiteRoomsBookedByDate(date);

        System.out.println(Printer.BOOKED_UNBOOKED_OPTION+"\n");
        System.out.println(RoomType.SINGLE_BED_ROOM +"        "+noOfSingleBedRoomsBookedByDate+"        "+(hotel.getNumberofSingleBedRooms()-noOfSingleBedRoomsBookedByDate));
        System.out.println(RoomType.DOUBLE_BED_ROOM +"        "+noOfDoubleBedRoomsBookedByDate+"        "+(hotel.getNumberofDoubleBedRooms()-noOfDoubleBedRoomsBookedByDate));
        System.out.println(RoomType.SUITE_ROOM +"            "+noOfSuiteRoomsBookedByDate+"        "+(hotel.getNumberofSuiteRooms()-noOfSuiteRoomsBookedByDate));

    }

    //----------------------------------------------6.Change Price of Rooms--------------------------------------------//

    void changeRoomPrices(Hotel hotel){
        System.out.println(Printer.CHANGE_ROOM_PRICES);
        System.out.println(Printer.ENTER_TYPE_OF_ROOM);
        System.out.println("1."+ RoomType.SINGLE_BED_ROOM);
        System.out.println("2."+RoomType.DOUBLE_BED_ROOM);
        System.out.println("3."+RoomType.SUITE_ROOM);
        System.out.println("4."+Printer.GO_BACK);
        System.out.println(Printer.GO_BACK);
        int choice=InputHelper.getInputWithinRange(4,null);

        switch (choice){
            case 1:
                System.out.println(RoomType.SINGLE_BED_ROOM +" -> "+Printer.CURRENT_BASE_PRICE+hotel.getSingleBedRoomBasePrice()+Printer.CURRENT_MAX_PRICE+hotel.getSingleBedRoomMaxPrice()+Printer.CURRENT_LIST_PRICE+hotel.getSingleBedRoomListPrice());
                double newBaseRoomPrice=setBaseRoomPrice("New");
                double newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setSingleBedRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 2:
                System.out.println(RoomType.DOUBLE_BED_ROOM +" ->"+Printer.CURRENT_BASE_PRICE+hotel.getDoubleBedRoomBasePrice()+Printer.CURRENT_MAX_PRICE+hotel.getDoubleBedRoomMaxPrice()+Printer.CURRENT_LIST_PRICE+hotel.getDoubleBedRoomListPrice());
                newBaseRoomPrice=setBaseRoomPrice("New");
                newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setDoubleBedRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 3:
                System.out.println(RoomType.SUITE_ROOM +" ->"+Printer.CURRENT_BASE_PRICE+hotel.getSuiteRoomBasePrice()+Printer.CURRENT_MAX_PRICE+hotel.getSuiteRoomMaxPrice()+Printer.CURRENT_LIST_PRICE+hotel.getSuiteRoomListPrice());
                newBaseRoomPrice=setBaseRoomPrice("New");
                newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setSuiteRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 4:
                System.out.println(Printer.BACK_TO_MAIN);
                return;
        }
        System.out.println(Printer.ROOM_PRICE_CHANGED_SUCCESSFULLY);
        adminDB.addPriceUpdatedHotelList(hotel.getHotelID());
    }

    double setBaseRoomPrice(String str){
        System.out.println((str==null?"":str+" ")+Printer.BASE_PRICE);
        double basePrice=InputHelper.getDoubleInput();
        do{
            if(basePrice<1){
                System.out.println(Printer.BASE_PRICE_CONDITION);
                return setBaseRoomPrice(str);
            }
            else{
                return basePrice;
            }
        }while(true);

    }

    double setMaxRoomPrice(double basePrice,String str){
        System.out.println((str==null?"":str+" ")+Printer.MAX_PRICE);
        double maxPrice=InputHelper.getDoubleInput();
        do{
            if(maxPrice<=basePrice){
                System.out.println(Printer.MAX_PRICE_CONDITION+Printer.BASE_PRICE+basePrice);
                return setMaxRoomPrice(basePrice,str);
            }
            else{
                return maxPrice;
            }
        }while(true);
    }

    //----------------------------------------------7.List of Customers who booked rooms-------------------------------//

    void bookedCustomersList(Hotel hotel){
        ArrayList<Integer> bookingIDs=hotel.getBookingIDs();
        if(bookingIDs.isEmpty()){
            System.out.println(Printer.NO_CUSTOMER_BOOKED_ROOMS);
            InputHelper.pressEnterToContinue();
            return;
        }
        System.out.println();
        for(int i=0;i<bookingIDs.size();i++){
            Booking booking= bookingDB.getBookingWithID(bookingIDs.get(i));
            Customer customer= customerDB.getCustomerByID(booking.getCustomerID());
            customerDetails(i,booking,customer);
        }
        InputHelper.pressEnterToContinue();
    }

    void customerDetails(int sno,Booking booking,Customer customer){
        System.out.println((sno!=-1?((sno+1)+"."):"")+Printer.CUSTOMER_NAME+customer.getUserName());
        System.out.println(Printer.BOOKING_ID+booking.getBookingID());
        System.out.println(Printer.CHECK_IN_DATE+booking.getCheckInDateString());
        System.out.println(Printer.CHECK_OUT_DATE+booking.getCheckOutDateString());
        System.out.println(Printer.NO_OF_ROOMS_BOOKED);
        System.out.println("     1."+RoomType.SINGLE_BED_ROOM +" - "+booking.getNoOfSingleBedroomsNeeded());
        System.out.println("     2."+RoomType.DOUBLE_BED_ROOM +" - "+booking.getNoOfDoubleBedroomsNeeded());
        System.out.println("     3."+RoomType.SUITE_ROOM +" - "+booking.getNoOfSuiteRoomNeeded());
        System.out.println(Printer.PAID+(booking.getPaid()?Printer.YES:Printer.NO));
        System.out.println("\n");
    }

    //------------------------------------------------------Verify Customer--------------------------------------------//

    void verifyCustomer(Hotel hotel){
        ArrayList<Integer> bookingIDs=hotel.getBookingIDs();
        if(bookingIDs.isEmpty()){
            System.out.println(Printer.NO_CUSTOMER_BOOKED_ROOMS);
            InputHelper.pressEnterToContinue();
            return;
        }
        System.out.println(Printer.ENTER_BOOKING_ID);
        int bookingID=InputHelper.getIntegerInput();
        for(int i=0;i<bookingIDs.size();i++){
            if(bookingIDs.get(i)==bookingID){
                Booking booking=bookingDB.getBookingWithID(bookingID);
                Customer customer= customerDB.getCustomerByID(booking.getCustomerID());
                customerDetails(-1,booking,customer);
                return;
            }
        }

        System.out.println(Printer.NO_CUSTOMER_BOOKED_WITH_FOLLOWING_ID);

    }

}
