package hotel;

import utility.InputHelper;

import java.util.ArrayList;
import java.util.Date;

public class Room {


    private int id;
    private int roomCapacity;
    private Price roomPrice;
    private Price bedPrice;


    private ArrayList<Date> booked =new ArrayList<>();


    public Room(int id,int roomCapacity,Price roomPrice,Price bedPrice){
        this.id=id;
        this.roomCapacity=roomCapacity;
        this.roomPrice=roomPrice;
        this.bedPrice=bedPrice;
    }

    public int getId() {
        return id;
    }

    public double getRoomBasePrice(){
        return roomPrice.getBasePrice();
    }

    public double getRoomMaxPrice(){
        return roomPrice.getMaxPrice();
    }
    public double getRoomListPrice(){
        return roomPrice.getListPrice();
    }

    public void setRoomListPrice(double listPrice){
        roomPrice.setListPrice(listPrice);
    }

    public double getBedPrice(){
        return bedPrice.getBasePrice();
    }

    public int getRoomCapacity(){
        return roomCapacity;
    }



    public void updateBookings(Date checkInDate, Date checkOutDate, boolean book){
        if(book){
            booked.addAll(InputHelper.getDatesBetweenTwoDates(checkInDate,checkOutDate));
            booked.add(checkOutDate);
        }
        else{
            booked.removeAll(InputHelper.getDatesBetweenTwoDates(checkInDate,checkOutDate));
            booked.remove(checkOutDate);
        }

    }

    public boolean checkBookedByDate(Date date){
        if(booked.contains(date)){
            return true;
        }
        else {
            return false;
        }
    }

    public void changeRoomPrice(Price roomPrice){
        this.roomPrice=roomPrice;
    }

    public void changeBedPrice(Price bedPrice){
        this.bedPrice=bedPrice;
    }



}












