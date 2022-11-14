package hotel;

public class Amenity {

    private final String name;
    private final int points;
    private int amenityID;

    public Amenity(String name,int points){
        this.name=name;
        this.points=points;
    }

    public String getName(){
        return this.name;
    }

    public int getPoints(){
        return this.points;
    }

    public void setAmenityID(int amenityID){
        this.amenityID=amenityID;
    }

    public int getAmenityID(){
        return amenityID;
    }


}
