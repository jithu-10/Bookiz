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
        hotel.setSingleBedRoomPrice(1,2);
        hotel.setDoubleBedRoomPrice(1,2);
        hotel.setSuiteRoomPrice(1,2);
        HotelDB.addApprovedHotelList(hotel);
        return hotel;
    }

}
