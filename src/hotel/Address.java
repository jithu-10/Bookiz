package hotel;

public class Address {
    private int buildingNo;
    private String street;
    private String locality;
    private String city;
    private String state;
    private int postalCode;

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

    public void setBuildingNo(int buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

}
