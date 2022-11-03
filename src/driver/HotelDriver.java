package driver;

import admin.AdminDB;
import booking.BookingDB;
import booking.CustomerBooking;
import hotel.*;
import hotel.Address;
import user.User;
import user.UserAuthenticationDB;
import user.UserDB;
import user.UserType;
import utility.InputHelper;
import utility.Printer;
import utility.Validator;

import java.util.ArrayList;
import java.util.Date;

public class HotelDriver implements Driver{

    static final HotelDriver hotelDriver=new HotelDriver();
    private static final UserAuthenticationDB userAuthenticationDB=UserAuthenticationDB.getInstance();
    private final AdminDB adminDB=AdminDB.getInstance();
    private final UserDB userDB= UserDB.getInstance();
    private final HotelDB hotelDB=HotelDB.getInstance();
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
                User hotelOwner;
                if((hotelOwner=signIn())!=null){
                    System.out.println(Printer.SIGNED_IN);
                    switch(hotelDB.getHotelStatusByUserID(hotelOwner.getUserID())){
                        case APPROVED:
                            menu(hotelOwner);
                            break;
                        case REJECTED:
                            rejectedHotelMenu(hotelOwner);
                            break;
                        case ON_PROCESS:
                            unApprovedHotelMenu(hotelOwner);
                            break;
                        case REMOVED:
                        case REMOVED_RE_PROCESS:
                            removedHotelMenu(hotelOwner);
                            break;

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
        User hotelOwner=null;
        if(userAuthenticationDB.authenticateHotelAdmin(phoneNumber,password)){
            hotelOwner=userDB.getUserByPhoneNumber(phoneNumber, UserType.HOTEL_OWNER);
        }
        return hotelOwner;
    }

    @Override
    public void menu(User user) {
        Hotel hotel=hotelDB.getHotelByUserID(user.getUserID());
        System.out.println(Printer.WELCOME+user.getUserName()+Printer.SMILE);
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


    public void unApprovedHotelMenu(User user){
        Hotel hotel=hotelDB.getHotelByUserID(user.getUserID());
        System.out.println(Printer.WELCOME+user.getUserName()+Printer.SMILE);
        System.out.println();
        System.out.println(Printer.HOTEL_YET_TO_APPROVED);
        System.out.println();
        do{
            System.out.println(Printer.UNAPPROVED_HOTEL_MENU);
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(7,null);
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
                    changeRoomPrices(hotel);
                    break;
                case 6:
                    changeHotelType(hotel);
                    break;
                case 7:
                    System.out.println(Printer.SIGNED_OUT);
                    return;
            }


        }while (true);
    }

    public void rejectedHotelMenu(User user){
        Hotel hotel=hotelDB.getHotelByUserID(user.getUserID());
        System.out.println(Printer.WELCOME+user.getUserName()+Printer.SMILE);
        System.out.println();
        System.out.println("Your Hotel has been rejected. ");
        System.out.println();
        do{
            System.out.println(Printer.REJECTED_HOTEL_MENU);
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(8,null);
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
                    changeRoomPrices(hotel);
                    break;
                case 6:
                    changeHotelType(hotel);
                    break;
                case 7:
                    registerAgain(hotel);
                    break;
                case 8:
                    System.out.println(Printer.SIGNED_OUT);
                    return;
            }


        }while (true);
    }

    public void removedHotelMenu(User user){
        Hotel hotel=hotelDB.getHotelByUserID(user.getUserID());
        System.out.println(Printer.WELCOME+user.getUserName()+Printer.SMILE);
        System.out.println();
        System.out.println("Your Hotel been removed from the app . Your Hotel Will not shown to customers until further update");
        System.out.println();
        do{
            System.out.println(Printer.REMOVED_HOTEL_MENU);
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(10,null);
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
                    registerAgain(hotel);
                    break;
                case 10:
                    System.out.println(Printer.SIGNED_OUT);
                    return;
            }


        }while (true);
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

    @Override
    public void register(){
        User hotelOwner=getHotelOwnerDetails();
        if(hotelOwner==null){
            return;
        }
        Hotel hotel=getHotelDetails(hotelOwner.getUserID());
        if(hotel==null){
            return;
        }
        roomDetails(hotel);
        addHotelAmenities(hotel);
        hotelTypeSpecification(hotel);
        userDB.addUser(hotelOwner);
        hotel.setHotelOwnerID(hotelOwner.getUserID());
        hotelDB.registerHotel(hotel);
        System.out.println(Printer.HOTEL_REGISTERED);
    }

    public void registerAgain(Hotel hotel){
        if(hotel.getHotelApproveStatus()==HotelStatus.REJECTED){
            hotel.setHotelApproveStatus(HotelStatus.ON_PROCESS);
        }
        else if(hotel.getHotelApproveStatus()==HotelStatus.REMOVED){
            hotel.setHotelApproveStatus(HotelStatus.REMOVED_RE_PROCESS);
        }
        System.out.println(Printer.HOTEL_REGISTERED_AGAIN);
    }

    public User getHotelOwnerDetails(){
        System.out.println(Printer.ENTER_HOTEL_ADMIN_NAME);
        String hotelOwnerName=InputHelper.getStringInput();
        System.out.println(Printer.ENTER_EMAIL);
        String mailID=InputHelper.getEmailInput();
        /*TODO Duplicate Mail ID Check*/
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
        User hotelOwner=new User();
        hotelOwner.setUserName(hotelOwnerName);
        hotelOwner.setPhoneNumber(phoneNumber);
        hotelOwner.setMailID(mailID);
        hotelOwner.setUserType(UserType.HOTEL_OWNER);
        userAuthenticationDB.addHotelAdminAuth(phoneNumber,password);
        return hotelOwner;
    }

    public Hotel getHotelDetails(int userID){
        System.out.println(Printer.ENTER_HOTEL_NAME);
        String hotelName=InputHelper.getStringInput();
        Address address=getHotelAddress();
        return new Hotel(userID,hotelName,address);
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

    public void changeHotelType(Hotel hotel){
        System.out.println("Hotel Type : "+hotel.getHotelType());
        System.out.println("1."+"Change Hotel Type");
        System.out.println("2."+"Back");
        if(InputHelper.getInputWithinRange(2,null)==1){
            hotelTypeSpecification(hotel);
            System.out.println("Hotel Type change requested successfully");
        }
    }

    public void hotelTypeSpecification(Hotel hotel){
        System.out.println("Select Hotel Type : ");
        System.out.println("1."+"ELITE HOTEL - "+HotelType.TOWNHOUSE);
        System.out.println("2."+"PREMIUM HOTEL - "+HotelType.COLLECTIONZ);
        System.out.println("3."+"STANDARD HOTEL - "+HotelType.SPOTZ);
        System.out.println("4."+"Skip");
        int choice=InputHelper.getInputWithinRange(4,null);
        switch (choice){
            case 1:
                hotel.setHotelType(HotelType.TOWNHOUSE);
                break;
            case 2:
                hotel.setHotelType(HotelType.COLLECTIONZ);
                break;
            case 3:
                hotel.setHotelType(HotelType.SPOTZ);
                break;
            case 4:
                if(hotel.getHotelType()==null){
                    hotel.setHotelType();
                }
                break;

        }
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
        if(choice==4){
            System.out.println(Printer.BACK_TO_MAIN);
            return;
        }
        System.out.println(Printer.ENTER_NO_OF_ROOMS_TO_ADD);
        int count=InputHelper.getInputWithinRange(10,Printer.ROOM_ADD_CONDITION);

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
        if(choice==4){
            System.out.println(Printer.BACK_TO_MAIN);
            return;
        }
        System.out.println(Printer.ENTER_NO_OF_ROOMS_TO_REMOVE);
        int count=0;
        switch (choice){
            case 1:
                count=InputHelper.getInputWithinRange(hotel.getTotalNumberOfRooms(RoomType.SINGLE_BED_ROOM),"Only "+hotel.getTotalNumberOfRooms(RoomType.SINGLE_BED_ROOM)+" are available to remove");
                hotel.removeRooms(count,RoomType.SINGLE_BED_ROOM);
                break;
            case 2:
                count=InputHelper.getInputWithinRange(hotel.getTotalNumberOfRooms(RoomType.DOUBLE_BED_ROOM),"Only "+hotel.getTotalNumberOfRooms(RoomType.DOUBLE_BED_ROOM)+" are available to remove");
                hotel.removeRooms(count,RoomType.DOUBLE_BED_ROOM);
                break;
            case 3:
                count=InputHelper.getInputWithinRange(hotel.getTotalNumberOfRooms(RoomType.SUITE_ROOM),"Only "+hotel.getTotalNumberOfRooms(RoomType.SUITE_ROOM)+" are available to remove");
                hotel.removeRooms(count,RoomType.SUITE_ROOM);
                break;
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
        hotel.removeAmenity(hotelAmenities.get(value-1));
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

        int noOfSingleBedRoomsBookedByDate=hotel.getNoOfRoomsBookedByDate(date,RoomType.SINGLE_BED_ROOM);
        int noOfDoubleBedRoomsBookedByDate=hotel.getNoOfRoomsBookedByDate(date,RoomType.DOUBLE_BED_ROOM);
        int noOfSuiteRoomsBookedByDate=hotel.getNoOfRoomsBookedByDate(date,RoomType.SUITE_ROOM);

        System.out.println(Printer.BOOKED_UNBOOKED_OPTION+"\n");
        System.out.println(RoomType.SINGLE_BED_ROOM +"        "+noOfSingleBedRoomsBookedByDate+"        "+(hotel.getTotalNumberOfRooms(RoomType.SINGLE_BED_ROOM)-noOfSingleBedRoomsBookedByDate));
        System.out.println(RoomType.DOUBLE_BED_ROOM +"        "+noOfDoubleBedRoomsBookedByDate+"        "+(hotel.getTotalNumberOfRooms(RoomType.DOUBLE_BED_ROOM)-noOfDoubleBedRoomsBookedByDate));
        System.out.println(RoomType.SUITE_ROOM +"            "+noOfSuiteRoomsBookedByDate+"        "+(hotel.getTotalNumberOfRooms(RoomType.SUITE_ROOM)-noOfSuiteRoomsBookedByDate));

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
        ArrayList<Integer> bookingIDs=bookingDB.getHotelBookingIDs(hotel.getHotelID());
        if(bookingIDs.isEmpty()){
            System.out.println(Printer.NO_CUSTOMER_BOOKED_ROOMS);
            InputHelper.pressEnterToContinue();
            return;
        }
        System.out.println();
        for(int i=0;i<bookingIDs.size();i++){
            CustomerBooking customerBooking= bookingDB.getCustomerBookingWithID(bookingIDs.get(i));
            User customer= userDB.getUserByID(customerBooking.getCustomerID(),UserType.CUSTOMER);
            customerDetails(i,customerBooking,customer);
        }
        InputHelper.pressEnterToContinue();
    }

    void customerDetails(int sno,CustomerBooking customerBooking,User customer){
        System.out.println((sno!=-1?((sno+1)+"."):"")+Printer.CUSTOMER_NAME+customer.getUserName());
        System.out.println(Printer.BOOKING_ID+customerBooking.getBookingID());
        System.out.println(Printer.CHECK_IN_DATE+customerBooking.getCheckInDateString());
        System.out.println(Printer.CHECK_OUT_DATE+customerBooking.getCheckOutDateString());
        System.out.println(Printer.NO_OF_ROOMS_BOOKED);
        System.out.println("     1."+RoomType.SINGLE_BED_ROOM +" - "+customerBooking.getNoOfSingleBedroomsBooked());
        System.out.println("     2."+RoomType.DOUBLE_BED_ROOM +" - "+customerBooking.getNoOfDoubleBedRoomsBooked());
        System.out.println("     3."+RoomType.SUITE_ROOM +" - "+customerBooking.getNoOfSuiteRoomsBooked());
        System.out.println(Printer.PAID+(customerBooking.getPaid()?Printer.YES:Printer.NO));
        System.out.println("\n");
    }

    //------------------------------------------------------Verify Customer--------------------------------------------//

    void verifyCustomer(Hotel hotel){
        ArrayList<Integer> bookingIDs=bookingDB.getHotelBookingIDs(hotel.getHotelID());
        if(bookingIDs.isEmpty()){
            System.out.println(Printer.NO_CUSTOMER_BOOKED_ROOMS);
            InputHelper.pressEnterToContinue();
            return;
        }
        System.out.println(Printer.ENTER_BOOKING_ID);
        int bookingID=InputHelper.getIntegerInput();
        for(int i=0;i<bookingIDs.size();i++){
            if(bookingIDs.get(i)==bookingID){
                CustomerBooking customerBooking=bookingDB.getCustomerBookingWithID(bookingID);
                User customer= userDB.getUserByID(customerBooking.getCustomerID(),UserType.CUSTOMER);
                customerDetails(-1,customerBooking,customer);
                return;
            }
        }

        System.out.println(Printer.NO_CUSTOMER_BOOKED_WITH_FOLLOWING_ID);

    }

}
