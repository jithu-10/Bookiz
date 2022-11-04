package driver;

import admin.AdminDB;
import booking.Booking;
import booking.BookingDB;
import booking.CustomerBooking;
import hotel.*;
import hotel.AddressDB;
import user.User;
import user.UserAuthenticationDB;
import user.UserDB;
import user.UserType;
import utility.QA;
import utility.InputHelper;
import utility.PrintStatements;
import utility.Validator;

import java.util.*;

public class CustomerDriver extends AbstractDriver {

    private static final CustomerDriver customerDriver=new CustomerDriver();
    private final UserDB userDB=UserDB.getInstance();
    private final HotelDB hotelDB=HotelDB.getInstance();
    private final AdminDB adminDB=AdminDB.getInstance();
    private final AddressDB addressDB=AddressDB.getInstance();
    private final BookingDB bookingDB=BookingDB.getInstance();
    private static final UserAuthenticationDB userAuthenticationDB=UserAuthenticationDB.getInstance();
    private CustomerDriver(){

    }

    static CustomerDriver getInstance(){
        return customerDriver;
    }

    @Override
    public void startDriver() {
        System.out.println(PrintStatements.CUSTOMER);
        System.out.println(PrintStatements.LOGIN_REGISTER);
        System.out.println(PrintStatements.ENTER_INPUT);
        int choice = InputHelper.getInputWithinRange(2,null);
        switch (choice){
            case 1:
                User user;
                if((user=signIn())!=null){
                    System.out.println(PrintStatements.SIGNED_IN);
                    menu(user);
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

    @Override
    public User signIn() {
        System.out.println(PrintStatements.SIGN_IN);
        System.out.println("Enter Phone Number or Email ");
        Object mail_or_phone=InputHelper.getPhoneNumberOrEmail();
        System.out.println(PrintStatements.ENTER_PASSWORD);
        String passWord= InputHelper.getStringInput();
        User user= null;
        if(userAuthenticationDB.authenticateCustomer(mail_or_phone,passWord)){
            user=userDB.getUserByPhoneNumber_Mail(mail_or_phone,UserType.CUSTOMER);
        }
        return user;
    }

    @Override
    public void menu(User customer) {

        System.out.println(PrintStatements.WELCOME+customer.getUserName()+ PrintStatements.SMILE);
        do{
            System.out.println(PrintStatements.CUSTOMER_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(6,null);
            switch (choice){
                case 1:
                    bookHotel(customer);
                    break;
                case 2:
                    listBookings(customer);
                    break;
                case 3:
                    cancelBooking(customer);
                    break;
                case 4:
                    listFavoriteHotels(customer);
                    InputHelper.pressEnterToContinue();
                    break;
                case 5:
                    help();
                    break;
                case 6:
                    System.out.println(PrintStatements.SIGNED_OUT);
                    return;
            }

        }while(true);
    }


    //------------------------------------------------Customer Registration--------------------------------------------//
    @Override
    public void register() {

        User user= getUserDetails(UserType.CUSTOMER);
        if(user==null){
            return;
        }
        userDB.addUser(user);
        System.out.println(PrintStatements.SIGN_UP_COMPLETED);
    }



    //------------------------------------------------1.Book Hotel----------------------------------------------------//

    private void bookHotel(User customer){
        System.out.println(PrintStatements.ENTER_LOCALITY);
        String locality=InputHelper.modifyString(InputHelper.getStringInput());
        if(!addressDB.isLocalityAvailable(locality)){
            System.out.println(PrintStatements.NO_HOTELS_AVAILABLE_LOCALITY);

            return;
        }

        System.out.println(PrintStatements.CHECK_IN_DATE);
        Date checkInDate=compareAndCheckDate(InputHelper.setTime(new Date()),"You can't book hotel for previous dates",false,true);
        System.out.println(PrintStatements.CHECK_OUT_DATE);
        Date checkOutDate=compareAndCheckDate(checkInDate,"Check out Date will be greater than Check In Date",true,false);

        int noOfSingleBedroomsNeeded=0;
        int noOfDoubleBedroomsNeeded=0;
        int noOfSuiteRoomNeeded=0;

        do{
            System.out.println(PrintStatements.ENTER_NO_OF+RoomType.SINGLE_BED_ROOM + PrintStatements.NEEDED);
            noOfSingleBedroomsNeeded=InputHelper.getWholeNumberIntegerInput();
            System.out.println(PrintStatements.ENTER_NO_OF+RoomType.DOUBLE_BED_ROOM + PrintStatements.NEEDED);
            noOfDoubleBedroomsNeeded=InputHelper.getWholeNumberIntegerInput();
            System.out.println(PrintStatements.ENTER_NO_OF+RoomType.SUITE_ROOM + PrintStatements.NEEDED);
            noOfSuiteRoomNeeded=InputHelper.getWholeNumberIntegerInput();

            if(noOfSingleBedroomsNeeded+noOfDoubleBedroomsNeeded+noOfSuiteRoomNeeded==0){
                System.out.println("At least 1 room should be given");
                System.out.println("1."+ PrintStatements.TRY_AGAIN);
                System.out.println("2."+ PrintStatements.GO_BACK);
                if(InputHelper.getInputWithinRange(2,null)==2){
                    break;
                }
            }
            else{
                break;
            }
        }while(true);


        CustomerBooking customerBooking=new CustomerBooking(checkInDate,checkOutDate);
        customerBooking.setNoOfSingleBedroomsBooked(noOfSingleBedroomsNeeded);
        customerBooking.setNoOfDoubleBedRoomsBooked(noOfDoubleBedroomsNeeded);
        customerBooking.setNoOfSuiteRoomsBooked(noOfSuiteRoomNeeded);

        findAvailableHotels(customer,customerBooking,locality);
    }

    private Date compareAndCheckDate(Date date,String str,boolean currentDateCheck,boolean checkIn){
        Date newDate=InputHelper.getDate();
        int value=newDate.compareTo(date);
        if(value==-1){
            System.out.println(str);
            System.out.println(PrintStatements.ENTER_CORRECT_DATE);
            return compareAndCheckDate(date,str,currentDateCheck,checkIn);
        }
        else if(currentDateCheck&&value==0){
            System.out.println(str);
            System.out.println(PrintStatements.ENTER_CORRECT_DATE);
            return compareAndCheckDate(date,str,true,checkIn);
        }

        else{
            int noOfInBetweenDays=InputHelper.getDatesBetweenTwoDates(date,newDate).size();
            if(noOfInBetweenDays>60&&!checkIn){
                System.out.println(PrintStatements.CHECK_IN_DATE_CONDITION);
                return compareAndCheckDate(date,str,currentDateCheck,false);
            }
            if(noOfInBetweenDays>180&&checkIn){
                System.out.println(PrintStatements.CHECK_OUT_DATE_CONDITION);
                return compareAndCheckDate(date,str,currentDateCheck,true);
            }
            return newDate;
        }
    }

    private void findAvailableHotels(User customer, CustomerBooking customerBooking, String locality){
        ArrayList<Integer> availableHotelsID=hotelDB.filterHotels(customerBooking,locality);
        if(availableHotelsID.isEmpty()){
            System.out.println(PrintStatements.SOLD_OUT);
            return ;
        }
        do{
            for(int i=0;i<availableHotelsID.size();i++){
                printHotelDetailsWithBooking(i,hotelDB.getHotelByID(availableHotelsID.get(i)),customerBooking);
            }

            System.out.println("1."+ PrintStatements.SELECT_HOTEl);
            System.out.println("2."+ PrintStatements.GO_BACK);
            int selectChoice=InputHelper.getInputWithinRange(2,null);
            if(selectChoice==1){
                System.out.println(PrintStatements.ENTER_SNO_TO_SELECT_HOTEL);
                int choice=InputHelper.getInputWithinRange(availableHotelsID.size(),null);
                System.out.println("\n\n");
                boolean booked=expandedHotelDetails(customerBooking,hotelDB.getHotelByID(availableHotelsID.get(choice-1)),customer,customerBooking.getNoOfDays());
                if(booked){
                    return;
                }
            }
            else{
                break;
            }
        }while(true);
    }



    private void printHotelDetails(int sno,Hotel hotel){
        System.out.println((sno+1)+" . "+"BOOKIZ "+hotel.getHotelType()+" Hotel ID : "+hotel.getHotelID());
        System.out.println("\t"+hotel.getHotelName());
        System.out.println("\tPh.No : "+hotel.getPhoneNumber());
        System.out.println("\tAddress : No."+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet());
        System.out.println("\t"+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity());
        System.out.println("\t"+hotel.getAddress().getState()+"-"+hotel.getAddress().getPostalCode());
    }
    private void printHotelDetailsWithBooking(int sno,Hotel hotel,CustomerBooking booking){
        printHotelDetails(sno,hotel);
        double listPrice=getListPrice(booking,hotel);
        double maxPrice=getMaxPrice(booking,hotel);
        int discount=(int)getDiscountPercent(listPrice,maxPrice);
        System.out.println("Price(per night) : ₹"+listPrice+" Actual Price(per night) : ₹"+maxPrice+"  "+discount+"%off\n\n");
    }

    private double getListPrice(CustomerBooking booking,Hotel hotel){
        double singleBedRoomPrice=booking.getNoOfSingleBedroomsBooked()*hotel.getSingleBedRoomListPrice();
        double doubleBedRoomPrice=booking.getNoOfDoubleBedRoomsBooked()*hotel.getDoubleBedRoomListPrice();
        double suiteRoomPrice=booking.getNoOfSuiteRoomsBooked()*hotel.getSuiteRoomListPrice();
        return singleBedRoomPrice+doubleBedRoomPrice+suiteRoomPrice;
    }

    private double getMaxPrice(CustomerBooking booking,Hotel hotel){
        double singleBedRoomPrice=booking.getNoOfSingleBedroomsBooked()*hotel.getSingleBedRoomMaxPrice();
        double doubleBedRoomPrice=booking.getNoOfDoubleBedRoomsBooked()*hotel.getDoubleBedRoomMaxPrice();
        double suiteRoomPrice=booking.getNoOfSuiteRoomsBooked()*hotel.getSuiteRoomMaxPrice();
        return singleBedRoomPrice+doubleBedRoomPrice+suiteRoomPrice;
    }

    private double getDiscountPercent(double listPrice,double maxPrice){
        double discount=maxPrice-listPrice;
        return (discount/maxPrice)*100;
    }


    private boolean expandedHotelDetails(CustomerBooking customerBooking,Hotel hotel,User customer,int totalDays){
        System.out.println(hotel.getHotelType()+" "+hotel.getHotelID()+" "+hotel.getHotelName());
        System.out.println("\tNo."+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet());
        System.out.println("\t"+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity());
        System.out.println("\t"+hotel.getAddress().getState()+hotel.getAddress().getPostalCode());

        System.out.println();
        System.out.println("Your Booking Details");
        System.out.println("Dates : "+customerBooking.getCheckInDateString()+" - "+customerBooking.getCheckOutDateString());
        System.out.println("Rooms : "+customerBooking.getTotalNoOfRoomsBooked());
        System.out.println("Booking for : "+customer.getUserName());
        System.out.println();
        System.out.println("Amenities : ");
        ArrayList<Amenity> amenitiesList=hotel.getAmenities();
        int rowChange=0;
        for(int i=0;i<amenitiesList.size();i++){
            System.out.print((i+1)+"."+amenitiesList.get(i).getName()+"    ");
            rowChange++;
            if(rowChange==4){
                rowChange=0;
                System.out.println();
            }
        }

        System.out.println("\nPricing breakup : ");
        double totalPriceOfSingleBedRooms=hotel.getSingleBedRoomListPrice()*customerBooking.getNoOfSingleBedroomsBooked()*totalDays;
        if(customerBooking.getNoOfSingleBedroomsBooked()>0){
            System.out.println(RoomType.SINGLE_BED_ROOM +" : ₹"+hotel.getSingleBedRoomListPrice()+" * "+customerBooking.getNoOfSingleBedroomsBooked()+" : ₹"+hotel.getSingleBedRoomListPrice()*customerBooking.getNoOfSingleBedroomsBooked()+" * "+totalDays+(totalDays>1?" days":" day")+" : ₹"+totalPriceOfSingleBedRooms);
        }

        double totalPriceOfDoubleBedRooms=hotel.getDoubleBedRoomListPrice()*customerBooking.getNoOfDoubleBedRoomsBooked()*totalDays;
        if(customerBooking.getNoOfDoubleBedRoomsBooked()>0){
            System.out.println(RoomType.DOUBLE_BED_ROOM +" : ₹"+hotel.getDoubleBedRoomListPrice()+" * "+customerBooking.getNoOfDoubleBedRoomsBooked()+" : ₹"+hotel.getDoubleBedRoomListPrice()*customerBooking.getNoOfDoubleBedRoomsBooked()+" * "+totalDays+(totalDays>1?" days":" day")+" : ₹"+totalPriceOfDoubleBedRooms);
        }

        double totalPriceOfSuiteRooms=hotel.getSuiteRoomListPrice()*customerBooking.getNoOfSuiteRoomsBooked()*totalDays;
        if(customerBooking.getNoOfSuiteRoomsBooked()>0){
            System.out.println(RoomType.SUITE_ROOM +" : ₹"+hotel.getSuiteRoomListPrice()+" * "+customerBooking.getNoOfSuiteRoomsBooked()+" : ₹"+hotel.getSuiteRoomListPrice()*customerBooking.getNoOfSuiteRoomsBooked()+" * "+totalDays+(totalDays>1?" days":" day")+" : ₹"+totalPriceOfSuiteRooms);
        }

        double totalPrice=totalPriceOfSingleBedRooms+totalPriceOfDoubleBedRooms+totalPriceOfSuiteRooms;
        System.out.println("\t\t"+"Total : ₹"+totalPrice);
        System.out.println("\n\n1.Book  2.Add To Favorite List 3.Back");
        int choice=InputHelper.getInputWithinRange(3,null);
        loop:do{
            switch (choice){
                case 1:

                    customerBooking.setTotalPriceOfSingleBedRooms(totalPriceOfSingleBedRooms);
                    customerBooking.setTotalPriceOfDoubleBedRooms(totalPriceOfDoubleBedRooms);
                    customerBooking.setTotalPriceOfSuiteRooms(totalPriceOfSuiteRooms);
                    boolean booked=paySlip(customerBooking);
                    if(booked){
                        bookingDB.addBooking(customerBooking);
                        customerBooking.setHotelID(hotel.getHotelID());
                        bookedDetails(customerBooking,customer,hotel);

                        InputHelper.pressEnterToContinue();
                        return true;
                    }
                    break loop;
                case 2:
                    addToFavoriteList(customer,hotel);
                    System.out.println("Added to Favorite List");
                    InputHelper.pressEnterToContinue();
                    break loop;
                case 3:
                    return false;
            }
        }while (true);

        return false;

    }

    private boolean paySlip(CustomerBooking customerBooking){
        System.out.println("1."+ PrintStatements.PAY_NOW+"₹"+customerBooking.getTotalPrice());
        System.out.println("2."+ PrintStatements.PAY_LATER);
        System.out.println("3."+ PrintStatements.GO_BACK);
        int choice=InputHelper.getInputWithinRange(2,null);
        switch (choice){
            case 1:
                System.out.println(PrintStatements.PAID_USING_UPI);
                customerBooking.setPaid();
                return true;
            case 2:
                System.out.println(PrintStatements.PAY_AT_HOTEL+" "+ PrintStatements.RUPEE+customerBooking.getTotalPrice());
                return true;
            case 3:
                return false;
        }
        return false;
    }

    private void bookedDetails(CustomerBooking customerBooking, User customer,Hotel hotel){
        customerBooking.setCustomerID(customer.getUserID());
        hotel.updateRoomBooking(customerBooking.getNoOfSingleBedroomsBooked(),customerBooking.getNoOfDoubleBedRoomsBooked(),customerBooking.getNoOfSuiteRoomsBooked(),customerBooking.getCheckInDate(),customerBooking.getCheckOutDate(),true);
        System.out.println("\n"+ PrintStatements.BOOKING_CONFIRMED+"\n");
        System.out.println("BOOKIZ "+hotel.getHotelType()+" "+hotel.getHotelID());
        System.out.println(hotel.getHotelName());
        System.out.println("\tNo."+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet());
        System.out.println("\t   "+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity());
        System.out.println("\t   "+hotel.getAddress().getState()+","+hotel.getAddress().getPostalCode());
        System.out.println("Contact : "+hotel.getPhoneNumber());
        System.out.println();
        System.out.println("Check-in                             Check-out");
        System.out.println(customerBooking.getCheckInDateString()+"        "+customerBooking.getNoOfDays()+"N             "+customerBooking.getCheckOutDateString());
        System.out.println("12:00PM onwards                   Before 11:00AM\n");


        System.out.println("BOOKING ID");
        System.out.println(" --> "+customerBooking.getBookingID()+"\n");
        System.out.println("RESERVED FOR");
        System.out.println(" --> "+customer.getUserName()+"\n");
        System.out.println("ROOMS & TYPE");
        if(customerBooking.getNoOfSingleBedroomsBooked()>0){
            System.out.println(" --> "+customerBooking.getNoOfSingleBedroomsBooked()+" "+RoomType.SINGLE_BED_ROOM);
        }
        if(customerBooking.getNoOfDoubleBedRoomsBooked()>0){
            System.out.println(" --> "+customerBooking.getNoOfDoubleBedRoomsBooked()+" "+RoomType.DOUBLE_BED_ROOM);
        }
        if(customerBooking.getNoOfSuiteRoomsBooked()>0){
            System.out.println(" --> "+customerBooking.getNoOfSuiteRoomsBooked()+" "+RoomType.SUITE_ROOM);
        }
        System.out.println("\nContact Information");
        System.out.println(" --> "+"+91-"+customer.getPhoneNumber());

    }



    //------------------------------------------------2.List Bookings--------------------------------------------------//

    private boolean listBookings(User customer){
        ArrayList<Integer>bookingIDs=bookingDB.getUserBookingIDs(customer.getUserID());
        if(bookingIDs.isEmpty()){
            System.out.println(PrintStatements.NO_BOOKINGS_AVAIL);
            return false;
        }
        System.out.println("\n"+ PrintStatements.BOOKING_LIST+"\n");
        for(int i=0;i<bookingIDs.size();i++){
            Booking booking=bookingDB.getBookingWithID(bookingIDs.get(i));
            Hotel hotel=hotelDB.getHotelByID(booking.getHotelID());
            System.out.println((i+1)+".Booking ID : "+booking.getBookingID());
            System.out.println("  Check-in Date : "+booking.getCheckInDateString()+"   Check-out Date : "+booking.getCheckOutDateString());
            System.out.println("  "+hotel.getHotelType()+" "+hotel.getHotelName());
            System.out.println("  No."+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet());
            System.out.println("  "+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity());
            System.out.println("  "+hotel.getAddress().getState()+"-"+hotel.getAddress().getPostalCode());
            System.out.println();
        }
        return true;
    }

    //------------------------------------------------3.Cancel Bookings------------------------------------------------//

    private void cancelBooking(User customer){
        if(!listBookings(customer)){
            return;
        }
        System.out.println("1."+ PrintStatements.CANCEL_HOTEL);
        System.out.println("2."+ PrintStatements.GO_BACK);
        System.out.println(PrintStatements.ENTER_INPUT);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            System.out.println(PrintStatements.ENTER_SNO_TO_CANCEL_BOOKING);
            ArrayList<Integer> bookingIDs=bookingDB.getUserBookingIDs(customer.getUserID());
            int bookingIDIndex=InputHelper.getInputWithinRange(bookingIDs.size(),null);
            int bookingID=bookingIDs.get(bookingIDIndex-1);
            CustomerBooking customerBooking=bookingDB.getCustomerBookingWithID(bookingID);
            bookingDB.removeBookingWithBookingID(bookingID);
            Hotel hotel=hotelDB.getHotelByID(customerBooking.getHotelID());
            hotel.updateRoomBooking(customerBooking.getNoOfSingleBedroomsBooked(),customerBooking.getNoOfDoubleBedRoomsBooked(),customerBooking.getNoOfSuiteRoomsBooked(),customerBooking.getCheckInDate(),customerBooking.getCheckOutDate(),false);
            System.out.println(PrintStatements.BOOKING_CANCELLED);
        }
    }


    //------------------------------------------------4.Favorite List----------------------------------------------------//
    private void addToFavoriteList(User customer,Hotel hotel){
        hotelDB.addFavoriteHotels(customer.getUserID(),hotel.getHotelID());
    }

    private void listFavoriteHotels(User customer){
        System.out.println(PrintStatements.FAVORITE_HOTELS);
        ArrayList<Integer> favoriteHotelsIDs=hotelDB.getFavoriteHotels(customer.getUserID());
        for(int i=0;i<favoriteHotelsIDs.size();i++){
            Hotel hotel=hotelDB.getHotelByID(favoriteHotelsIDs.get(i));
            if(hotel.getHotelApproveStatus()!=HotelStatus.APPROVED){
                hotelDB.removeFavoriteHotels(customer.getUserID(), favoriteHotelsIDs.get(i));
            }
            else{
                printHotelDetails(i,hotel);
            }

        }
        if(favoriteHotelsIDs.isEmpty()){
            System.out.println(PrintStatements.NO_FAVORITE_HOTELS);
        }


    }

    //------------------------------------------------4.Help-----------------------------------------------------------//


    private void help(){
        System.out.println(PrintStatements.HELP_SECTION);
        System.out.println(PrintStatements.HELP_MENU);
        int choice=InputHelper.getInputWithinRange(3,null);

        switch (choice){
            case 1:
                listFaq();
                break;
            case 2:
                askQuestion();
                break;
            case 3:

        }
    }

    private void listFaq(){
        ArrayList<QA> faq=adminDB.getFaq();
        if(faq.isEmpty()){
            System.out.println(PrintStatements.NO_FAQ_AVAIL);
            return;
        }
        System.out.println(PrintStatements.FAQ_FULL_FORM+"\n");
        for(int i=0;i<faq.size();i++){
            QA qa=faq.get(i);
            System.out.println((i+1)+". "+qa.getQuestion());
            System.out.println("   "+qa.getAnswer()+"\n");
        }
    }

    private void askQuestion(){
        System.out.println(PrintStatements.TYPE_YOUR_QUES);
        QA question=new QA(InputHelper.getStringInput());
        adminDB.addNewQuestion(question);
        System.out.println(PrintStatements.QUES_ADDED);
    }

}
