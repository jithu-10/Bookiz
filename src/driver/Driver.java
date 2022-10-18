package driver;

import user.User;

public interface Driver {
    void startDriver();
    User signIn();
    void menu(User user);
    void register();
}
