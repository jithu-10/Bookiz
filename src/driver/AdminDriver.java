package driver;

import admin.AdminDB;
import booking.Booking;
import booking.BookingDB;
import hotel.*;
import user.User;
import user.UserAuthenticationDB;
import user.UserDB;
import user.UserType;
import utility.InputHelper;
import utility.Printer;
import utility.QA;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AdminDriver implements Driver {
    private static final AdminDriver adminDriver=new AdminDriver();
    private final UserAuthenticationDB userAuthenticationDB=UserAuthenticationDB.getInstance();
    private final AdminDB adminDB=AdminDB.getInstance();
    private final HotelDB hotelDB=HotelDB.getInstance();
    private final AmenityDB amenityDB=AmenityDB.getInstance();
    private final UserDB userDB=UserDB.getInstance();
    private final BookingDB bookingDB= BookingDB.getInstance();

    private AdminDriver(){

    }
    public static AdminDriver getInstance(){
        return adminDriver;
    }

    @Override
    public void startDriver() {
        System.out.println("Admin");
        User admin;
        if((admin=signIn())!=null){
            System.out.println(Printer.SIGNED_IN);
            menu(admin);
        }
        else{
            System.out.println(Printer.INVALID_CREDENTIALS);
        }

    }

    @Override
    public User signIn() {
        System.out.println(Printer.SIGN_IN);
        System.out.println(Printer.ENTER_USERNAME);
        String userName= InputHelper.getStringInput();
        System.out.println(Printer.ENTER_PASSWORD);
        String passWord= InputHelper.getStringInput();
        if(userAuthenticationDB.authenticateAdmin(userName,passWord)){
            return adminDB.getAdmin();
        }
        return null;
    }

    @Override
    public void menu(User user) {

        do{
            System.out.println(Printer.ADMIN_MENU);
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice =InputHelper.getInputWithinRange(10,null);
            switch (choice){
                case 1:
                    approveHotels();
                    break;
                case 2:
                    listRegisteredHotels();
                    break;
                case 3:
                    removeRegisteredHotels();
                    break;
                case 4:
                    setTermsAndConditions();
                    break;
                case 5:
                    setPriceforRooms();
                    break;
                case 6:
                    listAllBookings();
                    break;
                case 7:
                    addAmenity();
                    break;
                case 8:
                    removeAmenity();
                    break;
                case 9:
                    giveSolution();
                    break;
                case 10:
                    System.out.println(Printer.SIGNED_OUT);
                    return;
            }

        }while(true);

    }

    @Override
    public void register() {

    }

    //----------------------------------------------1.Approve Hotels------------------------------------------------------//
    private void approveHotels(){
        listHotelsRequestedforApproval();
    }

    private void listHotelsRequestedforApproval(){
        ArrayList<Hotel> hotelsRequested=hotelDB.getHotelListByStatus(HotelStatus.ON_PROCESS);
        hotelsRequested.addAll(hotelDB.getHotelListByStatus(HotelStatus.REMOVED_RE_PROCESS));
        if(hotelsRequested.size()==0){
            System.out.println(Printer.NO_SUCH_REQUEST_AVAILABLE_NOW);
            return;
        }
        for(int i=0;i<hotelsRequested.size();i++){
            Hotel hotel=hotelsRequested.get(i);
            System.out.println((i+1)+" . "+hotel.getHotelName());
            System.out.println("\t"+Printer.NUMBER_NO+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet());
            System.out.println("\t"+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity());
            System.out.println("\t"+hotel.getAddress().getState()+","+hotel.getAddress().getPostalCode());

            System.out.println(Printer.PHONE_NUMBER_SHORT+hotel.getPhoneNumber());
            System.out.println(Printer.TOTAL_NO_OF_ROOMS+hotel.getTotalNumberOfRooms());
            System.out.println(Printer.SINGLE_BED_COUNT+hotel.getTotalNumberOfRooms(RoomType.SINGLE_BED_ROOM));
            System.out.println(Printer.DOUBLE_BED_COUNT+hotel.getTotalNumberOfRooms(RoomType.DOUBLE_BED_ROOM));
            System.out.println(Printer.SUITE_ROOM_COUNT+hotel.getTotalNumberOfRooms(RoomType.SUITE_ROOM));
            System.out.println(Printer.AMENITY_PERCENT+(int)hotel.getTotalAmenityPercent()+"%");
            System.out.println(Printer.REQUESTED_HOTEL_TYPE+hotel.getHotelType());
            System.out.println("\n\n");
        }
        int options=selectOrExitOptions();
        if(options==2){
            return;
        }
        System.out.println("\n"+Printer.ENTER_SNO_TO_SELECT_HOTEL);
        int choice = InputHelper.getInputWithinRange(hotelsRequested.size(),null);
        int approval=approvalOptions();
        Hotel hotel=hotelsRequested.get(choice-1);
        if(approval==1){
            if(changeHotelTypeOption()==1){
                changeHotelTypeSpecification(hotel);
            }
            hotelDB.approveHotel(hotel);
            setPriceforHotelRooms(hotel);
            hotelsRequested.remove(choice-1);
        }
        else{
            if(hotel.getHotelApproveStatus()==HotelStatus.ON_PROCESS) {
                hotel.setHotelApproveStatus(HotelStatus.REJECTED);
            }
            else{
                hotel.setHotelApproveStatus(HotelStatus.REMOVED);
            }

            hotelsRequested.remove(choice-1);
        }
    }

    private int changeHotelTypeOption(){
        System.out.println("1. Change Hotel Type");
        System.out.println("2. Skip");
        return InputHelper.getInputWithinRange(2,null);
    }

    private int selectOrExitOptions(){

        System.out.println(Printer.SELECT_FROM_LIST_MENU);
        System.out.println(Printer.ENTER_INPUT);
        return InputHelper.getInputWithinRange(2,null);
    }

    private int approvalOptions(){
        System.out.println(Printer.ACCEPT_REJECT_OPTION);
        System.out.println(Printer.ENTER_INPUT);

        return InputHelper.getInputWithinRange(2,null);
    }

    private void setPriceforHotelRooms(Hotel hotel){

        System.out.println(Printer.SET_PRICE_FOR_HOTEL_ROOMS);
        double basePrice=hotel.getSingleBedRoomBasePrice();
        double maxPrice=hotel.getSingleBedRoomMaxPrice();
        System.out.println(RoomType.SINGLE_BED_ROOM +"->"+Printer.BASE_PRICE+basePrice+Printer.MAX_PRICE+maxPrice);
        System.out.println(Printer.SET_LIST_PRICE);
        double listPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setSingleBedRoomListPrice(listPrice);

        basePrice=hotel.getDoubleBedRoomBasePrice();
        maxPrice=hotel.getDoubleBedRoomMaxPrice();
        System.out.println(RoomType.DOUBLE_BED_ROOM +"->"+Printer.BASE_PRICE+basePrice+Printer.MAX_PRICE+maxPrice);
        System.out.println(Printer.SET_LIST_PRICE);
        listPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setDoubleBedRoomListPrice(listPrice);

        basePrice=hotel.getSuiteRoomBasePrice();
        maxPrice=hotel.getSuiteRoomMaxPrice();
        System.out.println(RoomType.SUITE_ROOM +"->"+Printer.BASE_PRICE+basePrice+Printer.MAX_PRICE+maxPrice);
        System.out.println(Printer.SET_LIST_PRICE);
        listPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setSuiteRoomListPrice(listPrice);
    }

    private void changeHotelTypeSpecification(Hotel hotel){
        System.out.println("Select Hotel Type : ");
        System.out.println("1."+"ELITE HOTEL - "+ HotelType.TOWNHOUSE);
        System.out.println("2."+"PREMIUM HOTEL - "+HotelType.COLLECTIONZ);
        System.out.println("3."+"STANDARD HOTEL - "+HotelType.SPOTZ);
        System.out.println("4."+"Go Back");
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
                hotel.setHotelType();
                break;

        }
    }

    private double createCurrentPrice(double basePrice,double maxPrice){
        double listPrice;
        do{
            listPrice=InputHelper.getDoubleInput();
            if(listPrice<basePrice||listPrice>maxPrice){
                System.out.println(Printer.LIST_PRICE_CONDITION);
                System.out.println(Printer.SET_LIST_PRICE);
            }
            else{
                break;
            }

        }while(true);
        return listPrice;
    }

//---------------------------------------------2.List Registered Hotels------------------------------------------------//
    private void listRegisteredHotels(){
        ArrayList<Hotel> hotels = hotelDB.getHotelListByStatus(HotelStatus.APPROVED);
        if(hotels.size()==0){
            System.out.println(Printer.NO_HOTEL_AVAIL);
            return;
        }
        System.out.println("ID\t Hotel Name\t\t\t\tLocality \t\t\t\tRooms\tTypeofRoom\n");
        for(int i=0;i<hotels.size();i++){
            Hotel hotel=hotels.get(i);
            System.out.printf("%-4s %-20s %-25s %-7s %-7s",hotel.getHotelID(),hotel.getHotelName(),hotel.getAddress().getLocality()+","+hotel.getAddress().getCity(),hotel.getTotalNumberOfRooms(),hotel.getHotelType());
            System.out.println();

        }
    }
//--------------------------------------------3.Remove Registered Hotels-----------------------------------------------//

    private void removeRegisteredHotels(){
        System.out.println(Printer.ENTER_HOTEL_ID);
        int hotelId=InputHelper.getIntegerInput();
        if(hotelDB.removeHotels(hotelId)){
            System.out.println(Printer.HOTEL_REMOVED);
        }
        else{
            System.out.println(Printer.NO_APPROVED_HOTEL_FOUND_WITH_ID);
        }

    }

//-------------------------------------------4.Set Terms and Conditions------------------------------------------------//

    private void setTermsAndConditions(){
        loop:do{
            System.out.println("1."+Printer.UPDATE_TERMS);
            System.out.println("2."+Printer.VIEW_TERMS);
            System.out.println("3."+Printer.GO_BACK);
            System.out.println(Printer.ENTER_INPUT);
            int choice=InputHelper.getInputWithinRange(3,null);

            switch (choice){
                case 1:
                    //  Path : /Users/jithin-15752/Java Programs/Notes.txt
                    adminDB.addTermsAndConditions(InputHelper.getFileInput());
                    break;
                case 2:
                    if(adminDB.getTermsAndConditions().isEmpty()){
                        System.out.println(Printer.NOT_AVAIL);
                        break;
                    }
                    System.out.println("\n");
                    InputHelper.printFile(adminDB.getTermsAndConditions());
                    break;
                case 3:
                    break loop;
            }
        }while(true);

    }


//-------------------------------------------5.List All Bookings-------------------------------------------------------//


    private void listAllBookings(){

        ArrayList<Booking> bookings= bookingDB.getBookings();
        if(bookings.isEmpty()){
            System.out.println(Printer.NO_BOOKINGS_AVAIL);
            return;
        }
        System.out.println(Printer.BOOKING_LIST);
        for(int i=0;i<bookings.size();i++){
            Booking booking=bookings.get(i);
            User customer=userDB.getUserByID(booking.getCustomerID(),UserType.CUSTOMER);
            Hotel hotel=hotelDB.getHotelByID(booking.getHotelID());
            System.out.println((i+1)+". "+Printer.BOOKING_ID+booking.getBookingID());
            System.out.println(Printer.CHECK_IN_DATE+booking.getCheckInDateString());
            System.out.println(Printer.CHECK_OUT_DATE+booking.getCheckOutDateString());
            System.out.println(Printer.CUSTOMER_NAME+customer.getUserName());
            System.out.println(Printer.CONTACT+customer.getPhoneNumber());
            System.out.println(Printer.HOTEL_NAME+hotel.getHotelName()+" "+Printer.HOTEL_ID+hotel.getHotelID());
            System.out.println();
        }
    }
//-------------------------------------------6.Set Price for Rooms of Hotels-------------------------------------------//

    private void setPriceforRooms(){
        System.out.println(Printer.ROOM_PRICE_SET_MENU);
        System.out.println(Printer.ENTER_INPUT);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            changeHotelPriceByID();
        }
        else if(choice==2){
            listHotelsWhichChangedPrice();
        }
    }

    private void changeHotelPriceByID(){
        System.out.println("\n"+Printer.HOTEL_ID);
        int hotelID=InputHelper.getInputWithinRange(Integer.MAX_VALUE,"Please enter valid Hotel ID");
        Hotel hotel=hotelDB.getHotelByID(hotelID);
        setPrice(hotel);
    }

    private void setPrice(Hotel hotel){


        if(hotel!=null){
            System.out.println("Hotel ID : "+hotel.getHotelID());
            System.out.println("Hotel Name : "+hotel.getHotelName());
            System.out.println("Hotel Address : No."+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet()+","+hotel.getAddress().getLocality());
            System.out.println("\t"+hotel.getAddress().getCity()+","+hotel.getAddress().getState());
            System.out.println("Postal Code : "+hotel.getAddress().getPostalCode());
            System.out.println("No of Rooms : "+hotel.getTotalNumberOfRooms());
            System.out.println("Set Price for Rooms");
            System.out.println("SingleBed Rooms -> Base Price : "+hotel.getSingleBedRoomBasePrice()+" Max Price : "+hotel.getSingleBedRoomMaxPrice()+" Current List Price : "+hotel.getSingleBedRoomListPrice());
            double listPrice;
            if((listPrice=priceChangeOptions(hotel.getSingleBedRoomBasePrice(),hotel.getSingleBedRoomMaxPrice()))!=-1){
                hotel.setSingleBedRoomListPrice(listPrice);
            }
            System.out.println("DoubleBed Rooms -> Base Price : "+hotel.getDoubleBedRoomBasePrice()+" Max Price : "+hotel.getDoubleBedRoomMaxPrice()+" Current List Price : "+hotel.getDoubleBedRoomListPrice());
            if((listPrice=priceChangeOptions(hotel.getDoubleBedRoomBasePrice(),hotel.getDoubleBedRoomMaxPrice()))!=-1){
                hotel.setDoubleBedRoomListPrice(listPrice);
            }
            System.out.println("Suite Rooms -> Base Price : "+hotel.getSuiteRoomBasePrice()+" Max Price : "+hotel.getSuiteRoomMaxPrice()+" Current List Price : "+hotel.getSuiteRoomListPrice());
            if((listPrice=priceChangeOptions(hotel.getSuiteRoomBasePrice(),hotel.getSuiteRoomMaxPrice()))!=-1){
                hotel.setSuiteRoomListPrice(listPrice);
            }
            adminDB.removeHotelfromPriceUpdatedHotelList(hotel.getHotelID());
            return;
        }
        System.out.println("No Hotel With Respected ID Found");

    }

    private double priceChangeOptions(double basePrice,double maxPrice){
        System.out.println("1.Change List Price");
        System.out.println("2.Skip");
        System.out.println("Enter Input :");
        int choice = InputHelper.getInputWithinRange(2,null);
        if(choice==2){
            return -1;
        }
        else{
            System.out.println("Set List Price : ");
            return createCurrentPrice(basePrice,maxPrice);
        }

    }

    private void listHotelsWhichChangedPrice(){
        LinkedHashSet<Integer> hotelList= adminDB.getPriceUpdatedHotelList();
        if(hotelList.isEmpty()){
            System.out.println(Printer.NO_HOTEL_CHANGED_PRICE);
            return;
        }
        System.out.println("HOTEL ID |       HOTEL DETAILS     |    HOTEL TYPE ");
        for(Integer id:hotelList){
            Hotel hotel=hotelDB.getHotelByID(id);
            System.out.println("  "+hotel.getHotelID()+"  "+hotel.getHotelName()+" , "+hotel.getAddress().getCity()+"  "+hotel.getHotelType());
        }
        System.out.println("\n\n"+Printer.ENTER_HOTEL_ID);
        int hotelId=InputHelper.getInputWithinRange(Integer.MAX_VALUE,"There is no hotel with Negative ID's");
        if(!hotelList.contains(hotelId)){
            System.out.println("Hotel With ID:"+hotelId+" didn't changed their prices recently");
            return;
        }
        Hotel hotel=hotelDB.getHotelByID(hotelId);
        setPrice(hotel);
    }

    //-----------------------------------------------7.Add Amenity----------------------------------------------------//

    private void addAmenity(){
        System.out.println("Enter Amenity Name : ");
        String amenityName=InputHelper.getStringInput().toUpperCase();
        System.out.println("Enter Amenity Points : ");
        int amenityPoints=InputHelper.getInputWithinRange(100,"Maximum 100 points can be given");
        Amenity amenity= new Amenity(amenityName,amenityPoints);
        amenityDB.addAmenity(amenity);

    }
    //----------------------------------------------8.Remove Amenity---------------------------------------------------//

    private void removeAmenity(){
        System.out.println("Amenity List \n");
        int sno=1;
        for(Amenity amenity:amenityDB.getAmenities()){
            System.out.println(sno+". "+amenity.getName());
            sno++;
        }
        System.out.println("Enter s.no to remove amenity : ");
        int choice=InputHelper.getInputWithinRange(amenityDB.getAmenities().size(),null);
        amenityDB.removeAmenity(choice-1);

    }

    //-----------------------------------------------9.Give Solutions--------------------------------------------------//

    private void giveSolution(){
        ArrayList<QA> newQuestions= adminDB.getNewQuestions();
        if(newQuestions.isEmpty()){
            System.out.println(Printer.NO_NEW_QUES);
            return;
        }
        System.out.println("Questions : \n");
        for(int i=0;i< newQuestions.size();i++){
            QA question=newQuestions.get(i);
            System.out.println((i+1)+". "+question.getQuestion()+"\n");
        }
        System.out.println("1."+Printer.SELECT_QUES);
        System.out.println("2."+Printer.GO_BACK);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            System.out.println(Printer.ENTER_QUES_NO);
            int qNo=InputHelper.getInputWithinRange(newQuestions.size(),null);
            QA question=newQuestions.get(qNo-1);
            answerOrDeleteQuestion(question);
        }

    }

    private void answerOrDeleteQuestion(QA question){
        System.out.println("1.Answer Question");
        System.out.println("2.Remove Question");
        System.out.println("3.Go Back");
        int choice=InputHelper.getInputWithinRange(3,null);
        switch (choice){
            case 1:
                answerQuestion(question);
                break;
            case 2:
                adminDB.removeNewQuestion(question);
                break;
            case 3:
                break;
        }
    }

    private void answerQuestion(QA question){
        System.out.println("Answer : ");
        String answer=InputHelper.getStringInput();
        question.setAnswer(answer);
        adminDB.addFaqQuestion(question);
        adminDB.removeNewQuestion(question);
    }


}
