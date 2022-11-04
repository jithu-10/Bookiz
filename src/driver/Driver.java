package driver;

import user.User;
import user.UserType;

public interface Driver {
    void startDriver();
    User signIn();
    void menu(User user);
    void register();
    boolean acceptTermsAndConditions();

}
