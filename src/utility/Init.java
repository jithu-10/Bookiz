package utility;


import hotel.*;
import user.User;
import user.UserAuthenticationDB;
import user.UserDB;
import user.UserType;

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
            User user=new User();
            user.setUserType(UserType.CUSTOMER);
            user.setUserName(userNames[i]);
            user.setPhoneNumber(phoneNumber[i]);
            user.setMailID(mailID[i]);
            UserDB.getInstance().addUser(user);
            UserAuthenticationDB.getInstance().addCustomerAuth(phoneNumber[i],"pass");
        }
    }

    public static void initializeHotels(){
        User users[]=getHotelOwners();
        String hotelName[]={"ARASAN INN","TAMILNADU MANSION","THE PRINCE PARK","ANNAI GUEST HOUSE","SHAPPY IN","KB RESIDENC&"};
        Address address[]=initAddress();
        HotelType hotelType[]={HotelType.TOWNHOUSE,HotelType.SPOTZ,HotelType.COLLECTIONZ,HotelType.TOWNHOUSE,HotelType.SPOTZ,HotelType.COLLECTIONZ};
        HotelStatus hotelStatus=HotelStatus.APPROVED;
        ArrayList<Integer> amenities=getAmenityIDs( AmenityDB.getInstance().getAmenities());
        ArrayList<Room> rooms[]=setRooms();
        Price singleBedRoomPrice=new Price(1000,2000);
        Price doubleBedRoomPrice=new Price(3000,4000);
        Price suiteRoomPrice=new Price(6000,7000);
        Hotel hotels[]=new Hotel[6];
        for(int i=0;i<hotels.length;i++){
            Hotel hotel=new Hotel(users[i].getUserID(),hotelName[i],address[i],hotelType[i],hotelStatus,amenities,rooms[i],singleBedRoomPrice,doubleBedRoomPrice,suiteRoomPrice);
            HotelDB.getInstance().registerHotel(hotel);
            HotelDB.getInstance().approveHotel(hotel);
        }
    }

    public static User[] getHotelOwners(){
        String userNames[]={"User 1","User 2","User 3","User 4","User 5","User 6"};
        long phoneNumber[]={9092722880L,9677298160L,7358196791L,1234567890L,9876543211L,5555544444L};
        User[] users=new User[6];
        for(int i=0;i<6;i++){
            User user=new User();
            user.setUserType(UserType.HOTEL_OWNER);
            user.setUserName(userNames[i]);
            user.setPhoneNumber(phoneNumber[i]);
            UserDB.getInstance().addUser(user);
            users[i]=user;
            UserAuthenticationDB.getInstance().addHotelAdminAuth(phoneNumber[i],"pass");
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
        for(int i=1;i<=2;i++){
            Room room=new Room(RoomType.SINGLE_BED_ROOM);
            rooms.add(room);
        }
        for(int i=1;i<=2;i++){
            Room room=new Room(RoomType.DOUBLE_BED_ROOM);
            rooms.add(room);
        }
        for(int i=1;i<=2;i++){
            Room room=new Room(RoomType.SUITE_ROOM);
            rooms.add(room);
        }
        return rooms;
    }




}
