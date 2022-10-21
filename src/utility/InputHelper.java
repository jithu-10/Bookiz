package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if(value.equals("")){
                throw new ValidateException();
            }
        }
        catch(InputMismatchException e){
            System.out.println(Printer.ENTER_VALUE_IN_STRING_FORMAT);
            value=getStringInput();
        }
        catch(ValidateException e){

        }
        return value;
    }

    public static String getEmailInput(){
        String email=getStringInput();
        Pattern p = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        Matcher m = p.matcher(email);
        if(!m.matches()){
            System.out.println("Please Enter Valid Email ID");
            return getEmailInput();
        }
        else{
            return email;
        }

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

    public static int getPostalCode(){
        lastIntegerValue=true;
        int postalCode=0;
        try{
            postalCode=input.nextInt();
            if(String.valueOf(postalCode).length()!=6){
                throw new ValidateException();
            }
        }catch (InputMismatchException e){
            input.nextLine();
            System.out.println("Enter Valid Postal Code");
            postalCode=getPostalCode();
        } catch (ValidateException e) {
            System.out.println("Enter Valid Postal Code");
            postalCode=getPostalCode();
        }
        return postalCode;
    }

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

    public static Date getDate(){
        System.out.println("Enter Date in dd-mm-yyyy format");
        String dateStr=getStringInput();
        Pattern p = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
        Matcher m = p.matcher(dateStr);
        if(!m.matches()){
            System.out.println("Please enter date in exact format");
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
        String dateOnly = dateFormat.format(date);
        return dateOnly;
    }

    public static void pressEnterToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
            input.nextLine();
        }
        catch(Exception e)
        {

        }
    }

    public static String modifyString(String str){
        String newStr="";
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==' '){
                continue;
            }
            newStr+=str.charAt(i);
        }
        newStr=newStr.toUpperCase();
        return newStr;
    }

    public static ArrayList<String> getFileInput(){
        ArrayList<String> textFile=new ArrayList<>();
        try {
            System.out.println("Enter File Path : ");
            String filePath=InputHelper.getStringInput();
            File file = new File(filePath);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                int j=0;
                String separatedData="";
                for(int i=0;i<data.length();i++){
                    j++;
                    separatedData+=data.charAt(i);
                    if(j==100){
                        j=0;
                        textFile.add(separatedData);
                        separatedData="";
                    }
                }
                textFile.add(separatedData);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Exist");
        }
        return textFile;
    }

    public static void printFile(ArrayList<String> files){
        for(int i=0;i<files.size();i++){
            System.out.println(files.get(i));
        }
    }


}
