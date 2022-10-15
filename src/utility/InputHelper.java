package utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    private static Scanner input=new Scanner(System.in);
    private static boolean lastIntegerValue=false;
    public static int getIntegerInput(){
        lastIntegerValue=true;
        int value=0;
        try{
            value=input.nextInt();
        }
        catch(InputMismatchException e){
            input.nextLine();
            System.out.println(Printer.ENTER_VALUE_IN_INTEGER_FORMAT);
            value=getIntegerInput();
        }
        return value;

    }

    public static String getStringInput(){
        String value=null;
        if(lastIntegerValue){
            input.nextLine();
        }
        lastIntegerValue=false;
        try{
            value=input.nextLine();
        }
        catch(InputMismatchException e){
            System.out.println(Printer.ENTER_VALUE_IN_STRING_FORMAT);
            value=getStringInput();
        }
        return value;
    }

    public static double getDoubleInput(){
        lastIntegerValue=true;
        double value=0;
        try{
            value=input.nextDouble();
        }
        catch (InputMismatchException e){
            input.nextLine();
            System.out.println(Printer.ENTER_VALUE_IN_INTEGER_FORMAT);
            value=getDoubleInput();
        }
        return value;

    }

    public static long getPhoneNumber(){
        lastIntegerValue=true;
        long phoneNumber=0;
        try{
            //validate phone number here
            phoneNumber=input.nextLong();
            if(String.valueOf(phoneNumber).length()!=10){
                throw new ValidateException();
            }
        }catch (InputMismatchException e){
            input.nextLine();
            System.out.println("Enter Valid Phone Number");
            phoneNumber=getPhoneNumber();
        } catch (ValidateException e) {
            System.out.println("Enter Valid Phone Number");
            phoneNumber=getPhoneNumber();
        }
        return phoneNumber;
    }

    /*
    public static int getTwoOptionsInput(){
        int value=InputHelper.getIntegerInput();
        if(value<1||value>2){
            System.out.println("Please enter input only from the options : ");
            return getTwoOptionsInput();
        }
        return value;
    }
     */

    public static int getInputWithinRange(int end,String str){
        if(str==null){
            str="Please enter input only from the options : ";
        }
        int start=1;
        int value=InputHelper.getIntegerInput();
        if(value<start||value>end){
            System.out.println(str);
            return getInputWithinRange(end,str);
        }
        return value;
    }







}
