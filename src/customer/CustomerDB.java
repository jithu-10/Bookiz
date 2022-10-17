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

}
