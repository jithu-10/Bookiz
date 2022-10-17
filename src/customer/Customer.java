package customer;

import user.User;

public class Customer extends User {

    private String fullName;
    private long phoneNumber;
    private int customerID;
    private String mailID;
    private String password;

    public Customer(String fullName,long phoneNumber,String mailID,String password){
        this.fullName=fullName;
        this.phoneNumber=phoneNumber;
        this.mailID=mailID;
        this.password=password;
    }

    public void setCustomerID(int customerID){
        this.customerID=customerID;
    }

    public int getCustomerID(){
        return customerID;
    }

    public long getPhoneNumber(){
        return phoneNumber;
    }

    public String getPassword(){
        return password;
    }

}
