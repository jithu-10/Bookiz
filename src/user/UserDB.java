package user;

import customer.Customer;

import java.util.ArrayList;

public class UserDB  {
    private static int idHelper=0;
    private static UserDB userDB=new UserDB();
    private ArrayList<User> hotelAdminList =new ArrayList<>();
    private ArrayList<User> customerList=new ArrayList<>();

    // Admin
    // Customers
    // Hotel

    private UserDB(){

    }

    public static UserDB getInstance(){
        return userDB;
    }
    private static int generateId(){
        return ++idHelper;
    }


    public void addHotelAdmin(User user){
        user.setUserID(generateId());
        hotelAdminList.add(user);
    }

    public void addCustomer(User user){
        user.setUserID(generateId());
        customerList.add(user);
    }


    public User getHotelAdminByPhoneNumber_Mail(Object phone_mail){
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

    public User getCustomerByPhoneNumber_Mail(Object phone_mail){
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

    public User getHotelAdminByID(int userID){
        for(User user: hotelAdminList){
            if(user.getUserID()==userID){
                return user;
            }
        }
        return null;
    }

    public User getCustomerByID(int userID){
        for(User customer:customerList){
            if(customer.getUserID()==userID){
                return customer;
            }
        }
        return null;
    }
}
