package admin;

import user.User;

public class Admin extends User {
    private static final String USER_NAME="admin";
    private static final String PASSWORD="pass";
    private static final Admin admin=new Admin();

    public static boolean checkAuthentication(String userName,String password){
        if(userName.equals(USER_NAME)&&password.equals(PASSWORD)){
            return true;
        }
        return false;
    }

    private Admin(){

    }

    public static Admin getInstance(){
        return admin;
    }



}
