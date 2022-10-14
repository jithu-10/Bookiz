package hotel;

import java.util.ArrayList;

public class AmenityDB {
    private static final Amenity TV = new Amenity("TV",20);
    private static final Amenity AC = new Amenity("AC",20);
    private static final Amenity FRIDGE=new Amenity("FRIDGE",10);
    private static final Amenity WASHING_MACHINE=new Amenity("WASHING MACHINE",10);
    private static final Amenity MODERN_WARDROBE=new Amenity("MODERN WARDROBE",5);
    private static final Amenity HIGH_SPEED_WIFI=new Amenity("HIGH SPEED WIFI",20);
    private static final Amenity STUDY_LAMP_TABLE=new Amenity("STUDY LAMP AND TABLE",5);
    private static final Amenity GEYSER=new Amenity("GEYSER",10);

    private static final ArrayList<Amenity> AMENITIES=new ArrayList<>();

    static{
        AMENITIES.add(TV);
        AMENITIES.add(AC);
        AMENITIES.add(FRIDGE);
        AMENITIES.add(WASHING_MACHINE);
        AMENITIES.add(MODERN_WARDROBE);
        AMENITIES.add(HIGH_SPEED_WIFI);
        AMENITIES.add(STUDY_LAMP_TABLE);
        AMENITIES.add(GEYSER);
    }

    public static Amenity getTV() {
        return TV;
    }

    public static Amenity getAC() {
        return AC;
    }

    public static Amenity getFRIDGE() {
        return FRIDGE;
    }

    public static Amenity getGEYSER() {
        return GEYSER;
    }

    public static Amenity getHighSpeedWifi() {
        return HIGH_SPEED_WIFI;
    }

    public static Amenity getModernWardrobe() {
        return MODERN_WARDROBE;
    }

    public static Amenity getStudyLampTable() {
        return STUDY_LAMP_TABLE;
    }

    public static Amenity getWashingMachine() {
        return WASHING_MACHINE;
    }

    public static ArrayList<Amenity> getAmenities(){
        return AMENITIES;
    }

}
