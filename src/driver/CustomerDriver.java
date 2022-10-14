package driver;

public class CustomerDriver implements Driver {

    static final CustomerDriver customerDriver=new CustomerDriver();


    private CustomerDriver(){

    }

    static CustomerDriver getInstance(){
        return customerDriver;
    }

    @Override
    public void startDriver() {

    }

    @Override
    public boolean signIn() {
        return false;
    }

    @Override
    public void menu() {

    }
}
