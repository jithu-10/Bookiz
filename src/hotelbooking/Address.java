package hotelbooking;

public class Address {
    private final String buildingNo;
    private final String street;
    private final String locality;
    private final String city;
    private final String state;
    private final int postalCode;

    public Address(String buildingNo,String street,String locality,String city,String state, int postalCode){
        Helper.validateString(city,"City can't be null or empty");
        Helper.validateString(city,"Locality can't be null or empty");
        Helper.validateString(city,"State can't be null or empty");
        validatePostalCode(postalCode);
        this.buildingNo=buildingNo;
        this.street=(street==null)?"":street;
        this.locality=locality;
        this.city=city;
        this.state=state;
        this.postalCode=postalCode;
    }
    public String getBuildingNo() {
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

    private static void validatePostalCode(int postalCode){
        if(String.valueOf(postalCode).length()!=6){
            throw new RuntimeException("Postal Code is not valid");
        }
    }
}
