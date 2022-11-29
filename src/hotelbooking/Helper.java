package hotelbooking;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Helper {


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

    private static Calendar getCalendarWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    static void validateCheckInDate(Date checkInDate){
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        Date currentDate = cal.getTime();
        int value=checkInDate.compareTo(currentDate);
        if(value==-1){
            throw new RuntimeException("You can't book hotel for previous dates");
        }
        else{

            int noOfInBetweenDays= getDatesBetweenTwoDates(currentDate,checkInDate).size();
            if(noOfInBetweenDays>60){
                throw new RuntimeException("You can book hotel room only up to 60 days");
            }
        }

    }

    static void validateCheckOutDate(Date checkInDate,Date checkOutDate){
        int value=checkOutDate.compareTo(checkInDate);
        if(value==-1||value==0){
            throw new RuntimeException("Check out Date will be greater than Check In Date");
        }
        else{
            int noOfInBetweenDays=getDatesBetweenTwoDates(checkInDate,checkOutDate).size();
            if(noOfInBetweenDays>150){
                throw new RuntimeException("You can book only for next 150 days");
            }
        }

    }


    static void validateNull(Object object, String message){
        if(object==null){
            throw new NullPointerException(message);
        }
    }

    static String modifyString(String str){
       return  str.trim().toUpperCase();
//        StringBuilder newStr= new StringBuilder();
//        for(int i=0;i<str.length();i++){
//            if(str.charAt(i)==' '){
//                continue;
//            }
//            newStr.append(str.charAt(i));
//        }
//        newStr = new StringBuilder(newStr.toString().toUpperCase());
//        return newStr.toString();
    }

    static void validateString(String string,String message){
        string=string.trim();
        if(string.isEmpty()){
            throw new RuntimeException(message);
        }
        validateNull(string,message);
    }

    static void validatePhoneNumber(long phoneNumber){
        Pattern pattern=Pattern.compile("([1-9]\\d{9})");
        Matcher matcher=pattern.matcher(String.valueOf(phoneNumber));
        if(!matcher.matches()){
            throw new RuntimeException("Phone Number is Not Valid");
        }
    }

    static void validateMailID(String mailID){
        Pattern pattern=Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher=pattern.matcher(mailID);
        if(!matcher.matches()){
            throw  new RuntimeException("E-Mail is Not Valid");
        }
    }


}
