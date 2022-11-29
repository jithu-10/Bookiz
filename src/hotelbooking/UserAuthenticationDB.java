package hotelbooking;

import java.util.HashMap;

class UserAuthenticationDB {

    private final HashMap<String,String> adminAuthentication=new HashMap<>();
    private final HashMap<Long,String> hotelAdminAuthentication =new HashMap<>();
    private final HashMap<String,String> hotelAdminAuthenticationByEmailID=new HashMap<>();
    private final HashMap<Long,String> customerAuthentication=new HashMap<>();
    private final HashMap<String,String> customerAuthenticationByEmailID=new HashMap<>();
    private static final UserAuthenticationDB USER_AUTHENTICATION_DB =new UserAuthenticationDB();

    private UserAuthenticationDB(){

    }

    static UserAuthenticationDB getInstance(){
        return USER_AUTHENTICATION_DB;
    }

    void addAdminAuth(String userName,String password){
        adminAuthentication.put(userName,password);
    }
    void addHotelAdminAuth(Long phoneNumber,String email,String password){
        hotelAdminAuthentication.put(phoneNumber,password);
        hotelAdminAuthenticationByEmailID.put(email,password);
    }
    void addCustomerAuth(Long phoneNumber,String email,String password){
        customerAuthentication.put(phoneNumber,password);
        customerAuthenticationByEmailID.put(email,password);
    }
    boolean authenticateAdmin(String userName,String password){
        if(adminAuthentication.containsKey(userName)){
            String orgPassword=adminAuthentication.get(userName);
            return orgPassword.equals(password);
        }
        return false;
    }

    boolean authenticateHotelAdmin(Object mailOrPhone,String password){

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


    boolean authenticateCustomer(Object mailOrPhone,String password){
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


    boolean isHotelPhoneNumberExist(Long phoneNumber){
        return hotelAdminAuthentication.containsKey(phoneNumber);
    }

    boolean isCustomerPhoneNumberExist(Long phoneNumber){
        return customerAuthentication.containsKey(phoneNumber);
    }

    boolean isHotelEmailExist(String email){
        return hotelAdminAuthenticationByEmailID.containsKey(email);
    }

    boolean isCustomerEmailExist(String email){
        return customerAuthenticationByEmailID.containsKey(email);
    }



}
