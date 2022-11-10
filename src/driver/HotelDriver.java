package driver;

import admin.AdminDB;
import booking.BookingDB;
import booking.CustomerBooking;
import customer.Customer;
import hotel.*;
import hotel.Address;
import user.User;
import user.UserAuthenticationDB;
import user.UserDB;
import utility.InputHelper;
import utility.PrintStatements;
import utility.Validator;

import java.util.*;

public class HotelDriver extends AbstractDriver{

    static final HotelDriver hotelDriver=new HotelDriver();
    private static final UserAuthenticationDB userAuthenticationDB=UserAuthenticationDB.getInstance();
    private final UserDB userDB= UserDB.getInstance();
    private final AdminDB adminDB=AdminDB.getInstance();
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
        System.out.println(PrintStatements.LOGIN_REGISTER);
        System.out.println(PrintStatements.ENTER_INPUT);
        int choice = InputHelper.getInputWithinRange(2,null);

        switch (choice){
            case 1:
                User hotelOwner;
                if((hotelOwner=signIn())!=null){
                    System.out.println(PrintStatements.SIGNED_IN);
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
                    System.out.println(PrintStatements.INVALID_CREDENTIALS);
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
        System.out.println(PrintStatements.SIGN_IN);
        System.out.println("Enter Phone Number or Email ");
        Object mail_or_phone=InputHelper.getPhoneNumberOrEmail();
        System.out.println(PrintStatements.ENTER_PASSWORD);
        String password= InputHelper.getStringInput();
        User hotelOwner=null;
        if(userAuthenticationDB.authenticateHotelAdmin(mail_or_phone,password)){
            hotelOwner=userDB.getHotelAdminByPhoneNumber_Mail(mail_or_phone);
        }
        return hotelOwner;
    }

    @Override
    public void menu(User user) {
        Hotel hotel=hotelDB.getHotelByUserID(user.getUserID());
        System.out.println(PrintStatements.WELCOME+user.getUserName()+ PrintStatements.SMILE);
        do{
            System.out.println(PrintStatements.HOTEL_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
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
                    System.out.println(PrintStatements.SIGNED_OUT);
                    return;
            }


        }while (true);
    }


    private void unApprovedHotelMenu(User user){
        Hotel hotel=hotelDB.getHotelByUserID(user.getUserID());
        System.out.println(PrintStatements.WELCOME+user.getUserName()+ PrintStatements.SMILE);
        System.out.println();
        System.out.println(PrintStatements.HOTEL_YET_TO_APPROVED);
        System.out.println();
        do{
            System.out.println(PrintStatements.UNAPPROVED_HOTEL_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
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
                    System.out.println(PrintStatements.SIGNED_OUT);
                    return;
            }


        }while (true);
    }

    private void rejectedHotelMenu(User user){
        Hotel hotel=hotelDB.getHotelByUserID(user.getUserID());
        System.out.println(PrintStatements.WELCOME+user.getUserName()+ PrintStatements.SMILE);
        System.out.println();
        System.out.println("Your Hotel has been rejected. ");
        System.out.println();
        do{
            System.out.println(PrintStatements.REJECTED_HOTEL_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
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
                    System.out.println(PrintStatements.SIGNED_OUT);
                    return;
            }


        }while (true);
    }

    private void removedHotelMenu(User user){
        Hotel hotel=hotelDB.getHotelByUserID(user.getUserID());
        System.out.println(PrintStatements.WELCOME+user.getUserName()+ PrintStatements.SMILE);
        System.out.println();
        System.out.println("Your Hotel been removed from the app . Your Hotel Will not shown to customers until further update");
        System.out.println();
        do{
            System.out.println(PrintStatements.REMOVED_HOTEL_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
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
                    System.out.println(PrintStatements.SIGNED_OUT);
                    return;
            }


        }while (true);
    }


    //------------------------------------------------Hotel Registration----------------------------------------------//


    @Override
    public void register(){
        User hotelOwner= getUserDetails();
        if(hotelOwner==null){
            return;
        }
        Hotel hotel=getHotelDetails(hotelOwner.getUserID());

        addRooms(hotel);
        addHotelAmenities(hotel);
        hotelTypeSpecification(hotel);
        userDB.addHotelAdmin(hotelOwner);
        hotel.setHotelOwnerID(hotelOwner.getUserID());
        hotelDB.registerHotel(hotel);
        System.out.println(PrintStatements.HOTEL_REGISTERED);
    }

    public User getUserDetails(){
        System.out.println(PrintStatements.ENTER_FULL_NAME);
        String fullName=InputHelper.getStringInput();
        String email=getEmail();
        if(email==null){
            return null;
        }
        long phoneNumber=getPhoneNumber();
        if(phoneNumber==-1){
            return null;
        }

        String password,confirmPassword;
        while(true){
            System.out.println(PrintStatements.ENTER_PASSWORD);
            password=InputHelper.getStringInput();
            System.out.println(PrintStatements.CONFIRM_PASSWORD);
            confirmPassword=InputHelper.getStringInput();

            if(Validator.confirmPasswordValidatator(password,confirmPassword)){
                break;
            }
            System.out.println(PrintStatements.PASSWORD_NOT_MATCH);
        }

        User user=new Customer(fullName);
        user.setPhoneNumber(phoneNumber);
        user.setMailID(email);
        userAuthenticationDB.addHotelAdminAuth(phoneNumber,email,password);
        return user;
    }

    private String getEmail(){
        String email;
        do{
            System.out.println(PrintStatements.ENTER_EMAIL);
            email=InputHelper.getEmailInput();

            if(userAuthenticationDB.isHotelEmailExist(email)){
                System.out.println("E-Mail already exist");
                System.out.println("1."+ PrintStatements.TRY_AGAIN);
                System.out.println("2."+ PrintStatements.GO_BACK);
                System.out.println(PrintStatements.ENTER_INPUT);
                int choice=InputHelper.getInputWithinRange(2,null);
                if(choice==2){
                    return null;
                }
            }
            else{
                break;
            }

        }while(true);
        return email;
    }

    private long getPhoneNumber(){
        long phoneNumber;
        do{
            System.out.println(PrintStatements.ENTER_PHONE_NUMBER);
            phoneNumber=InputHelper.getPhoneNumber();
            if((userAuthenticationDB.isHotelPhoneNumberExist(phoneNumber))){
                System.out.println(PrintStatements.PHONE_NUMBER_ALREADY_EXIST);
                System.out.println("1."+ PrintStatements.TRY_AGAIN);
                System.out.println("2."+ PrintStatements.GO_BACK);
                System.out.println(PrintStatements.ENTER_INPUT);
                int choice=InputHelper.getInputWithinRange(2,null);
                if(choice==2){
                    return -1;
                }
            }
            else{
                break;
            }
        }while(true);
        return phoneNumber;
    }

    private void registerAgain(Hotel hotel){
        if(hotel.getHotelApprovalStatus()== HotelApprovalStatus.REJECTED){
            hotel.setHotelApprovalStatus(HotelApprovalStatus.ON_PROCESS);
        }
        else if(hotel.getHotelApprovalStatus()== HotelApprovalStatus.REMOVED){
            hotel.setHotelApprovalStatus(HotelApprovalStatus.REMOVED_RE_PROCESS);
        }
        System.out.println(PrintStatements.HOTEL_REGISTERED_AGAIN);
    }


    private Hotel getHotelDetails(int userID){
        System.out.println(PrintStatements.ENTER_HOTEL_NAME);
        String hotelName=InputHelper.getStringInput();
        Address address=getHotelAddress();
        return new Hotel(userID,hotelName,address);
    }

    private Address getHotelAddress(){
        System.out.println(PrintStatements.ENTER_BUILDING_NO);
        int buildingNo=InputHelper.getIntegerInput();
        System.out.println(PrintStatements.ENTER_STREET);
        String street=InputHelper.getStringInput();
        System.out.println(PrintStatements.ENTER_LOCALITY);
        String locality=InputHelper.getStringInput();
        System.out.println(PrintStatements.ENTER_CITY);
        String city=InputHelper.getStringInput();
        System.out.println(PrintStatements.ENTER_STATE);
        String state=InputHelper.getStringInput();
        System.out.println(PrintStatements.ENTER_POSTAL_CODE);
        int postalCode=InputHelper.getPostalCode();
        return new Address(buildingNo,street,locality,city,state,postalCode);
    }

    private void changeHotelType(Hotel hotel){
        System.out.println("Hotel Type : "+hotel.getHotelType());
        System.out.println("1."+"Change Hotel Type");
        System.out.println("2."+"Back");
        if(InputHelper.getInputWithinRange(2,null)==1){
            hotelTypeSpecification(hotel);
            System.out.println("Hotel Type change requested successfully");
        }
    }

    private void hotelTypeSpecification(Hotel hotel){
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


    private void addRooms(Hotel hotel){
        System.out.println("Enter No of Types of Rooms : ");
        int types=InputHelper.getPositiveInput();
        for(int i=0;i<types;i++){
            System.out.println("Type : "+(i+1));
            System.out.println("Enter Number of Rooms for Type "+(i+1));
            int noOfRooms=InputHelper.getPositiveInput();
            System.out.println("Enter Max Guest can accommodate in this Room : ");
            int maxGuest=InputHelper.getPositiveInput();
            System.out.println("Enter Base Room Rent (Without Bed Price) : ");
            double baseRoomPrice= setBasePrice(100,null);
            System.out.println("Enter Max Room Rent (Without Bed Price) : ");
            double maxRoomPrice= setMaxPrice(baseRoomPrice,null);
            System.out.println("Enter Bed Price : ");
            double bedPrice=InputHelper.getDoubleInputWithinRange(1,(int)InputHelper.findParts(30,baseRoomPrice),"Bed price should be less than 30% of Base Room Price");
            hotel.addRooms(noOfRooms,maxGuest,baseRoomPrice,maxRoomPrice,bedPrice);

        }
    }



    //-------------------------------------------2.Remove Rooms--------------------------------------------------------//

    private void displayRooms(ArrayList<Room> rooms){
        for(int i=0;i<rooms.size();i++){
            Room room=rooms.get(i);
            System.out.println((i+1)+".Room Max Capacity : "+room.getRoomCapacity());
            System.out.println("Room Base Price : "+room.getRoomBasePrice()+"  Room Max Price : "+room.getRoomMaxPrice());
            System.out.println();
        }
    }

    private void removeRooms(Hotel hotel){
        System.out.println(PrintStatements.REMOVE_ROOMS);
        ArrayList<Room> rooms=hotel.getRooms();
        if(rooms.isEmpty()){
            System.out.println("No rooms available");
            return;
        }
        displayRooms(rooms);
        System.out.println("Enter S.No to Remove Room");
        int choice=InputHelper.getInputWithinRange(1,rooms.size(),null);
        hotel.removeRooms(rooms.get(choice-1));
    }

    //-----------------------------------------------3.Add Amenities---------------------------------------------------//

    private void addHotelAmenities(Hotel hotel){

        ArrayList<Amenity>totalAmenities=amenityDB.getAmenities();
        ArrayList<Amenity>hotelAmenities=hotel.getAmenities();
        if(totalAmenities.size()==hotelAmenities.size()){
            System.out.println(PrintStatements.ALL_AMENITY_ALREADY_ADDED);
            return;
        }
        System.out.println(PrintStatements.ADD_HOTEL_AMENITIES);
        for(Amenity amenity: totalAmenities){
            if(!hotelAmenities.contains(amenity)){
                System.out.println(PrintStatements.DOES_HOTEL_HAVE_QUESTION+amenity.getName()+" ?");
                System.out.println(PrintStatements.YES_NO_OPTION);
                System.out.println(PrintStatements.ENTER_INPUT);
                int choice = InputHelper.getInputWithinRange(2,null);
                if(choice==1){
                    hotel.addAmenity(amenity);
                }
            }

        }
    }

    //-----------------------------------------------4.Remove Amenities------------------------------------------------//

    private void removeHotelAmenities(Hotel hotel){
        System.out.println(PrintStatements.REMOVE_HOTEL_AMENITIES);
        ArrayList<Amenity>hotelAmenities=hotel.getAmenities();
        for(int i=0;i<hotelAmenities.size();i++){
            Amenity amenity=hotelAmenities.get(i);
            System.out.println((i+1)+" "+amenity.getName());
        }
        System.out.println(PrintStatements.ENTER_SNO_TO_REMOVE_AMENITY);
        int value=InputHelper.getInputWithinRange(hotelAmenities.size(),null);
        hotel.removeAmenity(hotelAmenities.get(value-1));
    }

    //----------------------------------------5.Show Rooms which are booked and non booked-----------------------------//

    private void showRoomsBookedNonBooked(Hotel hotel){
        System.out.println(PrintStatements.ENTER_DATE);
        Date date=InputHelper.getDate();
        Date currentDate=InputHelper.setTime(new Date());
        if(currentDate.compareTo(date)==1){
            System.out.println(PrintStatements.CANT_SHOW_BOOKED_ROOMS_BEFORE_CURRENT_DATE);
            return;
        }

        int noOfRoomsBookedByDate=hotel.getNoOfRoomsBookedByDate(date);

        System.out.println(PrintStatements.BOOKED_UNBOOKED_OPTION+"\n");
        System.out.println(noOfRoomsBookedByDate+"        "+(hotel.getTotalNumberOfRooms()-noOfRoomsBookedByDate));

    }

    //----------------------------------------------6.Change Price of Rooms--------------------------------------------//

    private void changeRoomPrices(Hotel hotel){
        ArrayList<Room> rooms=hotel.getRooms();
        displayRooms(rooms);
        System.out.println(PrintStatements.CHANGE_ROOM_PRICES);
        System.out.println("Enter S.No Change Room Price : ");
        int choice = InputHelper.getInputWithinRange(1,rooms.size(),null);
        Room room=rooms.get(choice-1);
        System.out.println("Room Max Capacity : "+room.getRoomCapacity());
        System.out.println("Room Base Price : "+room.getRoomBasePrice()+"  Room Max Price : "+room.getRoomMaxPrice());
        System.out.println("Room Bed Price : "+room.getBedPrice());
        double newBaseRoomPrice=setBasePrice(100,"New");
        double newMaxRoomPrice=setMaxPrice(newBaseRoomPrice,"New");
        System.out.println("New Bed Price : ");
        double newBedPrice=InputHelper.getDoubleInputWithinRange(1,(int)InputHelper.findParts(30,newBaseRoomPrice),"Base Bed price should be less than 30% of Base Room Price");
        room.changeRoomPrice(new Price(newBaseRoomPrice,newMaxRoomPrice));
        room.changeBedPrice(new Price(newBedPrice,newBedPrice));
        adminDB.addPriceUpdatedHotelList(hotel.getHotelID());
    }


    private double setBasePrice(int minimum,String str){
        System.out.println((str==null?"":str+" ")+ PrintStatements.BASE_PRICE);
        double basePrice=InputHelper.getDoubleInput();
        do{
            if(basePrice<minimum){
                System.out.println("Base Price Should be Greater than "+minimum);
                return setBasePrice(minimum,str);
            }
            else{
                return basePrice;
            }
        }while(true);
    }

    private double setMaxPrice(double basePrice, String str){
        System.out.println((str==null?"":str+" ")+ PrintStatements.MAX_PRICE);
        double maxPrice=InputHelper.getDoubleInput();
        do{
            if(maxPrice<=basePrice){
                System.out.println(PrintStatements.MAX_PRICE_CONDITION+ PrintStatements.BASE_PRICE+basePrice);
                return setMaxPrice(basePrice,str);
            }
            else{
                return maxPrice;
            }
        }while(true);
    }

    //----------------------------------------------7.List of Customers who booked rooms-------------------------------//

    private void bookedCustomersList(Hotel hotel){
        ArrayList<Integer> bookingIDs=bookingDB.getHotelBookingIDs(hotel.getHotelID());
        if(bookingIDs.isEmpty()){
            System.out.println(PrintStatements.NO_CUSTOMER_BOOKED_ROOMS);
            InputHelper.pressEnterToContinue();
            return;
        }
        System.out.println();
        for(int i=0;i<bookingIDs.size();i++){
            CustomerBooking customerBooking= bookingDB.getCustomerBookingWithID(bookingIDs.get(i));
            User customer= userDB.getCustomerByID(customerBooking.getCustomerID());
            customerDetails(i,customerBooking,customer);
        }
        InputHelper.pressEnterToContinue();
    }

    private void customerDetails(int sno,CustomerBooking customerBooking,User customer){
        System.out.println((sno!=-1?((sno+1)+"."):"")+ PrintStatements.CUSTOMER_NAME+customer.getUserName());
        System.out.println(PrintStatements.BOOKING_ID+customerBooking.getBookingID());
        System.out.println(PrintStatements.CHECK_IN_DATE+customerBooking.getCheckInDateString());
        System.out.println(PrintStatements.CHECK_OUT_DATE+customerBooking.getCheckOutDateString());
        System.out.println(PrintStatements.NO_OF_ROOMS_BOOKED+customerBooking.getTotalNoOfRoomsBooked());
        System.out.println("Total Price : ₹"+customerBooking.getTotalPrice());
        System.out.println(PrintStatements.PAID+(customerBooking.getPaid()? PrintStatements.YES: PrintStatements.NO));
        System.out.println("\n");
    }

    //------------------------------------------------------Verify Customer--------------------------------------------//

    private void verifyCustomer(Hotel hotel){
        ArrayList<Integer> bookingIDs=bookingDB.getHotelBookingIDs(hotel.getHotelID());
        if(bookingIDs.isEmpty()){
            System.out.println(PrintStatements.NO_CUSTOMER_BOOKED_ROOMS);
            InputHelper.pressEnterToContinue();
            return;
        }
        System.out.println(PrintStatements.ENTER_BOOKING_ID);
        int bookingID=InputHelper.getIntegerInput();
        for(int i=0;i<bookingIDs.size();i++){
            if(bookingIDs.get(i)==bookingID){
                CustomerBooking customerBooking=bookingDB.getCustomerBookingWithID(bookingID);
                User customer= userDB.getCustomerByID(customerBooking.getCustomerID());
                customerDetails(-1,customerBooking,customer);
                return;
            }
        }

        System.out.println(PrintStatements.NO_CUSTOMER_BOOKED_WITH_FOLLOWING_ID);

    }

}
