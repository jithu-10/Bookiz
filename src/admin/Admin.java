package admin;

public class Admin {
    private static final String USER_NAME="admin";
    private static final String PASSWORD="pass";

    public static boolean checkAuthentication(String userName,String password){
        if(userName.equals(USER_NAME)&&password.equals(PASSWORD)){
            return true;
        }
        return false;
    }

}
