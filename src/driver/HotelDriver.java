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
import utility.PrintStatements;

import java.util.ArrayList;
import java.util.Date;

public class HotelDriver extends AbstractDriver{

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
            hotelOwner=userDB.getUserByPhoneNumber_Mail(mail_or_phone, UserType.HOTEL_OWNER);
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
        User hotelOwner= getUserDetails(UserType.HOTEL_OWNER);
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
        System.out.println(PrintStatements.HOTEL_REGISTERED);
    }

    private void registerAgain(Hotel hotel){
        if(hotel.getHotelApproveStatus()==HotelStatus.REJECTED){
            hotel.setHotelApproveStatus(HotelStatus.ON_PROCESS);
        }
        else if(hotel.getHotelApproveStatus()==HotelStatus.REMOVED){
            hotel.setHotelApproveStatus(HotelStatus.REMOVED_RE_PROCESS);
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

    private void roomDetails(Hotel hotel){

        System.out.println(PrintStatements.ROOM_DETAILS);

        System.out.println(PrintStatements.SINGLE_BED_COUNT);
        int singleBedCount=InputHelper.getWholeNumberIntegerInput();
        double basePrice=setBaseRoomPrice(null);
        double maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addSingleBedRooms(singleBedCount,basePrice,maxPrice);
        System.out.println(PrintStatements.DOUBLE_BED_COUNT);
        int doubleBedCount=InputHelper.getWholeNumberIntegerInput();
        basePrice=setBaseRoomPrice(null);
        maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addDoubleBedRooms(doubleBedCount,basePrice,maxPrice);

        System.out.println(PrintStatements.SUITE_ROOM_COUNT);
        int suiteRoomCount=InputHelper.getWholeNumberIntegerInput();
        basePrice=setBaseRoomPrice(null);
        maxPrice=setMaxRoomPrice(basePrice,null);
        hotel.addSuiteRooms(suiteRoomCount,basePrice,maxPrice);

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
        System.out.println(PrintStatements.ADD_ROOMS);
        System.out.println(PrintStatements.ENTER_TYPE_OF_ROOM);
        System.out.println("1."+ RoomType.SINGLE_BED_ROOM);
        System.out.println("2."+RoomType.DOUBLE_BED_ROOM);
        System.out.println("3."+RoomType.SUITE_ROOM);
        System.out.println("4."+ PrintStatements.EXIT);
        System.out.println(PrintStatements.ENTER_INPUT);
        int choice=InputHelper.getInputWithinRange(4,null);
        if(choice==4){
            System.out.println(PrintStatements.BACK_TO_MAIN);
            return;
        }
        System.out.println(PrintStatements.ENTER_NO_OF_ROOMS_TO_ADD);
        int count=InputHelper.getInputWithinRange(10, PrintStatements.ROOM_ADD_CONDITION);

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
        System.out.println(PrintStatements.ROOMS_ADDED_SUCCESSFULLY);
    }

    //-------------------------------------------2.Remove Rooms--------------------------------------------------------//

    private void removeRooms(Hotel hotel){
        System.out.println(PrintStatements.REMOVE_ROOMS);
        System.out.println(PrintStatements.ENTER_TYPE_OF_ROOM);
        System.out.println("1."+ RoomType.SINGLE_BED_ROOM);
        System.out.println("2."+RoomType.DOUBLE_BED_ROOM);
        System.out.println("3."+RoomType.SUITE_ROOM);
        System.out.println("4."+ PrintStatements.GO_BACK);
        System.out.println(PrintStatements.ENTER_INPUT);
        int choice=InputHelper.getInputWithinRange(4,null);
        if(choice==4){
            System.out.println(PrintStatements.BACK_TO_MAIN);
            return;
        }
        System.out.println(PrintStatements.ENTER_NO_OF_ROOMS_TO_REMOVE);
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
        System.out.println(PrintStatements.ROOMS_REMOVED_SUCCESSFULLY);
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

        int noOfSingleBedRoomsBookedByDate=hotel.getNoOfRoomsBookedByDate(date,RoomType.SINGLE_BED_ROOM);
        int noOfDoubleBedRoomsBookedByDate=hotel.getNoOfRoomsBookedByDate(date,RoomType.DOUBLE_BED_ROOM);
        int noOfSuiteRoomsBookedByDate=hotel.getNoOfRoomsBookedByDate(date,RoomType.SUITE_ROOM);

        System.out.println(PrintStatements.BOOKED_UNBOOKED_OPTION+"\n");
        System.out.println(RoomType.SINGLE_BED_ROOM +"        "+noOfSingleBedRoomsBookedByDate+"        "+(hotel.getTotalNumberOfRooms(RoomType.SINGLE_BED_ROOM)-noOfSingleBedRoomsBookedByDate));
        System.out.println(RoomType.DOUBLE_BED_ROOM +"        "+noOfDoubleBedRoomsBookedByDate+"        "+(hotel.getTotalNumberOfRooms(RoomType.DOUBLE_BED_ROOM)-noOfDoubleBedRoomsBookedByDate));
        System.out.println(RoomType.SUITE_ROOM +"            "+noOfSuiteRoomsBookedByDate+"        "+(hotel.getTotalNumberOfRooms(RoomType.SUITE_ROOM)-noOfSuiteRoomsBookedByDate));

    }

    //----------------------------------------------6.Change Price of Rooms--------------------------------------------//

    private void changeRoomPrices(Hotel hotel){
        System.out.println(PrintStatements.CHANGE_ROOM_PRICES);
        System.out.println(PrintStatements.ENTER_TYPE_OF_ROOM);
        System.out.println("1."+ RoomType.SINGLE_BED_ROOM);
        System.out.println("2."+RoomType.DOUBLE_BED_ROOM);
        System.out.println("3."+RoomType.SUITE_ROOM);
        System.out.println("4."+ PrintStatements.GO_BACK);
        System.out.println(PrintStatements.GO_BACK);
        int choice=InputHelper.getInputWithinRange(4,null);

        switch (choice){
            case 1:
                System.out.println(RoomType.SINGLE_BED_ROOM +" -> "+ PrintStatements.CURRENT_BASE_PRICE+hotel.getSingleBedRoomBasePrice()+ PrintStatements.CURRENT_MAX_PRICE+hotel.getSingleBedRoomMaxPrice()+ PrintStatements.CURRENT_LIST_PRICE+hotel.getSingleBedRoomListPrice());
                double newBaseRoomPrice=setBaseRoomPrice("New");
                double newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setSingleBedRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 2:
                System.out.println(RoomType.DOUBLE_BED_ROOM +" ->"+ PrintStatements.CURRENT_BASE_PRICE+hotel.getDoubleBedRoomBasePrice()+ PrintStatements.CURRENT_MAX_PRICE+hotel.getDoubleBedRoomMaxPrice()+ PrintStatements.CURRENT_LIST_PRICE+hotel.getDoubleBedRoomListPrice());
                newBaseRoomPrice=setBaseRoomPrice("New");
                newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setDoubleBedRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 3:
                System.out.println(RoomType.SUITE_ROOM +" ->"+ PrintStatements.CURRENT_BASE_PRICE+hotel.getSuiteRoomBasePrice()+ PrintStatements.CURRENT_MAX_PRICE+hotel.getSuiteRoomMaxPrice()+ PrintStatements.CURRENT_LIST_PRICE+hotel.getSuiteRoomListPrice());
                newBaseRoomPrice=setBaseRoomPrice("New");
                newMaxRoomPrice=setMaxRoomPrice(newBaseRoomPrice,"New");
                hotel.setSuiteRoomPrice(newBaseRoomPrice,newMaxRoomPrice);
                break;
            case 4:
                System.out.println(PrintStatements.BACK_TO_MAIN);
                return;
        }
        System.out.println(PrintStatements.ROOM_PRICE_CHANGED_SUCCESSFULLY);
        adminDB.addPriceUpdatedHotelList(hotel.getHotelID());
    }

    private double setBaseRoomPrice(String str){
        System.out.println((str==null?"":str+" ")+ PrintStatements.BASE_PRICE);
        double basePrice=InputHelper.getDoubleInput();
        do{
            if(basePrice<1){
                System.out.println(PrintStatements.BASE_PRICE_CONDITION);
                return setBaseRoomPrice(str);
            }
            else{
                return basePrice;
            }
        }while(true);

    }

    private double setMaxRoomPrice(double basePrice,String str){
        System.out.println((str==null?"":str+" ")+ PrintStatements.MAX_PRICE);
        double maxPrice=InputHelper.getDoubleInput();
        do{
            if(maxPrice<=basePrice){
                System.out.println(PrintStatements.MAX_PRICE_CONDITION+ PrintStatements.BASE_PRICE+basePrice);
                return setMaxRoomPrice(basePrice,str);
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
            User customer= userDB.getUserByID(customerBooking.getCustomerID(),UserType.CUSTOMER);
            customerDetails(i,customerBooking,customer);
        }
        InputHelper.pressEnterToContinue();
    }

    private void customerDetails(int sno,CustomerBooking customerBooking,User customer){
        System.out.println((sno!=-1?((sno+1)+"."):"")+ PrintStatements.CUSTOMER_NAME+customer.getUserName());
        System.out.println(PrintStatements.BOOKING_ID+customerBooking.getBookingID());
        System.out.println(PrintStatements.CHECK_IN_DATE+customerBooking.getCheckInDateString());
        System.out.println(PrintStatements.CHECK_OUT_DATE+customerBooking.getCheckOutDateString());
        System.out.println(PrintStatements.NO_OF_ROOMS_BOOKED);
        System.out.println("     1."+RoomType.SINGLE_BED_ROOM +" - "+customerBooking.getNoOfSingleBedroomsBooked());
        System.out.println("     2."+RoomType.DOUBLE_BED_ROOM +" - "+customerBooking.getNoOfDoubleBedRoomsBooked());
        System.out.println("     3."+RoomType.SUITE_ROOM +" - "+customerBooking.getNoOfSuiteRoomsBooked());
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
                User customer= userDB.getUserByID(customerBooking.getCustomerID(),UserType.CUSTOMER);
                customerDetails(-1,customerBooking,customer);
                return;
            }
        }

        System.out.println(PrintStatements.NO_CUSTOMER_BOOKED_WITH_FOLLOWING_ID);

    }

}
