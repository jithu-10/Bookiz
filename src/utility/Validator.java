package utility;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean confirmPasswordValidatator(String password,String confirmPassword){
        return password.equals(confirmPassword);
    }

    public static boolean dateValidator(String date){
        Pattern p = Pattern.compile(
                "^(29-02-(2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26])))$"
                        + "|^((0[1-9]|1[0-9]|2[0-8])-02-((19|2[0-9])[0-9]{2}))$"
                        + "|^((0[1-9]|[12][0-9]|3[01])-(0[13578]|10|12)-((19|2[0-9])[0-9]{2}))$"
                        + "|^((0[1-9]|[12][0-9]|30)-(0[469]|11)-((19|2[0-9])[0-9]{2}))$");
        //Pattern p = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
        //Pattern p = Pattern.compile("^(0[1-9]|[1-2][0-9]|3[0-1]|)-(0[1-9]|1[0-2])-\\d{4}$");
        Matcher m = p.matcher(date);
        return m.matches();
    }

    public static boolean emailValidator(String email){
        return regexValidator("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",email);
    }

    public static boolean phoneNumberValidator(String phoneNumber){
        return regexValidator("([1-9]\\d{9})",phoneNumber);
    }

    public static boolean regexValidator(String regex,String value){
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(value);
        return matcher.matches();
    }


}
