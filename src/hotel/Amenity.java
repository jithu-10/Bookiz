package hotel;

public class Amenity {
    private String name;
    private int points;

    Amenity(String name,int points){
        this.name=name;
        this.points=points;
    }

    public String getName(){
        return this.name;
    }

    public int getPoints(){
        return this.points;
    }

}
