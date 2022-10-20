package customer;

import hotel.Hotel;

import java.util.ArrayList;

public class CustomerDB {
    private static int idHelper=0;
    private static CustomerDB customerDB = new CustomerDB();
    private static ArrayList<Customer> customerList=new ArrayList<>();

    private CustomerDB(){

    }
    public static CustomerDB getInstance(){
        return customerDB;
    }

    private static int generateId(){
        return ++idHelper;
    }

    public void addCustomer(Customer customer){
        customerList.add(customer);
        customer.setCustomerID(generateId());
    }

    public Customer getCustomerByID(int ID){
        for(int i=0;i<customerList.size();i++){
            if(customerList.get(i).getCustomerID()==ID){
                return customerList.get(i);
            }
        }
        return null;
    }

    public Customer getCustomerByPhoneNumber(long phoneNumber){
        for(Customer customer: customerList){
            if(customer.getPhoneNumber()==phoneNumber){
                return customer;
            }
        }

        return null;

    }

}
