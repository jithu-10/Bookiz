package admin;

import user.User;

public class Admin extends User {

    private static final Admin admin=new Admin();

    private Admin(){

    }

    public static Admin getInstance(){
        return admin;
    }



}
