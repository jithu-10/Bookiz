package admin;


import java.util.LinkedHashSet;

public class AdminDB {
    private static LinkedHashSet<Integer> priceUpdatedHotelList=new LinkedHashSet<>();

    public static void addPriceUpdatedHotelList(int id){
        priceUpdatedHotelList.add(id);
    }

    public static LinkedHashSet<Integer> getPriceUpdatedHotelList(){
        return priceUpdatedHotelList;
    }

    public static void removeHotelfromPriceUpdatedHotelList(int id){
        if(priceUpdatedHotelList.contains(id)){
            priceUpdatedHotelList.remove(id);
        }
    }

}
