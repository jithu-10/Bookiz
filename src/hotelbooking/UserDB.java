package hotelbooking;

import java.util.ArrayList;

public class UserDB  {
    private static int idHelper=0;
    private static final UserDB USER_DB =new UserDB();
    private final ArrayList<User> hotelAdminList =new ArrayList<>();
    private final ArrayList<User> customerList=new ArrayList<>();

    private UserDB(){

    }

    public static UserDB getInstance(){
        return USER_DB;
    }
    int generateId(){
        return ++idHelper;
    }


    public void addCustomer(User user,String password){
        Helper.validateNull(user,"User can't be null");
        Helper.validateString(password,"Password can't be null or empty");
        customerList.add(user);
        UserAuthenticationDB.getInstance().addCustomerAuth(user.getPhoneNumber(),user.getMailID(),password);
    }


    public void addHotelAdmin(User user,String password){
        Helper.validateNull(user,"User can't be null");
        Helper.validateString(password,"Password can't be null or empty");
        if(isHotelEmailExist(user.getMailID())||isHotelPhoneNumberExist(user.getPhoneNumber())){
            throw new RuntimeException("User Mail-ID or Phone Number already exist in the database ");

        }
        hotelAdminList.add(user);
        UserAuthenticationDB.getInstance().addHotelAdminAuth(user.getPhoneNumber(),user.getMailID(),password);
    }


    public User getHotelAdminByPhoneNumber_Mail(Object phone_mail,String password){
        Helper.validateNull(phone_mail,"Phone or Mail can't be null");
        Helper.validateString(password,"Password can't be null or empty");
        if(!UserAuthenticationDB.getInstance().authenticateHotelAdmin(phone_mail,password)){
            return null;
        }
        for(User user: hotelAdminList){
            if(phone_mail instanceof Long){
                if(phone_mail.equals(user.getPhoneNumber())){
                    return user;
                }

            }
            else if(phone_mail instanceof String){
                if(phone_mail.equals(user.getMailID())){
                    return user;
                }

            }
        }
        return null;
    }

    public User getCustomerByPhoneNumber_Mail(Object phone_mail,String password){
        Helper.validateNull(phone_mail,"Phone or Mail can't be null");
        Helper.validateString(password,"Password can't be null or empty");
        if(!UserAuthenticationDB.getInstance().authenticateCustomer(phone_mail,password)){
            return null;
        }
        for(User customer:customerList){
            if(phone_mail instanceof Long){
                if(phone_mail.equals(customer.getPhoneNumber())){
                    return customer;
                }

            }
            else if(phone_mail instanceof String){
                if(phone_mail.equals(customer.getMailID())){
                    return customer;
                }

            }
        }
        return null;
    }





    public boolean isHotelEmailExist(String email){
        Helper.validateMailID(email);
        return UserAuthenticationDB.getInstance().isHotelEmailExist(email);
    }

    public boolean isHotelPhoneNumberExist(Long phoneNumber){
        Helper.validatePhoneNumber(phoneNumber);
        return UserAuthenticationDB.getInstance().isHotelPhoneNumberExist(phoneNumber);
    }

    public boolean isCustomerEmailExist(String email){
        Helper.validateMailID(email);
        return UserAuthenticationDB.getInstance().isCustomerEmailExist(email);
    }

    public boolean isCustomerPhoneNumberExist(Long phoneNumber){
        Helper.validatePhoneNumber(phoneNumber);
        return UserAuthenticationDB.getInstance().isCustomerPhoneNumberExist(phoneNumber);
    }


}
