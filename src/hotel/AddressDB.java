package hotel;

import utility.InputHelper;

import java.util.ArrayList;

public class AddressDB {
    private ArrayList<String> availableCities=new ArrayList<>();
    private ArrayList<String> availableLocalities=new ArrayList<>();
    private static AddressDB addressDB=null;
    private AddressDB(){

    }

    public static AddressDB getInstance(){
        if(addressDB==null){
            addressDB=new AddressDB();
        }
        return addressDB;
    }

    public void addLocality(String locality){
        locality= InputHelper.modifyString(locality);
        availableLocalities.add(locality);
    }

    public void addCity(String city){
        city=InputHelper.modifyString(city);
        availableCities.add(city);
    }

    public void removeLocality(String locality){
        locality=InputHelper.modifyString(locality);
        availableLocalities.remove(locality);
    }

    public void removeCity(String city){
        city=InputHelper.modifyString(city);
        availableCities.remove(city);
    }

    public boolean isLocalityAvailable(String locality){
        locality=InputHelper.modifyString(locality);
        for(int i=0;i<availableLocalities.size();i++){
            if(availableLocalities.get(i).equals(locality)){
                return true;
            }
        }
        return isCityAvailable(locality);
    }

    public boolean isCityAvailable(String locality){
        locality=InputHelper.modifyString(locality);
        for(int i=0;i<availableCities.size();i++){
            if(availableCities.get(i).equals(locality)){
                return true;
            }
        }
        return false;
    }


}
