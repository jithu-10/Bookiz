package hotel.subutil;

public class Price {
    private double basePrice;
    private double maxPrice;
    private double currentPrice;

    public Price(double basePrice,double maxPrice){
        this.basePrice=basePrice;
        this.maxPrice=maxPrice;
    }

    public void setCurrentPrice(double currentPrice){
        this.currentPrice=currentPrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
