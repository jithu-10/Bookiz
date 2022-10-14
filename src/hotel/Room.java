package hotel;

import hotel.utils.Price;

import java.util.ArrayList;

public class Room {


    protected RoomType roomType;


    Room(RoomType roomType){
        this.roomType=roomType;

    }


    public RoomType getRoomType() {
        return roomType;
    }

}












//    private static class SimpleBed{
//        String name="SimpleBed";
//
//    }
//
//    private static class DoubleBed{
//        String name="DoubleBed";
//    }
//
//    private static class SuiteRoom{
//        String name="SuiteRoom";
//    }