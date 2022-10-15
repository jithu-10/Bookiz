package driver;

import admin.Admin;
import hotel.Hotel;
import hotel.HotelDB;
import hotel.RoomType;
import user.User;
import utility.InputHelper;
import utility.Printer;

import java.util.ArrayList;

public class AdminDriver implements Driver{

    static final AdminDriver adminDriver=new AdminDriver();


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
            System.out.println("5.Set Discount Percentage for hotels");
            System.out.println("6.Sign Out");
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
                    break;
                    /*TODO Set Terms and Conditions*/
                case 5:
                    setPriceforRooms();
                    break;
                case 6:
                    System.out.println("Signing Out....");
                    return;
                default:
                    System.out.println("Enter Input only from given option");
            }

        }while(true);

    }
//----------------------------------------------1.Approve Hotels------------------------------------------------------//
    void approveHotels(){
        listHotelsRequestedforApproval();
    }
    
    void listHotelsRequestedforApproval(){
        ArrayList<Hotel> hotelsRequested= HotelDB.getHotelsRegisteredForApproval();
        if(hotelsRequested.size()==0){
            System.out.println("No Such Requests available now..");
            return;
        }
        for(int i=0;i<hotelsRequested.size();i++){
            Hotel hotel=hotelsRequested.get(i);
            System.out.println((i+1)+" . "+hotel.getHotelName());
            System.out.println("\t"+hotel.getAddress());
            System.out.println("\t"+hotel.getLocality());
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
            HotelDB.addApprovedHotelList(hotel);
            setPriceforHotelRooms(hotel);
            hotel.setHotelType();
            hotel.setHotelId(HotelDB.generateID());
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
        System.out.println("Set Current Price : ");
        double currentPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setSingleBedRoomCurrentPrice(currentPrice);

        basePrice=hotel.getDoubleBedRoomBasePrice();
        maxPrice=hotel.getDoubleBedRoomMaxPrice();
        System.out.println(RoomType.DOUBLEBEDROOM+"-> Base Price : "+basePrice+" Max Price : "+maxPrice);
        System.out.println("Set Current Price : ");
        currentPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setDoubleBedRoomCurrentPrice(currentPrice);

        basePrice=hotel.getSuiteRoomBasePrice();
        maxPrice=hotel.getSuiteRoomMaxPrice();
        System.out.println(RoomType.SUITEROOM+"-> Base Price : "+basePrice+" Max Price : "+maxPrice);
        System.out.println("Set Current Price : ");
        currentPrice=createCurrentPrice(basePrice,maxPrice);
        hotel.setSuiteRoomCurrentPrice(currentPrice);
    }

    double createCurrentPrice(double basePrice,double maxPrice){
        double currentPrice;
        do{
            currentPrice=InputHelper.getDoubleInput();
            if(currentPrice<basePrice||currentPrice>maxPrice){
                System.out.println("Current Price Should be Greater than Base Price and Lesser than Max Price");
                System.out.println("Set Current Price : ");
            }
            else{
                break;
            }

        }while(true);
        return currentPrice;
    }

//---------------------------------------------2.List Registered Hotels------------------------------------------------//
    void listRegisteredHotels(){
        if(HotelDB.getRegisteredHotelList().size()==0){
            System.out.println("No Hotels Available Now");
            return;
        }
        System.out.println("ID\t  Hotel Name  \t Locality \t TotalRooms \t TypeofRoom\n");
        for(Hotel hotel:HotelDB.getRegisteredHotelList()){
            System.out.println(hotel.getHotelID()+"\t"+hotel.getHotelName()+"\t"+hotel.getLocality()+"\t"+hotel.getTotalNumberofRooms()+"\t"+hotel.getHotelType());
        }
    }
//--------------------------------------------3.Remove Registered Hotels-----------------------------------------------//

    void removeRegisteredHotels(){
        System.out.println("Enter Hotel ID : ");
        int hotelId=InputHelper.getIntegerInput();
        ArrayList<Hotel> registeredHotels=HotelDB.getRegisteredHotelList();
        for(int i=0;i<registeredHotels.size();i++){
            if(hotelId==registeredHotels.get(i).getHotelID()){
                registeredHotels.remove(i);
                System.out.println("Hotel Removed Successfully");
                return;
            }
        }
        System.out.println("No Hotel Found with Id");
    }

//-------------------------------------------4.Set Terms and Conditions------------------------------------------------//
    /*TODO Space for creating file base setting terms and conditions*/
//-------------------------------------------5.Set Price for Rooms of Hotels-------------------------------------------//

    void setPriceforRooms(){
        System.out.println("1.Enter Hotel ID and change price for rooms");
        System.out.println("2.List Registered Hotels which updated there price");
        System.out.println("Enter Input : ");
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            setPriceBasedOnID();
        }
        else if(choice==2){
            /*TODO 2.option above*/
        }
    }

    void setPriceBasedOnID(){
        System.out.println("Enter Hotel Id : ");
        int hotelID=InputHelper.getIntegerInput();
        Hotel hotel=null;
        for(Hotel reghotel:HotelDB.getRegisteredHotelList()){
            if(reghotel.getHotelID()==hotelID){
                hotel=reghotel;
                break;
            }
        }
        if(hotel!=null){
            System.out.println("Hotel ID : "+hotel.getHotelID());
            System.out.println("Hotel Name : "+hotel.getHotelName());
            System.out.println("Hotel Address : "+hotel.getAddress());
            System.out.println("Hotel Locality : "+hotel.getLocality());
            System.out.println("No of Rooms : "+hotel.getTotalNumberofRooms());
            System.out.println("Set Price for Rooms");
            System.out.println("SingleBed Rooms -> Base Price : "+hotel.getSingleBedRoomBasePrice()+" Max Price : "+hotel.getSingleBedRoomMaxPrice());
            double currentPrice;
            if((currentPrice=priceChangeOptions(hotel.getSingleBedRoomBasePrice(),hotel.getSingleBedRoomMaxPrice()))!=-1){
                hotel.setSingleBedRoomCurrentPrice(currentPrice);
            }
            System.out.println("DoubleBed Rooms -> Base Price : "+hotel.getDoubleBedRoomBasePrice()+" Max Price : "+hotel.getDoubleBedRoomMaxPrice());
            if((currentPrice=priceChangeOptions(hotel.getDoubleBedRoomBasePrice(),hotel.getDoubleBedRoomMaxPrice()))!=-1){
                hotel.setDoubleBedRoomCurrentPrice(currentPrice);
            }
            System.out.println("Suite Rooms -> Base Price : "+hotel.getSuiteRoomBasePrice()+" Max Price : "+hotel.getSuiteRoomMaxPrice());
            if((currentPrice=priceChangeOptions(hotel.getSuiteRoomBasePrice(),hotel.getSuiteRoomMaxPrice()))!=-1){
                hotel.setSuiteRoomCurrentPrice(currentPrice);
            }
            return;
        }
        System.out.println("No Hotel With Respected ID Found");

    }

    double priceChangeOptions(double basePrice,double maxPrice){
        System.out.println("1.Change Current Price");
        System.out.println("2.Skip");
        System.out.println("Enter Input :");
        int choice = InputHelper.getInputWithinRange(2,null);
        if(choice==2){
            return -1;
        }
        else{
            System.out.println("Set Current Price : ");
            return createCurrentPrice(basePrice,maxPrice);
        }

    }


}
