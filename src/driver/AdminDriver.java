package driver;

import admin.Admin;
import admin.AdminDB;
import booking.Booking;
import booking.BookingDB;
import customer.Customer;
import customer.CustomerDB;
import hotel.Hotel;
import hotel.HotelDB;
import hotel.RoomType;
import user.User;
import utility.InputHelper;
import utility.Printer;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class AdminDriver implements Driver {

    static final AdminDriver adminDriver=new AdminDriver();
    private final AdminDB adminDB=AdminDB.getInstance();
    private final HotelDB hotelDB=HotelDB.getInstance();
    private final CustomerDB customerDB= CustomerDB.getInstance();
    private final BookingDB bookingDB= BookingDB.getInstance();

    private AdminDriver(){

    }

    static AdminDriver getInstance(){
        return adminDriver;
    }

    @Override
    public void startDriver() {
        System.out.println("Admin Driver");
        Admin admin;
        if((admin=(Admin)signIn())!=null){
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
        if(Admin.checkAuthentication(userName,passWord)){
            return Admin.getInstance();
        }
        return null;
    }

    @Override
    public void menu(User user) {

        do{
            System.out.println("1.List of hotels which have requested for approval.");
            System.out.println("2.List of hotels registered");
            System.out.println("3.Remove Hotels");
            System.out.println("4.Set Terms and Conditions");
            System.out.println("5.Set Price for hotel rooms");
            System.out.println("6.List All Bookings");
            System.out.println("7.Sign Out");
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice =InputHelper.getIntegerInput();
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
                    /*TODO Set Terms and Conditions*/
                    break;
                case 5:
                    setPriceforRooms();
                    break;
                case 6:
                    listAllBookings();
                    break;
                case 7:
                    System.out.println("Signing Out....");
                    return;
                default:
                    System.out.println("Enter Input only from given option");
            }

        }while(true);

    }

    @Override
    public void register() {

    }

    //----------------------------------------------1.Approve Hotels------------------------------------------------------//
    void approveHotels(){
        listHotelsRequestedforApproval();
    }
    
    void listHotelsRequestedforApproval(){
        LinkedList<Hotel> hotelsRequested= hotelDB.getHotelsRegisteredForApproval();
        if(hotelsRequested.size()==0){
            System.out.println("No Such Requests available now..");
            return;
        }
        for(int i=0;i<hotelsRequested.size();i++){
            Hotel hotel=hotelsRequested.get(i);
            System.out.println((i+1)+" . "+hotel.getHotelName());
            System.out.println("\tNo."+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet());
            System.out.println("\t"+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity());
            System.out.println("\t"+hotel.getAddress().getState()+","+hotel.getAddress().getPostalCode());

            System.out.println("Ph.No : "+hotel.getPhoneNumber());
            System.out.println("Total No of Rooms : "+hotel.getTotalNumberofRooms());
            System.out.println("Single Bed Rooms : "+hotel.getNumberofSingleBedRooms());
            System.out.println("Double Bed Rooms : "+hotel.getNumberofDoubleBedRooms());
            System.out.println("Suite Rooms : "+hotel.getNumberofSuiteRooms());
            System.out.println("Total Amenity Points : "+hotel.getTotalAmenityPoints());
            System.out.println("\n\n");
        }
        int options=selectOrExitOptions();
        if(options==2){
            return;
        }
        System.out.println("\nEnter S.No to Select Hotel : ");
        int choice = InputHelper.getInputWithinRange(hotelsRequested.size(),null);
        int approval=approvalOptions();
        if(approval==1){
            Hotel hotel=hotelsRequested.get(choice-1);
            hotelDB.addApprovedHotelList(hotel);
            setPriceforHotelRooms(hotel);
            hotel.setHotelType();
            //hotel.setHotelId(HotelDB.generateID()); NO NEED
            hotelsRequested.remove(choice-1);
        }
        else{
            hotelsRequested.remove(choice-1);
        }
    }

    int selectOrExitOptions(){
        System.out.println("1.Select From List");
        System.out.println("2.Back");
        System.out.println("Enter Input : ");
        return InputHelper.getInputWithinRange(2,null);
    }

    int approvalOptions(){
        System.out.println("1.Accept");
        System.out.println("2.Reject");
        System.out.println("Enter Input : ");

        return InputHelper.getInputWithinRange(2,null);
    }

    void setPriceforHotelRooms(Hotel hotel){

        System.out.println("Set Price For Hotel Rooms");
        double basePrice=hotel.getSingleBedRoomBasePrice();
        double maxPrice=hotel.getSingleBedRoomMaxPrice();
        System.out.println(RoomType.SINGLEBEDROOM+"-> Base Price : "+basePrice+" Max Price : "+maxPrice);
        System.out.println("Set List Price : ");
        double listPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setSingleBedRoomListPrice(listPrice);

        basePrice=hotel.getDoubleBedRoomBasePrice();
        maxPrice=hotel.getDoubleBedRoomMaxPrice();
        System.out.println(RoomType.DOUBLEBEDROOM+"-> Base Price : "+basePrice+" Max Price : "+maxPrice);
        System.out.println("Set List Price : ");
        listPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setDoubleBedRoomListPrice(listPrice);

        basePrice=hotel.getSuiteRoomBasePrice();
        maxPrice=hotel.getSuiteRoomMaxPrice();
        System.out.println(RoomType.SUITEROOM+"-> Base Price : "+basePrice+" Max Price : "+maxPrice);
        System.out.println("Set List Price : ");
        listPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setSuiteRoomListPrice(listPrice);
    }

    double createCurrentPrice(double basePrice,double maxPrice){
        double listPrice;
        do{
            listPrice=InputHelper.getDoubleInput();
            if(listPrice<basePrice||listPrice>maxPrice){
                System.out.println("List Price Should be Greater than Base Price and Lesser than Max Price");
                System.out.println("Set List Price : ");
            }
            else{
                break;
            }

        }while(true);
        return listPrice;
    }

//---------------------------------------------2.List Registered Hotels------------------------------------------------//
    void listRegisteredHotels(){
        if(hotelDB.getRegisteredHotelList().size()==0){
            System.out.println("No Hotels Available Now");
            return;
        }
        System.out.println("ID\t  Hotel Name  \t Locality \t TotalRooms \t TypeofRoom\n");
        for(Hotel hotel:hotelDB.getRegisteredHotelList()){
            System.out.println(hotel.getHotelID()+"\t"+hotel.getHotelName()+"\t"+hotel.getAddress().getLocality()+","+hotel.getAddress().getCity()+"\t"+hotel.getTotalNumberofRooms()+"\t"+hotel.getHotelType());
        }
    }
//--------------------------------------------3.Remove Registered Hotels-----------------------------------------------//

    void removeRegisteredHotels(){
        System.out.println("Enter Hotel ID : ");
        int hotelId=InputHelper.getIntegerInput();
        if(hotelDB.removeHotels(hotelId)){
            System.out.println("Hotel Removed Successfully");
        }
        else{
            System.out.println("No Hotel Found with Id");
        }

    }

//-------------------------------------------4.Set Terms and Conditions------------------------------------------------//
    /*TODO Space for creating file base setting terms and conditions*/

//-------------------------------------------5.List All Bookings-------------------------------------------------------//


    void listAllBookings(){

        ArrayList<Booking> bookings=bookingDB.getBookings();
        if(bookings.isEmpty()){
            System.out.println("No bookings available");
            return;
        }
        System.out.println("Booking List");
        for(int i=0;i<bookings.size();i++){
            Booking booking=bookings.get(i);
            Customer customer=customerDB.getCustomerByID(booking.getCustomerID());
            Hotel hotel=hotelDB.getHotelByID(booking.getHotelID());
            System.out.println((i+1)+". Booking ID : "+booking.getBookingID());
            System.out.println("Check-in Date : "+booking.getCheckInDateString());
            System.out.println("Check-out Date : "+booking.getCheckOutDateString());
            System.out.println("Customer Name : "+customer.getFullName());
            System.out.println("Contact : "+customer.getPhoneNumber());
            System.out.println("Hotel Name : "+hotel.getHotelName()+" ID :"+hotel.getHotelID());
            System.out.println();
        }
    }
//-------------------------------------------6.Set Price for Rooms of Hotels-------------------------------------------//

    void setPriceforRooms(){
        System.out.println("1.Enter Hotel ID and change price for rooms");
        System.out.println("2.List Registered Hotels which updated there price");
        System.out.println("Enter Input : ");
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            getHotelID();
        }
        else if(choice==2){
            listHotelsWhichChangedPrice();
        }
    }

    void getHotelID(){
        System.out.println("\n"+"Enter Hotel Id : ");
        int hotelID=InputHelper.getInputWithinRange(Integer.MAX_VALUE,"There is no hotel with Negative ID's");
        Hotel hotel=hotelDB.getHotelByID(hotelID);
        setPrice(hotel);
    }

    void setPrice(Hotel hotel){


        if(hotel!=null){
            System.out.println("Hotel ID : "+hotel.getHotelID());
            System.out.println("Hotel Name : "+hotel.getHotelName());
            System.out.println("Hotel Address : No."+hotel.getAddress().getBuildingNo()+","+hotel.getAddress().getStreet()+","+hotel.getAddress().getLocality());
            System.out.println("\t"+hotel.getAddress().getCity()+","+hotel.getAddress().getState());
            System.out.println("Postal Code : "+hotel.getAddress().getPostalCode());
            System.out.println("No of Rooms : "+hotel.getTotalNumberofRooms());
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

    double priceChangeOptions(double basePrice,double maxPrice){
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

    void listHotelsWhichChangedPrice(){
        LinkedHashSet<Integer> hotelList= adminDB.getPriceUpdatedHotelList();
        if(hotelList.isEmpty()){
            System.out.println("No Hotels changed their room prices...");
            return;
        }
        System.out.println("HOTEL ID |       HOTEL DETAILS     |    HOTEL TYPE ");
        for(Integer id:hotelList){
            Hotel hotel=hotelDB.getHotelByID(id);
            System.out.println("  "+hotel.getHotelID()+"  "+hotel.getHotelName()+" , "+hotel.getAddress().getCity()+"  "+hotel.getHotelType());
        }
        System.out.println("\n\n"+"Enter Hotel Id : ");
        int hotelId=InputHelper.getInputWithinRange(Integer.MAX_VALUE,"There is no hotel with Negative ID's");
        if(!hotelList.contains(hotelId)){
            System.out.println("Hotel With ID:"+hotelId+" didn't changed their prices recently");
            return;
        }
        Hotel hotel=hotelDB.getHotelByID(hotelId);
        setPrice(hotel);
    }


}
