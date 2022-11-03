package hotel;

import java.util.ArrayList;

public class AmenityDB {

    private static final AmenityDB amenityDB=new AmenityDB();
    private int idHelper=0;
    private final Amenity TV = new Amenity("TV",20);
    private final Amenity AC = new Amenity("AC",20);
    private final Amenity FRIDGE=new Amenity("FRIDGE",10);
    private final Amenity WASHING_MACHINE=new Amenity("WASHING MACHINE",10);
    private final Amenity MODERN_WARDROBE=new Amenity("MODERN WARDROBE",5);
    private final Amenity HIGH_SPEED_WIFI=new Amenity("HIGH SPEED WIFI",20);
    private final Amenity STUDY_LAMP_TABLE=new Amenity("STUDY LAMP AND TABLE",5);
    private final Amenity GEYSER=new Amenity("GEYSER",10);

    private final ArrayList<Amenity> AMENITIES=new ArrayList<>();

    {
        addAmenity(TV);
        addAmenity(AC);
        addAmenity(FRIDGE);
        addAmenity(WASHING_MACHINE);
        addAmenity(MODERN_WARDROBE);
        addAmenity(HIGH_SPEED_WIFI);
        addAmenity(STUDY_LAMP_TABLE);
        addAmenity(GEYSER);
    }

    private AmenityDB(){

    }

    private int generateId(){
        return ++idHelper;
    }

    public void addAmenity(Amenity amenity){
        amenity.setAmenityID(generateId());
        AMENITIES.add(amenity);
    }

    public void removeAmenity(int index){
        AMENITIES.remove(index);
    }


    public static AmenityDB getInstance(){
        return amenityDB;
    }

    public ArrayList<Amenity> getAmenities(){
        return AMENITIES;
    }

    public int getTotalAmenityPoints(){
        int totalAmenityPoints=0;
        for(Amenity amenity:AMENITIES){
            totalAmenityPoints+=amenity.getPoints();
        }
        return totalAmenityPoints;
    }

    public int getAmenityPointsByID(int id){
        for(Amenity amenity:AMENITIES){
            if(amenity.getAmenityID()==id){
                return amenity.getPoints();
            }
        }
        return 0;
    }
    public Amenity getAmenityByID(int id){
        for(Amenity amenity:AMENITIES){
            if(amenity.getAmenityID()==id){
                return amenity;
            }
        }
        return null;
    }

}
