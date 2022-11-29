package hotelbooking;

public class Price {
    private final double basePrice;
    private final double maxPrice;
    private double listPrice;

    public Price(double basePrice,double maxPrice){
        validateBasePrice(basePrice);
        validateMaxPrice(basePrice,maxPrice);
        this.basePrice=basePrice;
        this.maxPrice=maxPrice;
        this.listPrice=basePrice;
    }


    public void setListPrice(double listPrice){
        validateListPrice(listPrice,basePrice,maxPrice);
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

    private static void validateBasePrice(double basePrice){
        if(basePrice<100){
            throw new RuntimeException("Base Price Should be Greater than "+100);
        }
    }

    private static void validateMaxPrice(double basePrice,double maxPrice){
        if(maxPrice<=basePrice){
            throw new RuntimeException("Max Price should be greater than Base Price : "+basePrice);
        }
    }

    private static void validateListPrice(double listPrice,double basePrice,double maxPrice){
        if(listPrice<basePrice||listPrice>maxPrice){
            throw new RuntimeException("List Price should be greater than base Price and should be lesser than max Price");
        }
    }


    

}
