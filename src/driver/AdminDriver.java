package driver;

import admin.AdminDB;
import booking.Booking;
import booking.BookingDB;
import hotel.*;
import user.User;
import user.UserAuthenticationDB;
import user.UserDB;
import utility.InputHelper;
import utility.PrintStatements;
import utility.QA;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AdminDriver extends AbstractDriver {
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
            System.out.println(PrintStatements.SIGNED_IN);
            menu(admin);
        }
        else{
            System.out.println(PrintStatements.INVALID_CREDENTIALS);
        }

    }

    @Override
    public User signIn() {
        System.out.println(PrintStatements.SIGN_IN);
        System.out.println(PrintStatements.ENTER_USERNAME);
        String userName= InputHelper.getStringInput();
        System.out.println(PrintStatements.ENTER_PASSWORD);
        String passWord= InputHelper.getStringInput();
        if(userAuthenticationDB.authenticateAdmin(userName,passWord)){
            return adminDB.getAdmin();
        }
        return null;
    }

    @Override
    public void menu(User user) {

        do{
            System.out.println(PrintStatements.ADMIN_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
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
                    System.out.println(PrintStatements.SIGNED_OUT);
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

        ArrayList<Integer> hotelsRequestedID=hotelDB.getHotelListByStatus(HotelApprovalStatus.ON_PROCESS);
        hotelsRequestedID.addAll(hotelDB.getHotelListByStatus(HotelApprovalStatus.REMOVED_RE_PROCESS));
        if(hotelsRequestedID.size()==0){
            System.out.println(PrintStatements.NO_SUCH_REQUEST_AVAILABLE_NOW);
            return;
        }
        for(int i=0;i<hotelsRequestedID.size();i++){
            Hotel hotel=hotelDB.getHotelByID(hotelsRequestedID.get(i));
            System.out.println((i+1)+" . "+hotel.getHotelName());
            System.out.println("\t"+ PrintStatements.NUMBER_NO+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet());
            System.out.println("\t"+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity());
            System.out.println("\t"+hotel.getAddress().getState()+","+hotel.getAddress().getPostalCode());

            System.out.println(PrintStatements.PHONE_NUMBER_SHORT+hotel.getPhoneNumber());
            System.out.println(PrintStatements.TOTAL_NO_OF_ROOMS+hotel.getTotalNumberOfRooms());
            System.out.println(PrintStatements.AMENITY_PERCENT+(int)hotel.getTotalAmenityPercent()+"%");
            System.out.println(PrintStatements.REQUESTED_HOTEL_TYPE+hotel.getHotelType());
            System.out.println("\n\n");
        }
        int options=selectOrExitOptions();
        if(options==2){
            return;
        }
        System.out.println("\n"+ PrintStatements.ENTER_SNO_TO_SELECT_HOTEL);
        int choice = InputHelper.getInputWithinRange(hotelsRequestedID.size(),null);
        int approval=approvalOptions();

        Hotel hotel=hotelDB.getHotelByID(hotelsRequestedID.get(choice-1));
        if(approval==1){
            if(changeHotelTypeOption()==1){
                changeHotelTypeSpecification(hotel);
            }
            hotelDB.approveHotel(hotel);
            setPriceforHotelRooms(hotel);
            hotelsRequestedID.remove(choice-1);
        }
        else{
            if(hotel.getHotelApprovalStatus()== HotelApprovalStatus.ON_PROCESS) {
                hotel.setHotelApprovalStatus(HotelApprovalStatus.REJECTED);
            }
            else{
                hotel.setHotelApprovalStatus(HotelApprovalStatus.REMOVED);
            }

            hotelsRequestedID.remove(choice-1);
        }
    }

    private int changeHotelTypeOption(){
        System.out.println("1. Change Hotel Type");
        System.out.println("2. Skip");
        return InputHelper.getInputWithinRange(2,null);
    }

    private int selectOrExitOptions(){

        System.out.println(PrintStatements.SELECT_FROM_LIST_MENU);
        System.out.println(PrintStatements.ENTER_INPUT);
        return InputHelper.getInputWithinRange(2,null);
    }

    private int approvalOptions(){
        System.out.println(PrintStatements.ACCEPT_REJECT_OPTION);
        System.out.println(PrintStatements.ENTER_INPUT);

        return InputHelper.getInputWithinRange(2,null);
    }

    private void setPriceforHotelRooms(Hotel hotel){

        do{
            System.out.println(PrintStatements.SET_PRICE_FOR_HOTEL_ROOMS);
            ArrayList<Room> rooms=hotel.getRooms();
            for(int i=0;i<rooms.size();i++){
                Room room=rooms.get(i);
                System.out.println((i+1)+". Max Guest : "+ room.getRoomCapacity()+" Base Price : "+room.getRoomBasePrice()+" Max Price : "+room.getRoomMaxPrice()+" List Price : "+room.getRoomListPrice());
            }
            System.out.println("1.Change Room List Price");
            System.out.println("2.Back");
            int options=InputHelper.getInputWithinRange(2,null);
            if(options==2){
                return;
            }
            System.out.println("Enter Room No. to change list price : ");
            int choice=InputHelper.getInputWithinRange(rooms.size(),null);
            Room room=rooms.get(choice-1);
            System.out.println("Base Price : "+room.getRoomBasePrice()+" Max Price : "+room.getRoomMaxPrice()+" List Price : "+room.getRoomListPrice());
            double listPrice=createCurrentPrice(room.getRoomBasePrice(),room.getRoomMaxPrice());
            room.setRoomListPrice(listPrice);

        }while (true);

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
                System.out.println(PrintStatements.LIST_PRICE_CONDITION);
                System.out.println(PrintStatements.SET_LIST_PRICE);
            }
            else{
                break;
            }

        }while(true);
        return listPrice;
    }

//---------------------------------------------2.List Registered Hotels------------------------------------------------//
    private void listRegisteredHotels(){
        ArrayList<Integer> hotels = hotelDB.getHotelListByStatus(HotelApprovalStatus.APPROVED);
        if(hotels.size()==0){
            System.out.println(PrintStatements.NO_HOTEL_AVAIL);
            return;
        }
        System.out.println("ID\t Hotel Name\t\t\t\tLocality \t\t\t\tRooms\tTypeofRoom\n");
        for(int i=0;i<hotels.size();i++){
            Hotel hotel=hotelDB.getHotelByID(hotels.get(i));
            System.out.printf("%-4s %-20s %-25s %-7s %-7s",hotel.getHotelID(),hotel.getHotelName(),hotel.getAddress().getLocality()+","+hotel.getAddress().getCity(),hotel.getTotalNumberOfRooms(),hotel.getHotelType());
            System.out.println();

        }
    }
//--------------------------------------------3.Remove Registered Hotels-----------------------------------------------//

    private void removeRegisteredHotels(){
        System.out.println(PrintStatements.ENTER_HOTEL_ID);
        int hotelId=InputHelper.getIntegerInput();
        if(hotelDB.removeHotels(hotelId)){
            System.out.println(PrintStatements.HOTEL_REMOVED);
        }
        else{
            System.out.println(PrintStatements.NO_APPROVED_HOTEL_FOUND_WITH_ID);
        }

    }

//-------------------------------------------4.Set Terms and Conditions------------------------------------------------//

    private void setTermsAndConditions(){
        loop:do{
            System.out.println("1."+ PrintStatements.UPDATE_TERMS);
            System.out.println("2."+ PrintStatements.VIEW_TERMS);
            System.out.println("3."+ PrintStatements.GO_BACK);
            System.out.println(PrintStatements.ENTER_INPUT);
            int choice=InputHelper.getInputWithinRange(3,null);

            switch (choice){
                case 1:
                    //  Path : /Users/jithin-15752/Java Programs/Notes.txt
                    adminDB.addTermsAndConditions(InputHelper.getFileInput());
                    System.out.println("Terms and Conditions Updated\n");
                    break;
                case 2:
                    if(adminDB.getTermsAndConditions().isEmpty()){
                        System.out.println(PrintStatements.NOT_AVAIL);
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
            System.out.println(PrintStatements.NO_BOOKINGS_AVAIL);
            return;
        }
        System.out.println(PrintStatements.BOOKING_LIST);
        for(int i=0;i<bookings.size();i++){
            Booking booking=bookings.get(i);
            //User customer=userDB.getUserByID(booking.getCustomerID(),UserType.CUSTOMER);
            User customer=userDB.getCustomerByID(booking.getCustomerID());
            Hotel hotel=hotelDB.getHotelByID(booking.getHotelID());
            System.out.println((i+1)+". "+ PrintStatements.BOOKING_ID+booking.getBookingID());
            System.out.println(PrintStatements.CHECK_IN_DATE+booking.getCheckInDateString());
            System.out.println(PrintStatements.CHECK_OUT_DATE+booking.getCheckOutDateString());
            System.out.println(PrintStatements.CUSTOMER_NAME+customer.getUserName());
            System.out.println(PrintStatements.CONTACT+customer.getPhoneNumber());
            System.out.println(PrintStatements.HOTEL_NAME+hotel.getHotelName()+" "+ PrintStatements.HOTEL_ID+hotel.getHotelID());
            System.out.println();
        }
    }
//-------------------------------------------6.Set Price for Rooms of Hotels-------------------------------------------//

    private void setPriceforRooms(){
        System.out.println(PrintStatements.ROOM_PRICE_SET_MENU);
        System.out.println(PrintStatements.ENTER_INPUT);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            changeHotelPriceByID();
        }
        else if(choice==2){
            listHotelsWhichChangedPrice();
        }
    }

    private void changeHotelPriceByID(){
        System.out.println("\n"+ PrintStatements.HOTEL_ID);
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
            setPriceforHotelRooms(hotel);
            adminDB.removeHotelFromPriceUpdatedHotelList(hotel.getHotelID());
            return;
        }
        System.out.println("No Hotel With Respected ID Found");

    }


    private void listHotelsWhichChangedPrice(){
        LinkedHashSet<Integer> hotelList= adminDB.getPriceUpdatedHotelList();
        if(hotelList.isEmpty()){
            System.out.println(PrintStatements.NO_HOTEL_CHANGED_PRICE);
            return;
        }
        System.out.println("HOTEL ID |       HOTEL DETAILS     |    HOTEL TYPE ");
        for(Integer id:hotelList){
            Hotel hotel=hotelDB.getHotelByID(id);
            System.out.println("  "+hotel.getHotelID()+"  "+hotel.getHotelName()+" , "+hotel.getAddress().getCity()+"  "+hotel.getHotelType());
        }
        System.out.println("\n\n"+ PrintStatements.ENTER_HOTEL_ID);
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
            System.out.println(PrintStatements.NO_NEW_QUES);
            return;
        }
        System.out.println("Questions : \n");
        for(int i=0;i< newQuestions.size();i++){
            QA question=newQuestions.get(i);
            System.out.println((i+1)+". "+question.getQuestion()+"\n");
        }
        System.out.println("1."+ PrintStatements.SELECT_QUES);
        System.out.println("2."+ PrintStatements.GO_BACK);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            System.out.println(PrintStatements.ENTER_QUES_NO);
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
