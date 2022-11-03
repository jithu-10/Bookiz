package booking;

public class CustomerBooking extends Booking{


    private int noOfSingleBedRoomsBooked;
    private int noOfDoubleBedRoomsBooked;
    private int noOfSuiteRoomsBooked;
    private double totalPriceOfSingleBedRooms;
    private double totalPriceOfDoubleBedRooms;
    private double totalPriceOfSuiteRooms;
    private boolean paid;


    public int getNoOfSingleBedroomsBooked() {
        return noOfSingleBedRoomsBooked;
    }

    public void setNoOfSingleBedroomsBooked(int noOfSingleBedroomsBooked) {
        this.noOfSingleBedRoomsBooked = noOfSingleBedroomsBooked;
    }

    
    public int getNoOfDoubleBedRoomsBooked() {
        return noOfDoubleBedRoomsBooked;
    }

    public void setNoOfDoubleBedRoomsBooked(int noOfDoubleBedRoomsBooked) {
        this.noOfDoubleBedRoomsBooked = noOfDoubleBedRoomsBooked;
    }

    
    public int getNoOfSuiteRoomsBooked() {
        return noOfSuiteRoomsBooked;
    }

    public void setNoOfSuiteRoomsBooked(int noOfSuiteRoomsBooked) {
        this.noOfSuiteRoomsBooked = noOfSuiteRoomsBooked;
    }

    public int getTotalNoOfRoomsBooked() {
        return noOfSingleBedRoomsBooked + noOfDoubleBedRoomsBooked + noOfSuiteRoomsBooked;
    }

    public double getTotalPriceOfSingleBedRooms() {
        return totalPriceOfSingleBedRooms;
    }

    public void setTotalPriceOfSingleBedRooms(double totalPriceOfSingleBedRooms) {
        this.totalPriceOfSingleBedRooms = totalPriceOfSingleBedRooms;
    }

    public double getTotalPriceOfDoubleBedRooms() {
        return totalPriceOfDoubleBedRooms;
    }

    public void setTotalPriceOfDoubleBedRooms(double totalPriceOfDoubleBedRooms) {
        this.totalPriceOfDoubleBedRooms = totalPriceOfDoubleBedRooms;
    }

    public double getTotalPriceOfSuiteRooms() {
        return totalPriceOfSuiteRooms;
    }

    public void setTotalPriceOfSuiteRooms(double totalPriceOfSuiteRooms) {
        this.totalPriceOfSuiteRooms = totalPriceOfSuiteRooms;
    }
    public double getTotalPrice(){
        return totalPriceOfSingleBedRooms+totalPriceOfDoubleBedRooms+totalPriceOfSuiteRooms;
    }

    public void setPaid(){
        paid=true;
    }
    public boolean getPaid(){
        return paid;
    }
}
