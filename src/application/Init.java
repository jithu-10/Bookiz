package application;


import hotelbooking.Customer;
import hotelbooking.*;
import hotelbooking.User;
import hotelbooking.UserDB;

import java.util.ArrayList;

public class Init {


    static void init(){
        initializeAmenities();
        initializeHotels();
        initializeCustomers();
    }
    private static void initializeAmenities(){
        Amenity TV = new Amenity("TV",20);
        Amenity AC = new Amenity("AC",20);
        Amenity FRIDGE=new Amenity("FRIDGE",10);
        Amenity WASHING_MACHINE=new Amenity("WASHING MACHINE",10);
        Amenity MODERN_WARDROBE=new Amenity("MODERN WARDROBE",5);
        Amenity HIGH_SPEED_WIFI=new Amenity("HIGH SPEED WIFI",20);
        Amenity STUDY_LAMP_TABLE=new Amenity("STUDY LAMP AND TABLE",5);
        Amenity GEYSER=new Amenity("GEYSER",10);
        AmenityDB.getInstance().addAmenity(TV);
        AmenityDB.getInstance().addAmenity(AC);
        AmenityDB.getInstance().addAmenity(FRIDGE);
        AmenityDB.getInstance().addAmenity(WASHING_MACHINE);
        AmenityDB.getInstance().addAmenity(MODERN_WARDROBE);
        AmenityDB.getInstance().addAmenity(HIGH_SPEED_WIFI);
        AmenityDB.getInstance().addAmenity(STUDY_LAMP_TABLE);
        AmenityDB.getInstance().addAmenity(GEYSER);
    }

    private static void initializeCustomers(){
        String userNames[]={"Marty A. Ortiz","Florence M. Gilcrease","Charles J. Schultz","Rod L. Tarrance","Hector L. Joyner","Michael N. Bell"};
        long phoneNumber[]={9092722880L,9677298160L,7358196791L,1234567890L,9876543211L,5555544444L};
        String mailID[]={"13jackf9@masjoco.com","bxv6414@traz.xyz","keff85@ikanchana.com","frostschneider@happiseektest.com","ff6se@mymailcr.com","djevens@twichzhuce.com"};
        for(int i=0;i<6;i++){
            Customer customer=new Customer(userNames[i],phoneNumber[i],mailID[i]);
            UserDB.getInstance().addCustomer(customer,"pass");
        }
    }

    private static void initializeHotels(){
        User users[]=getHotelOwners();
        String hotelName[]={"ARASAN INN","TAMILNADU MANSION","THE PRINCE PARK","ANNAI GUEST HOUSE","SHAPPY IN","KB RESIDENC&"};
        Address address[]=initAddress();
        HotelType hotelType[]={HotelType.ELITE,HotelType.STANDARD,HotelType.PREMIUM,HotelType.ELITE,HotelType.STANDARD,HotelType.PREMIUM};
        HotelApprovalStatus hotelApprovalStatus = HotelApprovalStatus.APPROVED;
        //ArrayList<Integer> amenities=getAmenityIDs( AmenityDB.getInstance().getAmenities());
        ArrayList<Amenity> amenities=AmenityDB.getInstance().getAmenities();
        Hotel hotels[]=new Hotel[6];
        for(int i=0;i<hotels.length;i++){

            Hotel hotel=new Hotel(users[i],hotelName[i],address[i]);
            HotelDB.getInstance().registerHotel(hotel);
            HotelDB.getInstance().approveHotel(hotel);
            setRooms(hotel);
            hotel.setHotelType(hotelType[i]);
            hotel.setHotelApprovalStatus(hotelApprovalStatus);
            for(Amenity amenity:amenities){
                hotel.addAmenity(amenity);
            }

        }
    }

    private static User[] getHotelOwners(){
        String userNames[]={"User 1","User 2","User 3","User 4","User 5","User 6"};
        Long phoneNumber[]={9092722880L,9677298160L,7358196791L,1234567890L,9876543211L,5555544444L};
        String mailID[]={"13jackf9@masjoco.com","bxv6414@traz.xyz","keff85@ikanchana.com","frostschneider@happiseektest.com","ff6se@mymailcr.com","djevens@twichzhuce.com"};
        User[] users=new User[6];
        for(int i=0;i<6;i++){
            users[i]=new User(userNames[i],phoneNumber[i],mailID[i]);
            UserDB.getInstance().addHotelAdmin(users[i],"pass");
        }
        return users;
    }

    private static Address[] initAddress(){
        String buildingNo[]={"1","23","32B","3","5","54A"};
        String street[]={"Periyar","MGR","Shivaji","Kannagi","Karikalan","Mamta"};
        String city[]={"Chennai","Chennai","Mumbai","Chennai","Chennai","Kolkata"};
        String locality[]={"Tambaram","Sholinganallur","Potheri","Guduvancherry","Anna Nagar","Adyar"};
        String state[]={"Tamil Nadu","Tamil Nadu","Maharashtra","Tamil Nadu","Tamil Nadu","West Bengal"};
        int postalCode[]={600059,600102,600048,603102,600321,600043};
        Address addressList[]=new Address[6];
        for(int i=0;i<addressList.length;i++){
            Address address=new Address(buildingNo[i],street[i],locality[i],city[i],state[i],postalCode[i]);
            addressList[i]=address;
        }
        return addressList;
    }


    private static void setRooms(Hotel hotel){
        int[] roomCap={2,4,1,3,8,2};





        for(int i=0;i<6;i++){
            Price roomPrice=new Price(2000,4000);
            Price bedPrice=new Price(400,500);
            Room room=new Room(hotel,roomCap[i],roomPrice,bedPrice);
            hotel.addRoom(room);

        }
    }





}
