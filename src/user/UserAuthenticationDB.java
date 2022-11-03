package user;

import java.util.HashMap;

public class UserAuthenticationDB {

    private final HashMap<String,String> adminAuthentication=new HashMap<>();
    private final HashMap<Long,String> hotelAdminAuthentication =new HashMap<>();
    private final HashMap<Long,String> customerAuthentication=new HashMap<>();
    private static final UserAuthenticationDB userAuthenticationDB=new UserAuthenticationDB();

    private UserAuthenticationDB(){

    }

    public static UserAuthenticationDB getInstance(){
        return userAuthenticationDB;
    }

    public void addAdminAuth(String userName,String password){
        adminAuthentication.put(userName,password);
    }
    public void addHotelAdminAuth(Long phoneNumber, String password){
        hotelAdminAuthentication.put(phoneNumber,password);
    }
    public void addCustomerAuth(Long phoneNumber,String password){
        customerAuthentication.put(phoneNumber,password);
    }

    public boolean authenticateAdmin(String userName,String password){
        if(adminAuthentication.containsKey(userName)){
            String orgPassword=adminAuthentication.get(userName);
            return orgPassword.equals(password);
        }
        return false;
    }

    public boolean authenticateHotelAdmin(Long phoneNumber,String password){
        if(hotelAdminAuthentication.containsKey(phoneNumber)){
            String orgPassword=hotelAdminAuthentication.get(phoneNumber);
            return orgPassword.equals(password);
        }
        return false;
    }

    public boolean authenticateCustomer(Long phoneNumber,String password){
        if(customerAuthentication.containsKey(phoneNumber)){
            String orgPassword=customerAuthentication.get(phoneNumber);
            return orgPassword.equals(password);
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

}
