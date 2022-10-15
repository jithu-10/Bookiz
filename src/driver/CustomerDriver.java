package driver;

import user.User;

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
    public User signIn() {
        return null;
    }

    @Override
    public void menu(User user) {

    }
}
