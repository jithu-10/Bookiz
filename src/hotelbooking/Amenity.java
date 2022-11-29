package hotelbooking;

public class Amenity {

    private final int amenityID;
    private final String name;
    private final int points;

    public Amenity(String name,int points){
        Helper.validateString(name,"Amenity name can't be null or empty");
        validatePoints(points);
        this.amenityID=AmenityDB.getInstance().generateId();
        this.name=name;
        this.points=points;
    }

    public String getName(){
        return this.name;
    }

    public int getPoints(){
        return this.points;
    }

    int getAmenityID(){
        return amenityID;
    }

    private static void validatePoints(int points){
        if(points<1||points>100){
            throw new RuntimeException("Amenity Points should be greater than 0 or less than 100");
        }
    }


}
