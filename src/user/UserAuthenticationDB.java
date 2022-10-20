package user;

import java.util.HashMap;

public class UserAuthenticationDB {

    private HashMap<String,String> adminAuthentication=new HashMap<>();
    private HashMap<Long,String> hotelAuthentication=new HashMap<>();
    private HashMap<Long,String> customerAuthentication=new HashMap<>();
    private static UserAuthenticationDB userAuthenticationDB=new UserAuthenticationDB();

    private UserAuthenticationDB(){

    }

    public static UserAuthenticationDB getInstance(){
        return userAuthenticationDB;
    }

    public void addAdminAuth(){

    }

    public void addHotelAuth(Long phoneNumber,String password){
        hotelAuthentication.put(phoneNumber,password);
    }

    public void addCustomerAuth(Long phoneNumber,String password){
        customerAuthentication.put(phoneNumber,password);
    }

    public boolean authenticateHotel(Long phoneNumber,String password){
        if(hotelAuthentication.containsKey(phoneNumber)){
            String orgPassword=hotelAuthentication.get(phoneNumber);
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
        if(hotelAuthentication.containsKey(phoneNumber)){
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
