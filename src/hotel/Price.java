package hotel;

public class Price {
    private double basePrice;
    private double maxPrice;
    private double listPrice;

    public Price(double basePrice,double maxPrice){
        this.basePrice=basePrice;
        this.maxPrice=maxPrice;
        this.listPrice=basePrice;
    }

    public void setListPrice(double listPrice){
        this.listPrice = listPrice;
    }

    public double getListPrice() {
        return listPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
