package driver;

import customer.Customer;
import customer.CustomerDB;
import hotel.Hotel;
import hotel.HotelDB;
import user.User;
import utility.InputHelper;
import utility.Printer;
import utility.Validator;

import java.util.HashMap;

public class CustomerDriver implements Driver {

    static final CustomerDriver customerDriver=new CustomerDriver();


    private CustomerDriver(){

    }

    static CustomerDriver getInstance(){
        return customerDriver;
    }

    @Override
    public void startDriver() {
        System.out.println(" Customer Driver ");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("Enter Input : ");
        int choice = InputHelper.getInputWithinRange(2,null);
        switch (choice){
            case 1:
                System.out.println("Signed In .....");
                Customer customer;
                if((customer=(Customer) signIn())!=null){
                    menu(customer);
                }
                else{
                    System.out.println(Printer.INVALID_CREDENTIALS);
                }
                break;
            case 2:
                register();
                break;

        }

    }

    @Override
    public User signIn() {
        System.out.println(Printer.SIGN_IN);
        System.out.println(Printer.ENTER_PHONE_NUMBER);
        long phoneNumber= InputHelper.getPhoneNumber();
        System.out.println(Printer.ENTER_PASSWORD);
        String passWord= InputHelper.getStringInput();
        User user= CustomerDB.checkAuthentication(phoneNumber,passWord);
        return user;
    }

    @Override
    public void menu(User user) {
        Customer customer=(Customer) user;
        do{
            System.out.println("1.Book Hotel");
            System.out.println("2.List Bookings");
            System.out.println("3.Cancel Booking");
            System.out.println("4.Favorite List");
            System.out.println("5.Log Out");
            System.out.println(Printer.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(5,null);
            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Signing Out...");
                    return;
                default:
                    //NO NEED
                    System.out.println("Enter Input only from given options");
            }

        }while(true);
    }

    //------------------------------------------------Customer Registration--------------------------------------------//
    public void register(){
        Customer customer=customerDetails();
        CustomerDB.addCustomer(customer);
        System.out.println("Sign Up Completed . You can Sign in Now");
    }

    public Customer customerDetails(){
        System.out.println("Enter Phone Number : ");
        long phoneNumber=InputHelper.getPhoneNumber();
        String password,confirmPassword;
        while(true){
            System.out.println("Enter Password : ");
            password=InputHelper.getStringInput();
            System.out.println("Confirm Password : ");
            confirmPassword=InputHelper.getStringInput();

            if(Validator.confirmPasswordValidatator(password,confirmPassword)){
                break;
            }
            System.out.println("Password Not Matching Try Again ");
        }
        System.out.println("Full Name : ");
        String fullName=InputHelper.getStringInput();
        System.out.println("E-Mail ID : ");
        /*TODO Validate Mail ID*/
        String mailID=InputHelper.getStringInput();
        return new Customer(fullName,phoneNumber,mailID,password);
    }

    //------------------------------------------------1.Book Hotel----------------------------------------------------//


}
