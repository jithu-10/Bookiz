package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHelper {
    private static final Scanner input=new Scanner(System.in);
    private static boolean lastIntegerValue=false;
    public static int getIntegerInput(){
        lastIntegerValue=true;
        int value;
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

    public static int getWholeNumberIntegerInput(){
        lastIntegerValue=true;
        int value;
        try{
            value=input.nextInt();
            if(value<0){
                throw new ValidateException(Printer.WHOLE_NUMBER_CONDITION);
            }
        }
        catch (InputMismatchException e){
            input.nextLine();
            System.out.println(Printer.ENTER_VALUE_IN_INTEGER_FORMAT);
            value=getWholeNumberIntegerInput();
        } catch (ValidateException e) {
            value=getWholeNumberIntegerInput();
        }
        return value;
    }

    public static int getPositiveInput(){
        lastIntegerValue=true;
        int value;
        try{
            value=input.nextInt();
            if(value<1){
                throw new ValidateException(Printer.POSITIVE_NUMBER_CONDITION);
            }
        }
        catch (InputMismatchException e){
            input.nextLine();
            System.out.println(Printer.ENTER_VALUE_IN_INTEGER_FORMAT);
            value=getWholeNumberIntegerInput();
        } catch (ValidateException e) {
            value=getWholeNumberIntegerInput();
        }
        return value;
    }



    public static String getStringInput(){
        String value;
        if(lastIntegerValue){
            input.nextLine();
        }
        lastIntegerValue=false;
        try{
            value=input.nextLine();
            if(value.equals("")){
                throw new ValidateException(Printer.FIELD_CANT_BE_EMPTY);
            }
        }
        catch(InputMismatchException e){
            System.out.println(Printer.ENTER_VALUE_IN_STRING_FORMAT);
            value=getStringInput();
        }
        catch(ValidateException e){
            value=getStringInput();
        }
        return value;
    }

    public static String getEmailInput(){
        String email=getStringInput();

        if(!Validator.emailValidator(email)){
            System.out.println(Printer.ENTER_VALID_EMAIL);
            return getEmailInput();
        }
        else{
            return email;
        }

    }

    public static double getDoubleInput(){
        lastIntegerValue=true;
        double value;
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
        long phoneNumber;
        try{
            phoneNumber=input.nextLong();
            if(String.valueOf(phoneNumber).length()!=10){
                throw new ValidateException(Printer.ENTER_VALID_PHONE_NUMBER);
            }
        }catch (InputMismatchException e){
            input.nextLine();
            System.out.println(Printer.ENTER_VALID_PHONE_NUMBER);
            phoneNumber=getPhoneNumber();
        } catch (ValidateException e) {
            phoneNumber=getPhoneNumber();
        }
        return phoneNumber;
    }

    public static int getPostalCode(){
        lastIntegerValue=true;
        int postalCode;
        try{
            postalCode=input.nextInt();
            if(String.valueOf(postalCode).length()!=6){
                throw new ValidateException(Printer.ENTER_VALID_POSTAL_CODE);
            }
        }catch (InputMismatchException e){
            input.nextLine();
            System.out.println(Printer.ENTER_VALID_POSTAL_CODE);
            postalCode=getPostalCode();
        } catch (ValidateException e) {
            postalCode=getPostalCode();
        }
        return postalCode;
    }

    public static int getInputWithinRange(int end,String str){
        if(str==null){
            str=Printer.ENTER_INPUT_FROM_GIVEN_OPTION;
        }
        int start=1;
        int value=InputHelper.getIntegerInput();
        if(value<start||value>end){
            System.out.println(str);
            return getInputWithinRange(end,str);
        }
        return value;
    }

    public static Date getDate(){
        System.out.println(Printer.ENTER_DATE_IN_FORMAT);
        String dateStr=getStringInput();

        if(!Validator.dateValidator(dateStr)){
            System.out.println(Printer.ENTER_VALID_DATE);
            return getDate();
        }
        else{
            Date date=convertStringtoDate(dateStr);
            return date;
        }

    }

    public static Date convertStringtoDate(String dateStr){
        int day=0;
        int month=0;
        int year;
        boolean dateSet=false;
        boolean monthSet=false;
        int value=0;
        for(int i=0;i<dateStr.length();i++){
            if(dateStr.charAt(i)=='-'){
                if(!dateSet){
                    dateSet=true;
                    day=value;
                    value=0;
                }
                else if(!monthSet){
                    monthSet=true;
                    month=value;
                    value=0;
                }
            }
            else{
                value=value*10;
                value+=(dateStr.charAt(i)-48);
            }
        }
        year=value;
        Date date=null;
        try{
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            date=sdFormat.parse(year+"-"+month+"-"+day);
        }
        catch (Exception e){}

        return date;

    }

    public static Date setTime(Date date){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        date = cal.getTime();
        return date;

    }


    public static ArrayList<Date> getDatesBetweenTwoDates(Date startDate,Date endDate){
        ArrayList<Date> datesInRange=new ArrayList<>();
        Calendar calendar = getCalendarWithoutTime(startDate);
        Calendar endCalendar = getCalendarWithoutTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    public static Calendar getCalendarWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static String getSimpleDateWithoutYear(Date date){
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEE,dd MMM");
        return dateFormat.format(date);
    }

    public static void pressEnterToContinue()
    {
        System.out.println(Printer.PRESS_ENTER_TO_CONTINUE);
        try
        {
            System.in.read();
            //input.nextLine();
        }
        catch(Exception e)
        {

        }
    }

    public static String modifyString(String str){
        StringBuilder newStr= new StringBuilder();
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==' '){
                continue;
            }
            newStr.append(str.charAt(i));
        }
        newStr = new StringBuilder(newStr.toString().toUpperCase());
        return newStr.toString();
    }

    public static ArrayList<String> getFileInput(){
        ArrayList<String> textFile=new ArrayList<>();
        try {
            System.out.println(Printer.ENTER_FILE_PATH);
            String filePath=InputHelper.getStringInput();
            File file = new File(filePath);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                int j=0;
                StringBuilder separatedData= new StringBuilder();
                for(int i=0;i<data.length();i++){
                    j++;
                    separatedData.append(data.charAt(i));
                    if(j==100){
                        j=0;
                        textFile.add(separatedData.toString());
                        separatedData = new StringBuilder();
                    }
                }
                textFile.add(separatedData.toString());

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println(Printer.FILE_NOT_EXIST);
        }
        return textFile;
    }

    public static void printFile(ArrayList<String> files){
        for (String file : files) {
            System.out.println(file);
        }
    }


}
