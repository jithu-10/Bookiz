package customer;

import hotel.Hotel;

import java.util.ArrayList;

public class CustomerDB {
    private static int idHelper=0;
    private static ArrayList<Customer> customerList=new ArrayList<>();

    private static int generateId(){
        return ++idHelper;
    }

    public static void addCustomer(Customer customer){
        customerList.add(customer);
        customer.setCustomerID(generateId());
    }

    public static Customer checkAuthentication(long phoneNumber, String password){

        for(Customer customer:customerList){
            if(customer.getPhoneNumber()==phoneNumber&&customer.getPassword().equals(password)){
                return customer;
            }
        }
        return null;
    }

    public static Customer getCustomerByID(int ID){
        for(int i=0;i<customerList.size();i++){
            if(customerList.get(i).getCustomerID()==ID){
                return customerList.get(i);
            }
        }
        return null;
    }

    public static boolean isPhoneNumberExist(long phoneNumber){
        ArrayList<Customer> customers=customerList;
        for(int i=0;i<customers.size();i++){
            if(customers.get(i).getPhoneNumber()==phoneNumber){
                return true;
            }
        }
        return false;
    }

}
