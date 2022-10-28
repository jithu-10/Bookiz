package utility;

public class ValidateException extends Exception{
    ValidateException(String statement){
        System.out.println(statement);
    }

    ValidateException(){

    }
}
