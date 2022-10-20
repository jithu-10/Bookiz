package admin;


import java.util.LinkedHashSet;

public class AdminDB {
    private LinkedHashSet<Integer> priceUpdatedHotelList=new LinkedHashSet<>();
    private static AdminDB adminDB=new AdminDB();
    private AdminDB(){

    }

    public static AdminDB getInstance(){
        return adminDB;
    }

    public void addPriceUpdatedHotelList(int id){
        priceUpdatedHotelList.add(id);
    }

    public LinkedHashSet<Integer> getPriceUpdatedHotelList(){
        return priceUpdatedHotelList;
    }

    public void removeHotelfromPriceUpdatedHotelList(int id){
        if(priceUpdatedHotelList.contains(id)){
            priceUpdatedHotelList.remove(id);
        }
    }

}
