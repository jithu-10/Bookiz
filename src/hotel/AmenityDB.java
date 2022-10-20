package hotel;

import java.util.ArrayList;

public class AmenityDB {

    private static final AmenityDB amenityDB=new AmenityDB();
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
        AMENITIES.add(TV);
        AMENITIES.add(AC);
        AMENITIES.add(FRIDGE);
        AMENITIES.add(WASHING_MACHINE);
        AMENITIES.add(MODERN_WARDROBE);
        AMENITIES.add(HIGH_SPEED_WIFI);
        AMENITIES.add(STUDY_LAMP_TABLE);
        AMENITIES.add(GEYSER);
    }

    private AmenityDB(){

    }

    public static AmenityDB getInstance(){
        return amenityDB;
    }

    public Amenity getTV() {
        return TV;
    }

    public Amenity getAC() {
        return AC;
    }

    public Amenity getFRIDGE() {
        return FRIDGE;
    }

    public Amenity getGEYSER() {
        return GEYSER;
    }

    public Amenity getHighSpeedWifi() {
        return HIGH_SPEED_WIFI;
    }

    public Amenity getModernWardrobe() {
        return MODERN_WARDROBE;
    }

    public Amenity getStudyLampTable() {
        return STUDY_LAMP_TABLE;
    }

    public Amenity getWashingMachine() {
        return WASHING_MACHINE;
    }

    public ArrayList<Amenity> getAmenities(){
        return AMENITIES;
    }

}
