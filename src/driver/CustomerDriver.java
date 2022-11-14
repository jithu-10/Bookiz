package driver;

import admin.AdminDB;
import booking.Booking;
import booking.BookingDB;
import booking.CustomerBooking;
import customer.Customer;
import hotel.*;
import hotel.AddressDB;
import user.User;
import user.UserAuthenticationDB;
import user.UserDB;
import utility.QA;
import utility.InputHelper;
import utility.PrintStatements;
import utility.Validator;

import java.util.*;

public class CustomerDriver extends AbstractDriver {

    private final UserDB userDB=UserDB.getInstance();
    private final HotelDB hotelDB=HotelDB.getInstance();
    private final AdminDB adminDB=AdminDB.getInstance();
    private final AddressDB addressDB=AddressDB.getInstance();
    private final BookingDB bookingDB=BookingDB.getInstance();
    private final UserAuthenticationDB userAuthenticationDB=UserAuthenticationDB.getInstance();




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
            user=userDB.getCustomerByPhoneNumber_Mail(mail_or_phone);
        }
        return user;
    }

    @Override
    public void menu(User customer) {

        System.out.println(PrintStatements.WELCOME+customer.getUserName()+ PrintStatements.SMILE);
        do{
            System.out.println(PrintStatements.CUSTOMER_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(7,null);
            switch (choice){
                case 1:
                    bookHotel(customer);
                    break;
                case 2:
                    listBookings((Customer) customer);
                    break;
                case 3:
                    cancelBooking((Customer)customer);
                    break;
                case 4:
                    listCancelledBookings((Customer) customer);
                    break;
                case 5:
                    listFavoriteHotels((Customer) customer);
                    InputHelper.pressEnterToContinue();
                    break;
                case 6:
                    help();
                    break;
                case 7:
                    System.out.println(PrintStatements.SIGNED_OUT);
                    return;
            }

        }while(true);
    }


    //------------------------------------------------Customer Registration--------------------------------------------//
    @Override
    public void register() {

        User user= getUserDetails();
        if(user==null){
            return;
        }
        //userDB.addUser(user);
        userDB.addCustomer(user);
        System.out.println(PrintStatements.SIGN_UP_COMPLETED);
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
        userAuthenticationDB.addCustomerAuth(phoneNumber,email,password);
        return user;
    }

    private String getEmail(){
        String email;
        do{
            System.out.println(PrintStatements.ENTER_EMAIL);
            email=InputHelper.getEmailInput();

            if(userAuthenticationDB.isCustomerEmailExist(email)){
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
            if((userAuthenticationDB.isCustomerPhoneNumberExist(phoneNumber))){
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


        System.out.println(PrintStatements.ENTER_NO_OF+" Rooms Needed : ");
        int noOfRoomsNeeded=InputHelper.getInputWithinRange(10,"Only you can book up to 10 rooms at a time and no of rooms should be greater than 1");
        ArrayList<Integer> noOfGuests=new ArrayList<>();
        for(int i=0;i<noOfRoomsNeeded;i++){
            System.out.println("No of Guest in Room "+(i+1));
            int noOfGuest=InputHelper.getInputWithinRange(1,12,"Only up to 12 members can stay on single room");
            noOfGuests.add(noOfGuest);
        }
        CustomerBooking customerBooking=new CustomerBooking(checkInDate,checkOutDate);
        customerBooking.setNoOfRoomsBooked(noOfRoomsNeeded);
        customerBooking.setNoOfGuestsInEachRoom(noOfGuests);
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
        LinkedHashMap<Integer,ArrayList<Integer>> availableHotels=hotelDB.filterHotels(customerBooking,locality);
        ArrayList<Integer> availableHotelsID=new ArrayList<>(availableHotels.keySet());
        if(availableHotels.isEmpty()){
            System.out.println(PrintStatements.SOLD_OUT);
            return;
        }
        do{
            for(int i=0;i<availableHotelsID.size();i++){
                printHotelDetailsWithBooking(i,hotelDB.getHotelByID(availableHotelsID.get(i)),availableHotels.get(availableHotelsID.get(i)),customerBooking);
            }

            System.out.println("1."+ PrintStatements.SELECT_HOTEl);
            System.out.println("2."+ PrintStatements.GO_BACK);
            int selectChoice=InputHelper.getInputWithinRange(2,null);
            if(selectChoice==1){
                System.out.println(PrintStatements.ENTER_SNO_TO_SELECT_HOTEL);
                int choice=InputHelper.getInputWithinRange(availableHotels.size(),null);
                System.out.println("\n\n");
                ArrayList<Integer> roomsIDs=availableHotels.get(availableHotelsID.get(choice-1));
                customerBooking.setRoomIDs(roomsIDs);
                boolean booked=expandedHotelDetails(customerBooking,hotelDB.getHotelByID(availableHotelsID.get(choice-1)),(Customer) customer);
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


    private void printHotelDetailsWithBooking(int sno, Hotel hotel, ArrayList<Integer> roomsIndex, CustomerBooking customerBooking){
        printHotelDetails(sno,hotel);
        double listPrice=getListPrice(hotel,roomsIndex);
        double maxPrice=getMaxPrice(hotel,roomsIndex);
        double bedPrice=getBedPrice(hotel,roomsIndex,customerBooking);
        listPrice+=bedPrice;
        maxPrice+=bedPrice;
        int discount=(int)getDiscountPercent(listPrice,maxPrice);
        System.out.println("Price(per night) : ₹"+listPrice+" Actual Price(per night) : ₹"+maxPrice+"  "+discount+"%off\n\n");
    }

    private double getBedPrice(Hotel hotel,ArrayList<Integer> roomsIndex,CustomerBooking customerBooking){
        int bedPrice=0;
        for(int i=0;i<roomsIndex.size();i++){
            Room room=hotel.getRoomByID(roomsIndex.get(i));
            bedPrice+=room.getBedPrice()*customerBooking.getNoOfGuestsInEachRoom().get(i);
        }
        return bedPrice;

    }

    private double getListPrice(Hotel hotel,ArrayList<Integer> roomsIndex){
        int listPrice=0;
        for(int i=0;i<roomsIndex.size();i++){
            Room room=hotel.getRoomByID(roomsIndex.get(i));
            listPrice+=room.getRoomListPrice();
        }
        return listPrice;
    }

    private double getMaxPrice(Hotel hotel,ArrayList<Integer> roomsIndex){
        int maxPrice=0;
        for(int i=0;i<roomsIndex.size();i++){
            Room room=hotel.getRoomByID(roomsIndex.get(i));
            maxPrice+=room.getRoomMaxPrice();
        }
        return maxPrice;
    }

    private double getDiscountPercent(double listPrice,double maxPrice){
        double discount=maxPrice-listPrice;
        return (discount/maxPrice)*100;
    }


    private boolean expandedHotelDetails(CustomerBooking customerBooking,Hotel hotel,Customer customer){
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
        ArrayList<Integer> noOfGuestsInEachRoom=customerBooking.getNoOfGuestsInEachRoom();
        ArrayList<Room> hotelRooms=hotel.getRooms();
        double totalPrice=0;
        for(int i = 0; i<customerBooking.getRoomIDs().size(); i++){
            double roomPrice_oneDay=hotelRooms.get(i).getRoomListPrice()+(noOfGuestsInEachRoom.get(i)*hotelRooms.get(i).getBedPrice());
            double roomPrice_total=roomPrice_oneDay*customerBooking.getNoOfDays();
            System.out.println("Room "+(i+1)+" : ₹"+hotelRooms.get(i).getRoomMaxPrice()+"(org price) : ₹"+hotelRooms.get(i).getRoomListPrice()+"(discounted price) + ("+noOfGuestsInEachRoom.get(i)+"*"+hotelRooms.get(i).getBedPrice()+") = ₹"+roomPrice_oneDay+" * "+customerBooking.getNoOfDays()+"(days) : ₹"+roomPrice_total);
            totalPrice+=roomPrice_total;
        }
        System.out.println("\t\t"+"Total : ₹"+totalPrice);

        System.out.println("\n\n1.Book  2.Add To Favorite List 3.Back");
        int choice=InputHelper.getInputWithinRange(3,null);
        loop:do{
            switch (choice){
                case 1:
                    customerBooking.setTotalPrice(totalPrice);
                    boolean booked=paySlip(customerBooking);
                    if(booked){
                        bookingDB.addBooking(customerBooking);
                        customer.addBookings(customerBooking);
                        customerBooking.setHotelID(hotel.getHotelID());
                        bookedDetails(customerBooking,customer,hotel);

                        InputHelper.pressEnterToContinue();
                        return true;
                    }
                    break loop;
                case 2:
                    addToFavoriteList( customer,hotel);
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
        int choice=InputHelper.getInputWithinRange(3,null);
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
        hotel.addRoomBooking(customerBooking.getCheckInDate(),customerBooking.getCheckOutDate(),customerBooking.getRoomIDs());
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
        System.out.println("ROOMS ");
        for(int i=0;i<customerBooking.getNoOfGuestsInEachRoom().size();i++){
            System.out.println("Room "+(i+1)+" : "+customerBooking.getNoOfGuestsInEachRoom().get(i)+" Guests");
        }
        System.out.println("\nContact Information");
        System.out.println(" --> "+"+91-"+customer.getPhoneNumber());

    }



    //------------------------------------------------2.List Bookings--------------------------------------------------//

    private boolean listBookings(Customer customer){
        ArrayList<CustomerBooking>bookings=customer.getBookings();
        if(bookings.isEmpty()){
            System.out.println(PrintStatements.NO_BOOKINGS_AVAIL);
            return false;
        }
        bookingsWithDetail(bookings);
        return true;
    }

    private void bookingsWithDetail(ArrayList<CustomerBooking> bookings){
        System.out.println("\n"+ PrintStatements.BOOKING_LIST+"\n");
        for(int i=0;i<bookings.size();i++){
            Booking booking=bookings.get(i);
            Hotel hotel=hotelDB.getHotelByID(booking.getHotelID());
            System.out.println((i+1)+".Booking ID : "+booking.getBookingID());
            System.out.println("  Check-in Date : "+booking.getCheckInDateString()+"   Check-out Date : "+booking.getCheckOutDateString());
            System.out.println("  "+hotel.getHotelType()+" "+hotel.getHotelName());
            System.out.println("  No."+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet());
            System.out.println("  "+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity());
            System.out.println("  "+hotel.getAddress().getState()+"-"+hotel.getAddress().getPostalCode());
            System.out.println();
        }
    }

    //------------------------------------------------3.Cancel Bookings------------------------------------------------//

    private void cancelBooking(Customer customer){
        if(!listBookings(customer)){
            return;
        }
        System.out.println("1."+ PrintStatements.CANCEL_HOTEL);
        System.out.println("2."+ PrintStatements.GO_BACK);
        System.out.println(PrintStatements.ENTER_INPUT);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            System.out.println(PrintStatements.ENTER_SNO_TO_CANCEL_BOOKING);
            ArrayList<CustomerBooking> bookings=customer.getBookings();
            int bookingIDIndex=InputHelper.getInputWithinRange(bookings.size(),null);
            CustomerBooking customerBooking=bookings.get(bookingIDIndex-1);
            bookingDB.removeBookingWithBookingID(customerBooking.getBookingID());
            customer.removeBookings(customerBooking);
            customer.addCancelledBookings(customerBooking);
            Hotel hotel=hotelDB.getHotelByID(customerBooking.getHotelID());
            hotel.cancelRoomBooking(customerBooking.getCheckInDate(),customerBooking.getCheckOutDate(),customerBooking.getRoomIDs());
            System.out.println(PrintStatements.BOOKING_CANCELLED);
        }
    }

    //------------------------------------------------4.List Cancelled Bookings----------------------------------------//

    private void listCancelledBookings(Customer customer){
        ArrayList<CustomerBooking>bookings=customer.getCancelledBookings();
        if(bookings.isEmpty()){
            System.out.println(PrintStatements.NO_BOOKINGS_AVAIL);
            return ;
        }
        bookingsWithDetail(bookings);
    }

    //------------------------------------------------5.Favorite List--------------------------------------------------//
    private void addToFavoriteList(Customer customer,Hotel hotel){
        customer.addFavoriteHotels(hotel.getHotelID());
    }

    private void listFavoriteHotels(Customer customer){
        System.out.println(PrintStatements.FAVORITE_HOTELS);
        ArrayList<Integer> favoriteHotelsIDs=customer.getFavoriteHotelList();

        for(int i=0;i<favoriteHotelsIDs.size();i++){
            Hotel hotel=hotelDB.getHotelByID(favoriteHotelsIDs.get(i));
            if(hotel.getHotelApprovalStatus()!= HotelApprovalStatus.APPROVED){
                customer.removeFavoriteHotels(favoriteHotelsIDs.get(i));
            }
            else{
                printHotelDetails(i,hotel);
            }

        }
        if(favoriteHotelsIDs.isEmpty()){
            System.out.println(PrintStatements.NO_FAVORITE_HOTELS);
        }


    }

    //------------------------------------------------6.Help-----------------------------------------------------------//


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
