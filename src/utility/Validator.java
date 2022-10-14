package utility;

public class Validator {

    public static boolean confirmPasswordValidatator(String password,String confirmPassword){
        return password.equals(confirmPassword);
    }
}
