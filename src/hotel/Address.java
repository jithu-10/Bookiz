package hotel;

public class Address {
    private final int buildingNo;
    private final String street;
    private final String locality;
    private final String city;
    private final String state;
    private final int postalCode;

    public Address(int buildingNo,String street,String locality,String city,String state, int postalCode){
        this.buildingNo=buildingNo;
        this.street=(street==null)?"":street;
        this.locality=(locality==null)?"":locality;
        this.city=(city==null)?"":city;
        this.state=(state==null)?"":state;
        this.postalCode=postalCode;
    }
    public int getBuildingNo() {
        return buildingNo;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getLocality() {
        return locality;
    }
    public String getState() {
        return state;
    }
    public int getPostalCode() {
        return postalCode;
    }


}
