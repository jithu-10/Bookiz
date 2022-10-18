package testing;

import customer.Customer;
import customer.CustomerDB;
import hotel.Amenity;
import hotel.AmenityDB;
import hotel.Hotel;
import hotel.HotelDB;

import java.util.ArrayList;

public class Init {

    public static Hotel publichotels[]=new Hotel[6];
    public static void init(){
        String str[]={"ARASAN INN","TAMILNADU MANSION","THE PRINCE PARK","ANNAI GUEST HOUSE","SHAPPY IN","KB RESIDENC&"};
        long phn[]={9092722880L,9677298160L,7358196791L,1234567890L,9876543211L,5555544444L};
        String add[]={"Sholinganallur Junction","Police Quarters","Behind Koyambedu Bus Stand","Karappakkam","Easwaran Salai","Near Presidency College"};
        Hotel hotels[]=new Hotel[str.length];
        for(int i=0;i<str.length;i++){
            hotels[i]=createHotel(str[i],phn[i],"pass",add[i],"Chennai");
        }
        Hotel hotel=new Hotel("Special One",9747575427L,"pass","Special One","Vodafone Kattuppakam","Mumbai");
        hotel.addSingleBedRooms(5,500,800);
        hotel.addDoubleBedRooms(5,2000,2400);
        hotel.addSuiteRooms(5,4000,5000);
        HotelDB.addApprovedHotelList(hotel);
        ArrayList<Amenity> amenities=AmenityDB.getAmenities();
        for(int i=0;i<amenities.size();i++){
            hotel.addAmenity(amenities.get(i));
        }
        Customer customer=new Customer("Jadon Sancho",1234567890,"jithin@gmail.com","pass");
        CustomerDB.addCustomer(customer);
        publichotels=hotels;
    }

    public static Hotel createHotel(String name,long phoneNumber,String password,String address,String locality){
        Hotel hotel=new Hotel(name,phoneNumber,password,name,address,locality);
        hotel.addSingleBedRooms(2,300,700);
        hotel.addDoubleBedRooms(2,1000,2000);
        hotel.addSuiteRooms(2,2500,3500);
        ArrayList<Amenity> amenities=AmenityDB.getAmenities();
        for(int i=0;i<amenities.size();i++){
            hotel.addAmenity(amenities.get(i));
        }
        HotelDB.addApprovedHotelList(hotel);
        return hotel;
    }

    public static void endingInit(){
        for(int i=0;i<publichotels.length;i++){
            Hotel hotel=publichotels[i];
            System.out.println(hotel.getHotelID());
            System.out.println(hotel.getTotalNumberofRooms()+" "+hotel.getNumberofSingleBedRooms()+" "+hotel.getNumberofDoubleBedRooms()+" "+hotel.getNumberofSuiteRooms());
            System.out.println(hotel.getSingleBedroomsBooked());
            System.out.println(hotel.getDoubleBedroomsBooked());
            System.out.println(hotel.getSuiteRoomsBooked());
        }
    }


}
