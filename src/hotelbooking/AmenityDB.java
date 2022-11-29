package hotelbooking;

import java.util.ArrayList;

public class AmenityDB {

    private static final AmenityDB AMENITY_DB =new AmenityDB();
    private int idHelper=0;


    private final ArrayList<Amenity> AMENITIES=new ArrayList<>();



    private AmenityDB(){
    }



    int generateId(){
        return ++idHelper;
    }


    public void addAmenity(Amenity amenity){
        Helper.validateNull(amenity,"Amenity can't be null");
        AMENITIES.add(amenity);
    }

    public void removeAmenity(Amenity amenity){
        Helper.validateNull(amenity,"Amenity can't be null");
        AMENITIES.remove(amenity);
    }


    public static AmenityDB getInstance(){
        return AMENITY_DB;
    }

    public ArrayList<Amenity> getAmenities(){
        return AMENITIES;
    }

    int getTotalAmenityPoints(){
        int totalAmenityPoints=0;
        for(Amenity amenity:AMENITIES){
            totalAmenityPoints+=amenity.getPoints();
        }
        return totalAmenityPoints;
    }

    int getAmenityPointsByID(int id){
        for(Amenity amenity:AMENITIES){
            if(amenity.getAmenityID()==id){
                return amenity.getPoints();
            }
        }
        return 0;
    }
    Amenity getAmenityByID(int id){
        for(Amenity amenity:AMENITIES){
            if(amenity.getAmenityID()==id){
                return amenity;
            }
        }
        return null;
    }

}
