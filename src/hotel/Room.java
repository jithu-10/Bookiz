package hotel;

import utility.InputHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Room {


    private RoomType roomType;
    private ArrayList<Date> booked2=new ArrayList<>();


    public Room(RoomType roomType){
        this.roomType=roomType;

    }


    public RoomType getRoomType() {
        return roomType;
    }

    public void updateBookings2(Date checkInDate,Date checkOutDate,boolean book){
        if(book){
            booked2.addAll(InputHelper.getDatesBetweenTwoDates(checkInDate,checkOutDate));
            booked2.add(checkOutDate);
        }
        else{
            booked2.removeAll(InputHelper.getDatesBetweenTwoDates(checkInDate,checkOutDate));
            booked2.remove(checkOutDate);
        }

    }

    public boolean checkBookedByDate2(Date date){
        if(booked2.contains(date)){
            return true;
        }
        else {
            return false;
        }
    }



}












