package driver;

import booking.Booking;
import customer.Customer;
import customer.CustomerDB;
import hotel.*;
import user.User;
import utility.InputHelper;
import utility.Printer;
import utility.Validator;

import java.util.*;

public class CustomerDriver implements Driver {

    static final CustomerDriver customerDriver=new CustomerDriver();


    private CustomerDriver(){

    }

    static CustomerDriver getInstance(){
        return customerDriver;
    }

    @Override
    public void startDriver() {
        System.out.println(" Customer Driver ");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("Enter Input : ");
        int choice = InputHelper.getInputWithinRange(2,null);
        switch (choice){
            case 1:
                System.out.println("Signed In .....");
                Customer customer;
                if((customer=(Customer) signIn())!=null){
                    menu(customer);
                }
                else{
                    System.out.println(Printer.INVALID_CREDENTIALS);
                }
                break;
            case 2:
                register();
                break;

        }

    }

    @Override
    public User signIn() {
        System.out.println(Printer.SIGN_IN);
        System.out.println(Printer.ENTER_PHONE_NUMBER);
        long phoneNumber= InputHelper.getPhoneNumber();
        System.out.println(Printer.ENTER_PASSWORD);
        String passWord= InputHelper.getStringInput();
        User user= CustomerDB.checkAuthentication(phoneNumber,passWord);
        return user;
    }

    @Override
    public void menu(User user) {
        Customer customer=(Customer) user;
        do{
            System.out.println("1.Book Hotel");
            System.out.println("2.List Bookings");
            System.out.println("3.Cancel Booking");
            System.out.println("4.Favorite List");
            System.out.println("5.Log Out");
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(5,null);
            switch (choice){
                case 1:
                    bookHotel(customer);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Signing Out...");
                    return;
                default:
                    //NO NEED
                    System.out.println("Enter Input only from given options");
            }

        }while(true);
    }

    //------------------------------------------------Customer Registration--------------------------------------------//
    public void register(){
        Customer customer=customerDetails();
        CustomerDB.addCustomer(customer);
        System.out.println("Sign Up Completed . You can Sign in Now");
    }

    public Customer customerDetails(){
        System.out.println("Enter Phone Number : ");
        long phoneNumber=InputHelper.getPhoneNumber();
        String password,confirmPassword;
        while(true){
            System.out.println("Enter Password : ");
            password=InputHelper.getStringInput();
            System.out.println("Confirm Password : ");
            confirmPassword=InputHelper.getStringInput();

            if(Validator.confirmPasswordValidatator(password,confirmPassword)){
                break;
            }
            System.out.println("Password Not Matching Try Again ");
        }
        System.out.println("Full Name : ");
        String fullName=InputHelper.getStringInput();
        System.out.println("E-Mail ID : ");
        /*TODO Validate Mail ID*/
        String mailID=InputHelper.getStringInput();
        return new Customer(fullName,phoneNumber,mailID,password);
    }

    //------------------------------------------------1.Book Hotel----------------------------------------------------//

    void bookHotel(Customer customer){
        /*TODO start with locality based Search*/
        System.out.println("Check In Date : ");
        Date checkInDate=compareAndCheckDate(new Date(),"You can't book hotel for previous dates",false);
        /*TODO find check in date is lesser than after 10 Dates*/
        System.out.println("Check Out Date : ");
        Date checkOutDate=compareAndCheckDate(checkInDate,"Check out Date will be greater than Check In Date",true);
        System.out.println("Enter No of Rooms Needed : ");
        int noOfRoomsNeeded=InputHelper.getInputWithinRange(50,"Only 50 rooms can be booked at a time");
        int noOfSingleBedroomsNeeded=0;
        int noOfDoubleBedroomsNeeded=0;
        int noOfSuiteRoomNeeded=0;
        for(int i=0;i<noOfRoomsNeeded;i++){
            System.out.println("Enter Type of Room Needed ");
            System.out.println("1."+ RoomType.SINGLEBEDROOM);
            System.out.println("2."+ RoomType.DOUBLEBEDROOM);
            System.out.println("3."+RoomType.SUITEROOM);
            int choice=InputHelper.getInputWithinRange(3,null);
            switch (choice){
                case 1:
                    noOfSingleBedroomsNeeded++;
                    break;
                case 2:
                    noOfDoubleBedroomsNeeded++;
                    break;
                case 3:
                    noOfSuiteRoomNeeded++;
                    break;
            }

        }
        Booking booking=new Booking(checkInDate,checkOutDate,noOfSingleBedroomsNeeded,noOfDoubleBedroomsNeeded,noOfSuiteRoomNeeded);
        findAvailableHotels(customer,booking);
    }

    Date compareAndCheckDate(Date date,String str,boolean currentDateCheck){
        Date checkInDate=InputHelper.getDate();
        date=InputHelper.setTime(date);
        int value=checkInDate.compareTo(date);
        if(value==-1){
            System.out.println(str);
            System.out.println("Please Enter Correct Date ");
            return compareAndCheckDate(date,str,currentDateCheck);
        }
        if(currentDateCheck&&value==0){
            System.out.println(str);
            System.out.println("Enter Correct Date : ");
            return compareAndCheckDate(date,str,true);
        }
        else{
            return checkInDate;
        }
    }

    void findAvailableHotels(Customer customer,Booking booking){
        ArrayList<Date> datesInRange=InputHelper.getDatesBetweenTwoDates(booking.getCheckInDate(),booking.getCheckOutDate());
        datesInRange.add(booking.getCheckOutDate());
        ArrayList<Hotel> hotels=HotelDB.getRegisteredHotelList();
        ArrayList<Integer> availableHotelsID=new ArrayList<>();
        loop:for(int i=0;i<hotels.size();i++){
            Hotel hotel=hotels.get(i);
            /*TODO add Locality based search*/
            for(int j=0;j<datesInRange.size();j++){
                int noOfRemSingleBedRoomsBookedByDate=hotel.getNumberofSingleBedRooms()-hotel.getNoOfSingleBedRoomsBookedByDate(datesInRange.get(j));
                int noOfRemDoubleBedRoomsBookedByDate=hotel.getNumberofDoubleBedRooms()-hotel.getNoOfDoubleBedRoomsBookedByDate(datesInRange.get(j));
                int noOfRemSuiteRoomsBookedByDate=hotel.getNumberofSuiteRooms()-hotel.getNoOfSuiteRoomsBookedByDate(datesInRange.get(j));
                int totalNoOfRemRoomsBookedByDate=noOfRemSuiteRoomsBookedByDate+noOfRemDoubleBedRoomsBookedByDate+noOfRemSingleBedRoomsBookedByDate;
                if(booking.getTotalNoOfRoomsNeeded()>totalNoOfRemRoomsBookedByDate){
                    continue loop;
                }
                else if(booking.getNoOfSingleBedroomsNeeded()>noOfRemSingleBedRoomsBookedByDate){
                    continue loop;
                }
                else if(booking.getNoOfDoubleBedroomsNeeded()>noOfRemDoubleBedRoomsBookedByDate){
                    continue loop;
                }
                else if(booking.getNoOfSuiteRoomNeeded()>noOfRemSuiteRoomsBookedByDate){
                    continue loop;
                }
            }
            availableHotelsID.add(hotel.getHotelID());

        }

        for(int i=0;i<availableHotelsID.size();i++){
            printHotelDetails(i,HotelDB.getHotelByID(availableHotelsID.get(i)),booking);
        }

    }


    void printHotelDetails(int sno,Hotel hotel,Booking booking){
        System.out.println((sno+1)+" . "+"BOOKIZ "+hotel.getHotelType()+" Hotel ID : "+hotel.getHotelID());
        System.out.println("\t"+hotel.getHotelName());
        System.out.println("\t Ph.No : "+hotel.getPhoneNumber());
        System.out.println("\t Address : "+hotel.getAddress());
        System.out.println("\t Locality : "+hotel.getLocality()+"\n");
        double listPrice=getListPrice(booking,hotel);
        double maxPrice=getMaxPrice(booking,hotel);
        int discount=(int)getDiscountPercent(listPrice,maxPrice);
        System.out.println("Price(1 Day) : ₹"+listPrice+" Actual Price(1 Day) : ₹"+maxPrice+"  "+discount);
    }

    double getListPrice(Booking booking,Hotel hotel){
        double singleBedRoomPrice=booking.getNoOfSingleBedroomsNeeded()*hotel.getSingleBedRoomListPrice();
        double doubleBedRoomPrice=booking.getNoOfDoubleBedroomsNeeded()*hotel.getDoubleBedRoomListPrice();
        double suiteRoomPrice=booking.getNoOfSuiteRoomNeeded()*hotel.getSuiteRoomListPrice();
        return singleBedRoomPrice+doubleBedRoomPrice+suiteRoomPrice;
    }

    double getMaxPrice(Booking booking,Hotel hotel){
        double singleBedRoomPrice=booking.getNoOfSingleBedroomsNeeded()*hotel.getSingleBedRoomMaxPrice();
        double doubleBedRoomPrice=booking.getNoOfDoubleBedroomsNeeded()*hotel.getDoubleBedRoomMaxPrice();
        double suiteRoomPrice=booking.getNoOfSuiteRoomNeeded()*hotel.getSuiteRoomMaxPrice();
        return singleBedRoomPrice+doubleBedRoomPrice+suiteRoomPrice;
    }

    double getDiscountPercent(double listPrice,double maxPrice){
        double discount=maxPrice-listPrice;
        double rateOfDiscount=(discount/maxPrice)*100;
        return rateOfDiscount;
    }





}
