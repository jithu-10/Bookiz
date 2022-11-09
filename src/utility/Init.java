package utility;


import customer.Customer;
import hotel.*;
import user.User;
import user.UserAuthenticationDB;
import user.UserDB;

import java.util.ArrayList;

public class Init {


    public static void init(){
        initializeHotels();
        initializeCustomers();
    }

    public static void initializeCustomers(){
        String userNames[]={"Marty A. Ortiz","Florence M. Gilcrease","Charles J. Schultz","Rod L. Tarrance","Hector L. Joyner","Michael N. Bell"};
        long phoneNumber[]={9092722880L,9677298160L,7358196791L,1234567890L,9876543211L,5555544444L};
        String mailID[]={"13jackf9@masjoco.com","bxv6414@traz.xyz","keff85@ikanchana.com","frostschneider@happiseektest.com","ff6se@mymailcr.com","djevens@twichzhuce.com"};
        for(int i=0;i<6;i++){
            Customer user=new Customer(userNames[i]);
            user.setPhoneNumber(phoneNumber[i]);
            user.setMailID(mailID[i]);
            UserDB.getInstance().addCustomer(user);
            UserAuthenticationDB.getInstance().addCustomerAuth(phoneNumber[i],mailID[i],"pass");
        }
    }

    public static void initializeHotels(){
        User users[]=getHotelOwners();
        String hotelName[]={"ARASAN INN","TAMILNADU MANSION","THE PRINCE PARK","ANNAI GUEST HOUSE","SHAPPY IN","KB RESIDENC&"};
        Address address[]=initAddress();
        HotelType hotelType[]={HotelType.TOWNHOUSE,HotelType.SPOTZ,HotelType.COLLECTIONZ,HotelType.TOWNHOUSE,HotelType.SPOTZ,HotelType.COLLECTIONZ};
        HotelApprovalStatus hotelApprovalStatus = HotelApprovalStatus.APPROVED;
        ArrayList<Integer> amenities=getAmenityIDs( AmenityDB.getInstance().getAmenities());
        ArrayList<Room> rooms[]=setRooms();

        Hotel hotels[]=new Hotel[6];
        for(int i=0;i<hotels.length;i++){
            Hotel hotel=new Hotel(users[i].getUserID(),hotelName[i],address[i],hotelType[i], hotelApprovalStatus,amenities,rooms[i]);
            HotelDB.getInstance().registerHotel(hotel);
            HotelDB.getInstance().approveHotel(hotel);
        }
    }

    public static User[] getHotelOwners(){
        String userNames[]={"User 1","User 2","User 3","User 4","User 5","User 6"};
        Long phoneNumber[]={9092722880L,9677298160L,7358196791L,1234567890L,9876543211L,5555544444L};
        String mailID[]={"13jackf9@masjoco.com","bxv6414@traz.xyz","keff85@ikanchana.com","frostschneider@happiseektest.com","ff6se@mymailcr.com","djevens@twichzhuce.com"};
        User[] users=new User[6];
        for(int i=0;i<6;i++){
            User user=new User(userNames[i]);

            user.setPhoneNumber(phoneNumber[i]);
            user.setMailID(mailID[i]);
            UserDB.getInstance().addHotelAdmin(user);
            users[i]=user;
            UserAuthenticationDB.getInstance().addHotelAdminAuth(phoneNumber[i],mailID[i],"pass");
        }
        return users;
    }

    public static ArrayList<Integer> getAmenityIDs(ArrayList<Amenity> amenities){
        ArrayList<Integer> amenitiesID=new ArrayList<>();
        for(int i=0;i<amenities.size();i++){
            amenitiesID.add(amenities.get(i).getAmenityID());
        }
        return amenitiesID;
    }

    public static Address[] initAddress(){
        int buildingNo[]={1,23,32,3,5,54};
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

    public static ArrayList<Room>[] setRooms(){
        ArrayList<Room> rooms[]=new ArrayList[6];
        for(int i=0;i<rooms.length;i++){
            rooms[i]=initRooms();
        }
        return rooms;
    }
    public static ArrayList<Room> initRooms(){
        ArrayList<Room> rooms=new ArrayList<>();
        int[] roomCap={2,4,1,3,8,2};
        Price price=new Price(2000,4000);
        Price price1=new Price(200,400);
        for(int i=0;i<6;i++){
            Room room=new Room(i,roomCap[i],price,price1);
            rooms.add(room);
        }
        return rooms;
    }




}
