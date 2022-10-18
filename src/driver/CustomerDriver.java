package driver;

import booking.Booking;
import booking.BookingDB;
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
                Customer customer;
                if((customer=(Customer) signIn())!=null){
                    System.out.println("Signed In .....\n");
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
                    listFavoriteHotels(customer);
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
        System.out.println("Enter Locality : ");
        String locality=InputHelper.getStringInput();
        if(!HotelDB.isLocalityAvailable(locality)){
            System.out.println("No hotels available in your locality");
            return;
        }
        System.out.println("Check In Date : ");
        Date checkInDate=compareAndCheckDate(InputHelper.setTime(new Date()),"You can't book hotel for previous dates",false,true);
        System.out.println("Check Out Date : ");
        Date checkOutDate=compareAndCheckDate(checkInDate,"Check out Date will be greater than Check In Date",true,false);
        System.out.println("Enter No of Rooms Needed : ");
        int noOfRoomsNeeded=InputHelper.getInputWithinRange(50,"Only 50 rooms can be booked at a time");
        int noOfSingleBedroomsNeeded=0;
        int noOfDoubleBedroomsNeeded=0;
        int noOfSuiteRoomNeeded=0;
        for(int i=0;i<noOfRoomsNeeded;i++){
            System.out.println("Room no."+(i+1));
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
        findAvailableHotels(customer,booking,locality);
    }

    Date compareAndCheckDate(Date date,String str,boolean currentDateCheck,boolean checkIn){
        Date newDate=InputHelper.getDate();
        int value=newDate.compareTo(date);
        if(value==-1){
            System.out.println(str);
            System.out.println("Please Enter Correct Date ");
            return compareAndCheckDate(date,str,currentDateCheck,checkIn);
        }
        else if(currentDateCheck&&value==0){
            System.out.println(str);
            System.out.println("Enter Correct Date : ");
            return compareAndCheckDate(date,str,true,checkIn);
        }

        else{
            int noOfInBetweenDays=InputHelper.getDatesBetweenTwoDates(date,newDate).size();
            if(noOfInBetweenDays>60&&!checkIn){
                System.out.println("You can book hotel room only up to 60 days");
                return compareAndCheckDate(date,str,currentDateCheck,false);
            }
            if(noOfInBetweenDays>180&&checkIn){
                System.out.println("You can book only for next 150 days");
                return compareAndCheckDate(date,str,currentDateCheck,true);
            }
            return newDate;
        }
    }

    void findAvailableHotels(Customer customer,Booking booking,String locality){
        do{
            ArrayList<Date> datesInRange=InputHelper.getDatesBetweenTwoDates(booking.getCheckInDate(),booking.getCheckOutDate());
            booking.setNoOfDays(datesInRange.size());
            datesInRange.add(booking.getCheckOutDate());
            ArrayList<Hotel> hotels=HotelDB.getRegisteredHotelList();
            ArrayList<Integer> availableHotelsID=new ArrayList<>();
            loop:for(int i=0;i<hotels.size();i++){
                Hotel hotel=hotels.get(i);
                if(!InputHelper.modifyString(hotel.getLocality()).equals(InputHelper.modifyString(locality))){
                    continue ;
                }
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
            if(availableHotelsID.isEmpty()){
                System.out.println("SOLD OUT. No Hotels available now.");
                return ;
            }

            for(int i=0;i<availableHotelsID.size();i++){
                printHotelDetailsWithBooking(i,HotelDB.getHotelByID(availableHotelsID.get(i)),booking);
            }

            System.out.println("1.Select Hotel");
            System.out.println("2.Go Back");
            int selectChoice=InputHelper.getInputWithinRange(2,null);
            if(selectChoice==1){
                System.out.println("Enter S.NO to Select Hotel");
                int choice=InputHelper.getInputWithinRange(availableHotelsID.size(),null);
                System.out.println("\n\n");
                boolean booked=expandedHotelDetails(booking,HotelDB.getHotelByID(availableHotelsID.get(choice-1)),customer,booking.getNoOfDays());
                if(booked){
                    return;
                }
            }
            else{
                break;
            }

        }while(true);

    }


    void printHotelDetails(int sno,Hotel hotel){
        System.out.println((sno+1)+" . "+"BOOKIZ "+hotel.getHotelType()+" Hotel ID : "+hotel.getHotelID());
        System.out.println("\t"+hotel.getHotelName());
        System.out.println("\t Ph.No : "+hotel.getPhoneNumber());
        System.out.println("\t Address : "+hotel.getAddress());
        System.out.println("\t Locality : "+hotel.getLocality()+"\n");
    }
    void printHotelDetailsWithBooking(int sno,Hotel hotel,Booking booking){
        printHotelDetails(sno,hotel);
        double listPrice=getListPrice(booking,hotel);
        double maxPrice=getMaxPrice(booking,hotel);
        int discount=(int)getDiscountPercent(listPrice,maxPrice);
        System.out.println("Price(per night) : ₹"+listPrice+" Actual Price(per night) : ₹"+maxPrice+"  "+discount+"%off\n\n");
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


    boolean expandedHotelDetails(Booking booking,Hotel hotel,Customer customer,int totalDays){
        System.out.println(hotel.getHotelType()+" "+hotel.getHotelID()+" "+hotel.getHotelName());
        System.out.println(hotel.getAddress()+","+hotel.getLocality());
        System.out.println();
        System.out.println("Your Booking Details");
        System.out.println("Dates : "+InputHelper.getSimpleDateWithoutYear(booking.getCheckInDate())+" - "+InputHelper.getSimpleDateWithoutYear(booking.getCheckOutDate()));
        System.out.println("Rooms : "+booking.getTotalNoOfRoomsNeeded());
        System.out.println("Booking for : "+customer.getFullName());
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
        double totalPriceOfSingleBedRooms=hotel.getSingleBedRoomListPrice()*booking.getNoOfSingleBedroomsNeeded()*totalDays;
        if(booking.getNoOfSingleBedroomsNeeded()>0){
            System.out.println(RoomType.SINGLEBEDROOM+" : ₹"+hotel.getSingleBedRoomListPrice()+" * "+booking.getNoOfSingleBedroomsNeeded()+" : ₹"+hotel.getSingleBedRoomListPrice()*booking.getNoOfSingleBedroomsNeeded()+" * "+totalDays+(totalDays>1?" days":" day")+" : ₹"+totalPriceOfSingleBedRooms);
        }

        double totalPriceOfDoubleBedRooms=hotel.getDoubleBedRoomListPrice()*booking.getNoOfDoubleBedroomsNeeded()*totalDays;
        if(booking.getNoOfDoubleBedroomsNeeded()>0){
            System.out.println(RoomType.DOUBLEBEDROOM+" : ₹"+hotel.getDoubleBedRoomListPrice()+" * "+booking.getNoOfDoubleBedroomsNeeded()+" : ₹"+hotel.getDoubleBedRoomListPrice()*booking.getNoOfDoubleBedroomsNeeded()+" * "+totalDays+(totalDays>1?" days":" day")+" : ₹"+totalPriceOfDoubleBedRooms);
        }

        double totalPriceOfSuiteRooms=hotel.getSuiteRoomListPrice()*booking.getNoOfSuiteRoomNeeded()*totalDays;
        if(booking.getNoOfSuiteRoomNeeded()>0){
            System.out.println(RoomType.SUITEROOM+" : ₹"+hotel.getSuiteRoomListPrice()+" * "+booking.getNoOfSuiteRoomNeeded()+" : ₹"+hotel.getSuiteRoomListPrice()*booking.getNoOfSuiteRoomNeeded()+" * "+totalDays+(totalDays>1?" days":" day")+" : ₹"+totalPriceOfSuiteRooms);
        }

        double totalPrice=totalPriceOfSingleBedRooms+totalPriceOfDoubleBedRooms+totalPriceOfSuiteRooms;
        System.out.println("\t\t"+"Total : ₹"+totalPrice);
        System.out.println("\n\n1.Book  2.Add To Favorite List 3.Back");
        int choice=InputHelper.getInputWithinRange(3,null);
        loop:do{
            switch (choice){
                case 1:
                    booking.setTotalPrice(totalPrice);
                    boolean booked=bookHotel(booking,customer,hotel);
                    if(booked){
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

    boolean bookHotel(Booking booking,Customer customer,Hotel hotel){
        System.out.println("1.Pay Now : ₹"+booking.getTotalPrice());
        System.out.println("2.Pay Later");
        System.out.println("3.Go Back");
        int choice=InputHelper.getInputWithinRange(2,null);
        switch (choice){
            case 1:
                System.out.println("Paid Using UPI");
                break;
            case 2:
                System.out.println("Pay at Hotel ₹"+booking.getTotalPrice());
                break;
            case 3:
                return false;
        }
        booking.setCustomerID(customer.getCustomerID());
        hotel.updateHashMap(booking.getNoOfSingleBedroomsNeeded(),booking.getNoOfDoubleBedroomsNeeded(),booking.getNoOfSuiteRoomNeeded(),booking.getCheckInDate(),booking.getCheckOutDate());
        hotel.addBookingIDs(booking.getBookingID());
        System.out.println("\nYour booking is confirmed\n");
        System.out.println("BOOKIZ "+hotel.getHotelType()+" "+hotel.getHotelID());
        System.out.println(hotel.getHotelName());
        System.out.println(hotel.getAddress()+","+hotel.getLocality());
        System.out.println("Contact : "+hotel.getPhoneNumber());
        System.out.println();
        System.out.println("Check-in                             Check-out");
        System.out.println(InputHelper.getSimpleDateWithoutYear(booking.getCheckInDate())+"        "+booking.getNoOfDays()+"N             "+InputHelper.getSimpleDateWithoutYear(booking.getCheckOutDate()));
        System.out.println("12:00PM onwards                   Before 11:00AM\n");
        BookingDB.addBooking(booking);
        System.out.println("BOOKING ID");
        System.out.println(" --> "+booking.getBookingID()+"\n");
        System.out.println("RESERVED FOR");
        System.out.println(" --> "+customer.getFullName()+"\n");
        System.out.println("ROOMS & TYPE");
        if(booking.getNoOfSingleBedroomsNeeded()>0){
            System.out.println(" --> "+booking.getNoOfSingleBedroomsNeeded()+" "+RoomType.SINGLEBEDROOM);
        }
        if(booking.getNoOfDoubleBedroomsNeeded()>0){
            System.out.println(" --> "+booking.getNoOfDoubleBedroomsNeeded()+" "+RoomType.DOUBLEBEDROOM);
        }
        if(booking.getNoOfSuiteRoomNeeded()>0){
            System.out.println(" --> "+booking.getNoOfSuiteRoomNeeded()+" "+RoomType.SUITEROOM);
        }
        System.out.println("\nContact Information");
        System.out.println(" --> "+"+91-"+customer.getPhoneNumber());
        InputHelper.pressEnterToContinue();
        return true;
    }

    //------------------------------------------------2.List Bookings--------------------------------------------------//
    //------------------------------------------------3.Cancel Bookings------------------------------------------------//






    //------------------------------------------------4.Favorite List----------------------------------------------------//
    void addToFavoriteList(Customer customer,Hotel hotel){
        customer.addFavoriteHotels(hotel.getHotelID());
    }

    void listFavoriteHotels(Customer customer){
        /*TODO check whether the fav hotels is empty*/
        if(customer.getFavoriteHotels().isEmpty()){
            System.out.println("No Favorite Hotels ");
            return;
        }
        for(int i=0;i<customer.getFavoriteHotels().size();i++){
            Hotel hotel=HotelDB.getHotelByID(customer.getFavoriteHotels().get(i));
            printHotelDetails(i,hotel);
        }
    }

}
