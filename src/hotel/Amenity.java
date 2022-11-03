package hotel;

public class Amenity {

    private int amenityID;
    private String name;
    private int points;

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
