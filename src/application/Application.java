package application;

import hotelbooking.User;

public interface Application {
    void startApp();
    void register();
    User signIn();
    void menu(User user);
    boolean acceptTermsAndConditions();

}
