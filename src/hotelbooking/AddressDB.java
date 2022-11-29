package hotelbooking;

import java.util.ArrayList;

public class AddressDB {
    private final ArrayList<String> availableCities=new ArrayList<>();
    private final ArrayList<String> availableLocalities=new ArrayList<>();
    private static AddressDB addressDB=null;
    private AddressDB(){

    }

    public static AddressDB getInstance(){
        if(addressDB==null){
            addressDB=new AddressDB();
        }
        return addressDB;
    }

    void addLocality(String locality){
        locality= Helper.modifyString(locality);
        availableLocalities.add(locality);
    }

    void addCity(String city){
        city=Helper.modifyString(city);
        availableCities.add(city);
    }

    void removeLocality(String locality){
        locality=Helper.modifyString(locality);
        availableLocalities.remove(locality);
    }

    void removeCity(String city){
        city=Helper.modifyString(city);
        availableCities.remove(city);
    }

    public boolean isLocalityAvailable(String locality){
        Helper.validateString(locality,"Locality can't be null or empty");
        locality=Helper.modifyString(locality);
        for(int i=0;i<availableLocalities.size();i++){
            if(availableLocalities.get(i).equals(locality)){
                return true;
            }
        }
        return isCityAvailable(locality);
    }

    private boolean isCityAvailable(String locality){
        Helper.validateString(locality,"Locality can't be null or empty");
        locality=Helper.modifyString(locality);
        for(int i=0;i<availableCities.size();i++){
            if(availableCities.get(i).equals(locality)){
                return true;
            }
        }
        return false;
    }


}
