package testing;

import hotel.Hotel;
import hotel.HotelDB;

public class Init {
    public static void init(){
        String str[]={"A","B","C","D","E","F"};
        long phn[]={9092722880L,9677298160L,7358196791L,1234567890L,9876543211L,5555544444L};
        Hotel hotels[]=new Hotel[str.length];
        for(int i=0;i<str.length;i++){
            hotels[i]=createHotel(str[i],phn[i],"pass");
        }
    }

    public static Hotel createHotel(String name,long phoneNumber,String password){
        Hotel hotel=new Hotel(name,phoneNumber,password,name,null,null);
        hotel.addSingleBedRooms(10,300,700);
        hotel.addDoubleBedRooms(10,1000,2000);
        hotel.addSuiteRooms(10,2500,3500);
        HotelDB.addApprovedHotelList(hotel);
        return hotel;
    }

}
