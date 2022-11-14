package user;

import java.util.HashMap;

public class UserAuthenticationDB {

    private final HashMap<String,String> adminAuthentication=new HashMap<>();
    private final HashMap<Long,String> hotelAdminAuthentication =new HashMap<>();
    private final HashMap<String,String> hotelAdminAuthenticationByEmailID=new HashMap<>();
    private final HashMap<Long,String> customerAuthentication=new HashMap<>();
    private final HashMap<String,String> customerAuthenticationByEmailID=new HashMap<>();
    private static final UserAuthenticationDB USER_AUTHENTICATION_DB =new UserAuthenticationDB();

    private UserAuthenticationDB(){

    }

    public static UserAuthenticationDB getInstance(){
        return USER_AUTHENTICATION_DB;
    }

    public void addAdminAuth(String userName,String password){
        adminAuthentication.put(userName,password);
    }
    public void addHotelAdminAuth(Long phoneNumber,String email,String password){
        hotelAdminAuthentication.put(phoneNumber,password);
        hotelAdminAuthenticationByEmailID.put(email,password);
    }
    public void addCustomerAuth(Long phoneNumber,String email,String password){
        customerAuthentication.put(phoneNumber,password);
        customerAuthenticationByEmailID.put(email,password);
    }
    public boolean authenticateAdmin(String userName,String password){
        if(adminAuthentication.containsKey(userName)){
            String orgPassword=adminAuthentication.get(userName);
            return orgPassword.equals(password);
        }
        return false;
    }

    public boolean authenticateHotelAdmin(Object mailOrPhone,String password){

        if(mailOrPhone instanceof String){
            if(hotelAdminAuthenticationByEmailID.containsKey(mailOrPhone)){
                return hotelAdminAuthenticationByEmailID.get(mailOrPhone).equals(password);
            }
            return false;
        }
        else if(mailOrPhone instanceof Long){
            if(hotelAdminAuthentication.containsKey(mailOrPhone)){
                String orgPassword=hotelAdminAuthentication.get(mailOrPhone);
                return orgPassword.equals(password);
            }
        }

        return false;
    }


    public boolean authenticateCustomer(Object mailOrPhone,String password){
        if(mailOrPhone instanceof String){
            if(customerAuthenticationByEmailID.containsKey(mailOrPhone)){
                return customerAuthenticationByEmailID.get(mailOrPhone).equals(password);
            }
            return false;
        }
        else if(mailOrPhone instanceof Long){
            if(customerAuthentication.containsKey(mailOrPhone)){
                String orgPassword=customerAuthentication.get(mailOrPhone);
                return orgPassword.equals(password);
            }
        }

        return false;
    }


    public boolean isHotelPhoneNumberExist(Long phoneNumber){
        if(hotelAdminAuthentication.containsKey(phoneNumber)){
            return true;
        }
        return false;
    }

    public boolean isCustomerPhoneNumberExist(Long phoneNumber){
        if(customerAuthentication.containsKey(phoneNumber)){
            return true;
        }
        return false;
    }

    public boolean isHotelEmailExist(String email){
        if(hotelAdminAuthenticationByEmailID.containsKey(email)){
            return true;
        }
        return false;
    }

    public boolean isCustomerEmailExist(String email){
        if(customerAuthenticationByEmailID.containsKey(email)){
            return true;
        }
        return false;
    }



}
