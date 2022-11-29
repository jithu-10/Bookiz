package application;

import hotelbooking.AdminDB;
import hotelbooking.BookingDB;
import hotelbooking.CustomerBooking;
import hotelbooking.*;
import hotelbooking.User;
import hotelbooking.UserDB;

import java.util.*;

public class HotelAdminApp extends AbstractApp {

    @Override
    public void startApp() {
        System.out.println(PrintStatements.LOGIN_REGISTER);
        System.out.println(PrintStatements.ENTER_INPUT);
        int choice = InputHelper.getInputWithinRange(2,null);

        switch (choice){
            case 1:
                User hotelOwner;
                if((hotelOwner=signIn())!=null){
                    System.out.println(PrintStatements.SIGNED_IN);
                    switch(HotelDB.getInstance().getHotelStatusByUserID(hotelOwner.getUserID())){
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
        return UserDB.getInstance().getHotelAdminByPhoneNumber_Mail(mail_or_phone,password);

    }

    @Override
    public void menu(User user) {
        Hotel hotel=HotelDB.getInstance().getHotelByUserID(user.getUserID());
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
        Hotel hotel=HotelDB.getInstance().getHotelByUserID(user.getUserID());
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
        Hotel hotel=HotelDB.getInstance().getHotelByUserID(user.getUserID());
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
        Hotel hotel=HotelDB.getInstance().getHotelByUserID(user.getUserID());
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
        User hotelOwner=getUserDetails();
        if(hotelOwner==null){
            return;
        }

        String hotelName=getHotelName();
        Address hotelAddress=getHotelAddress();
        Hotel hotel;
        try{
            hotel=new Hotel(hotelOwner,hotelName,hotelAddress);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }


        HotelDB.getInstance().registerHotel(hotel);
        addRooms(hotel);
        addHotelAmenities(hotel);
        hotelTypeSpecification(hotel);
        System.out.println(PrintStatements.HOTEL_REGISTERED);
    }

    public User getUserDetails(){
        System.out.println(PrintStatements.ENTER_FULL_NAME);
        String userName=InputHelper.getStringInput();
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

            if(Validator.confirmPasswordValidator(password,confirmPassword)){
                break;
            }
            System.out.println(PrintStatements.PASSWORD_NOT_MATCH);
        }


        User hotelAdmin=new User(userName,phoneNumber,email);
        UserDB.getInstance().addHotelAdmin(hotelAdmin,password);
        return hotelAdmin;
    }

    private String getEmail(){
        String email;
        do{
            System.out.println(PrintStatements.ENTER_EMAIL);
            email=InputHelper.getEmailInput();

            if(UserDB.getInstance().isHotelEmailExist(email)){
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
            if((UserDB.getInstance().isHotelPhoneNumberExist(phoneNumber))){
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


    private String getHotelName(){
        System.out.println(PrintStatements.ENTER_HOTEL_NAME);
        String hotelName=InputHelper.getStringInput();
        return hotelName;
    }


    private Address getHotelAddress(){
        System.out.println(PrintStatements.ENTER_BUILDING_NO);
        String buildingNo=InputHelper.getStringInput();
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
        System.out.println("1."+ HotelType.ELITE+" HOTEL ");
        System.out.println("2."+ HotelType.PREMIUM+" HOTEL ");
        System.out.println("3."+ HotelType.STANDARD+" HOTEL ");
        System.out.println("4."+"Skip");
        int choice=InputHelper.getInputWithinRange(4,null);
        switch (choice){
            case 1:
                hotel.setHotelType(HotelType.ELITE);
                break;
            case 2:
                hotel.setHotelType(HotelType.PREMIUM);
                break;
            case 3:
                hotel.setHotelType(HotelType.STANDARD);
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
            int maxGuest=InputHelper.getInputWithinRange(12,"Only up to 12 guest can occupy a room");

            Price roomPrice=getRoomPrice();

            Price bedPrice=getBedPrice(roomPrice.getBasePrice());


            for(int j=0;j<noOfRooms;j++){
                Room room=new Room(hotel,maxGuest,roomPrice,bedPrice);
                hotel.addRoom(room);
            }


        }
    }

    private Price getBedPrice(double baseBedPrice){
        Price bedPrice;
        do{
            System.out.println("Enter Bed Price : ");
            double bedPriceMax=InputHelper.getDoubleInputWithinRange(1,(int)InputHelper.findParts(30,baseBedPrice),"Bed price should be less than 30% of Base Room Price");
            try{
                bedPrice=new Price(bedPriceMax,bedPriceMax+1);
                break;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }while(true);
        return bedPrice;
    }
    private Price getRoomPrice(){
        Price roomPrice;
        do{
            System.out.println("Enter Base Room Rent (Without Bed Price) : ");
            double baseRoomPrice= InputHelper.getDoubleInput();
            System.out.println("Enter Max Room Rent (Without Bed Price) : ");
            double maxRoomPrice= InputHelper.getDoubleInput();
            try{
                roomPrice=new Price(baseRoomPrice,maxRoomPrice);
                break;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }while(true);
        return roomPrice;
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

        ArrayList<Amenity>totalAmenities=AmenityDB.getInstance().getAmenities();
        ArrayList<Amenity>hotelAmenities=hotel.getAmenities();
        if(totalAmenities.size()==0){
            System.out.println("NO Amenity to Display");
            return;
        }
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
        ArrayList<Amenity>hotelAmenities=hotel.getAmenities();
        if(hotelAmenities.size()==0){
            System.out.println("No Amenity Available");
            return;
        }
        System.out.println(PrintStatements.REMOVE_HOTEL_AMENITIES);


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
        Date currentDate=InputHelper.getCurrentDate();
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
        Price roomPrice=getRoomPrice();
        Price bedPrice=getBedPrice(roomPrice.getBasePrice());
        room.changeRoomPrice(roomPrice);
        room.changeBedPrice(bedPrice);
        AdminDB.getInstance().addPriceUpdatedHotelList(hotel.getHotelID());
    }


    //----------------------------------------------7.List of Customers who booked rooms-------------------------------//

    private void bookedCustomersList(Hotel hotel){
        ArrayList<CustomerBooking> bookings=BookingDB.getInstance().getHotelBookings(hotel.getHotelID());
        if(bookings.isEmpty()){
            System.out.println(PrintStatements.NO_CUSTOMER_BOOKED_ROOMS);
            InputHelper.pressEnterToContinue();
            return;
        }
        System.out.println();
        for(int i=0;i<bookings.size();i++){
            CustomerBooking customerBooking= bookings.get(i);
            //User customer= UserDB.getInstance().getCustomerByID(customerBooking.getCustomerID());
            User customer=customerBooking.getCustomer();
            customerDetails(i,customerBooking,customer);
        }
        InputHelper.pressEnterToContinue();
    }

    private void customerDetails(int sno,CustomerBooking customerBooking,User customer){
        System.out.println((sno!=-1?((sno+1)+"."):"")+ PrintStatements.CUSTOMER_NAME+customer.getUserName());
        System.out.println(PrintStatements.BOOKING_ID+customerBooking.getBookingID());
        System.out.println(PrintStatements.CHECK_IN_DATE+InputHelper.getSimpleDateWithoutYear(customerBooking.getCheckInDate()));
        System.out.println(PrintStatements.CHECK_OUT_DATE+InputHelper.getSimpleDateWithoutYear(customerBooking.getCheckOutDate()));
        System.out.println(PrintStatements.NO_OF_ROOMS_BOOKED+customerBooking.getTotalNoOfRoomsBooked());
        System.out.println("Total Price : ₹"+customerBooking.getTotalPrice());
        System.out.println(PrintStatements.PAID+(customerBooking.getPaid()? PrintStatements.YES: PrintStatements.NO));
        System.out.println("\n");
    }

    //------------------------------------------------------Verify Customer--------------------------------------------//

    private void verifyCustomer(Hotel hotel){
        ArrayList<CustomerBooking> bookings=BookingDB.getInstance().getHotelBookings(hotel.getHotelID());
        if(bookings.isEmpty()){
            System.out.println(PrintStatements.NO_CUSTOMER_BOOKED_ROOMS);
            InputHelper.pressEnterToContinue();
            return;
        }
        System.out.println(PrintStatements.ENTER_BOOKING_ID);
        int bookingID=InputHelper.getIntegerInput();
        for(int i=0;i<bookings.size();i++){
            if(bookings.get(i).getBookingID()==bookingID){
                CustomerBooking customerBooking=bookings.get(i);
                //User customer= UserDB.getInstance().getCustomerByID(customerBooking.getCustomerID());
                User customer=customerBooking.getCustomer();
                customerDetails(-1,customerBooking,customer);
                return;
            }
        }

        System.out.println(PrintStatements.NO_CUSTOMER_BOOKED_WITH_FOLLOWING_ID);

    }

}
