package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

class InputHelper {
    private static final Scanner input=new Scanner(System.in);
    private static boolean lastNumberValue =false;
    static int getIntegerInput(){
        lastNumberValue =true;
        int value;
        try{
            value=input.nextInt();
        }
        catch(InputMismatchException e){
            input.nextLine();
            System.out.println(PrintStatements.ENTER_VALUE_IN_INTEGER_FORMAT);
            value=getIntegerInput();
        }
        return value;

    }

    static int getWholeNumberIntegerInput(){
        lastNumberValue =true;
        int value;
        try{
            value=input.nextInt();
            if(value<0){
                throw new RuntimeException(PrintStatements.WHOLE_NUMBER_CONDITION);
            }
        }
        catch (InputMismatchException e){
            input.nextLine();
            System.out.println(PrintStatements.ENTER_VALUE_IN_INTEGER_FORMAT);
            value=getWholeNumberIntegerInput();
        } catch (Exception e) {
            value=getWholeNumberIntegerInput();
        }
        return value;
    }

    static int getPositiveInput(){
        lastNumberValue =true;
        int value;
        try{
            value=input.nextInt();
            if(value<1){
                throw new RuntimeException(PrintStatements.POSITIVE_NUMBER_CONDITION);
            }
        }
        catch (InputMismatchException e){
            input.nextLine();
            System.out.println(PrintStatements.ENTER_VALUE_IN_INTEGER_FORMAT);
            value=getWholeNumberIntegerInput();
        } catch (Exception e) {
            value=getWholeNumberIntegerInput();
        }
        return value;
    }



    static String getStringInput(){
        String value;
        if(lastNumberValue){
            input.nextLine();
        }
        lastNumberValue =false;
        try{
            value=input.nextLine().trim();
            if(value.isEmpty()){
                throw new RuntimeException(PrintStatements.FIELD_CANT_BE_EMPTY);
            }
        }
        catch(InputMismatchException e){
            System.out.println(PrintStatements.ENTER_VALUE_IN_STRING_FORMAT);
            value=getStringInput();
        }
        catch(Exception e){
            value=getStringInput();
        }
        return value;
    }

    static String getEmailInput(){
        String email=getStringInput();

        if(!Validator.emailValidator(email)){
            System.out.println(PrintStatements.ENTER_VALID_EMAIL);
            return getEmailInput();
        }
        else{
            return email;
        }

    }

    static double getDoubleInput(){
        lastNumberValue =true;
        double value;
        try{
            value=input.nextDouble();
        }
        catch (InputMismatchException e){
            input.nextLine();
            System.out.println(PrintStatements.ENTER_VALUE_IN_INTEGER_FORMAT);
            value=getDoubleInput();
        }
        return value;

    }


    static long getPhoneNumber(){
        lastNumberValue =true;
        long phoneNumber;
        try{
            phoneNumber=input.nextLong();
            if(String.valueOf(phoneNumber).length()!=10){
                throw new RuntimeException(PrintStatements.ENTER_VALID_PHONE_NUMBER);
            }
        }catch (InputMismatchException e){
            input.nextLine();
            System.out.println(PrintStatements.ENTER_VALID_PHONE_NUMBER);
            phoneNumber=getPhoneNumber();
        } catch (Exception e) {
            phoneNumber=getPhoneNumber();
        }
        return phoneNumber;
    }

    static Object getPhoneNumberOrEmail(){
        String mail_phone;
        try{
            mail_phone=getStringInput();
            if(Validator.emailValidator(mail_phone)){
                return mail_phone;
            }
            else if(Validator.phoneNumberValidator(mail_phone)){
                return Long.valueOf(mail_phone);
            }
            else{
                throw new RuntimeException();
            }

        } catch (Exception e) {
            System.out.println("Please Enter Valid Phone Number or Mail Id..");
            return getPhoneNumberOrEmail();
        }

    }

    static int getPostalCode(){
        lastNumberValue =true;
        int postalCode;
        try{
            postalCode=input.nextInt();
            if(String.valueOf(postalCode).length()!=6){
                throw new RuntimeException(PrintStatements.ENTER_VALID_POSTAL_CODE);
            }
        }catch (InputMismatchException e){
            input.nextLine();
            System.out.println(PrintStatements.ENTER_VALID_POSTAL_CODE);
            postalCode=getPostalCode();
        } catch (Exception e) {
            postalCode=getPostalCode();
        }
        return postalCode;
    }

    static int getInputWithinRange(int end,String str){
        if(str==null){
            str= PrintStatements.ENTER_INPUT_FROM_GIVEN_OPTION;
        }
        int start=1;
        int value=InputHelper.getIntegerInput();
        if(value<start||value>end){
            System.out.println(str);
            return getInputWithinRange(end,str);
        }
        return value;
    }

    static int getInputWithinRange(int start,int end,String str){
        if(str==null){
            str= PrintStatements.ENTER_INPUT_FROM_GIVEN_OPTION;
        }
        int value=InputHelper.getIntegerInput();
        if(value<start||value>end){
            System.out.println(str);
            return getInputWithinRange(start,end,str);
        }
        return value;
    }

    static double getDoubleInputWithinRange(int start,int end,String str){
        if(str==null){
            str=PrintStatements.ENTER_INPUT_FROM_GIVEN_OPTION;
        }
        double value=InputHelper.getDoubleInput();
        if(value<start||value>end){
            System.out.println(str);
            return getInputWithinRange(start,end,str);
        }
        return value;
    }


    static Date getDate(){
        System.out.println(PrintStatements.ENTER_DATE_IN_FORMAT);
        String dateStr=getStringInput();

        if(!Validator.dateValidator(dateStr)){
            System.out.println(PrintStatements.ENTER_VALID_DATE);
            return getDate();
        }
        else{
            Date date=convertStringtoDate(dateStr);
            return date;
        }

    }

    static Date convertStringtoDate(String dateStr){
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

    static Date getCurrentDate(){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        Date date = cal.getTime();
        return date;

    }


    static ArrayList<Date> getDatesBetweenTwoDates(Date startDate,Date endDate){
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

    static Calendar getCalendarWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    static String getSimpleDateWithoutYear(Date date){
        SimpleDateFormat dateFormat= new SimpleDateFormat("EEE,dd MMM");
        return dateFormat.format(date);
    }

    static void pressEnterToContinue()
    {
        System.out.println(PrintStatements.PRESS_ENTER_TO_CONTINUE);
        try
        {
            System.in.read();
            //input.nextLine();
        }
        catch(Exception e)
        {

        }
    }

    static String modifyString(String str){
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

    static ArrayList<String> getFileInput(){
        ArrayList<String> textFile=new ArrayList<>();
        try {
            System.out.println(PrintStatements.ENTER_FILE_PATH);
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
            System.out.println(PrintStatements.FILE_NOT_EXIST);
        }
        return textFile;
    }

    static void printFile(ArrayList<String> files){
        for (String file : files) {
            System.out.println(file);
        }
    }

    static double findParts(double percent,double whole){
        double d=whole*percent;
        return d/100;
    }


}
