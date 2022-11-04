package driver;

import admin.AdminDB;
import user.User;
import user.UserAuthenticationDB;
import user.UserType;
import utility.InputHelper;
import utility.PrintStatements;
import utility.Validator;

public abstract class AbstractDriver implements Driver{

    private final AdminDB adminDB=AdminDB.getInstance();
    private static final UserAuthenticationDB userAuthenticationDB=UserAuthenticationDB.getInstance();
    public boolean acceptTermsAndConditions(){
        if(adminDB.getTermsAndConditions().isEmpty()){
            return true;
        }
        InputHelper.printFile(adminDB.getTermsAndConditions());
        System.out.println(PrintStatements.ACCEPT_DECLINE_OPTION);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            return true;
        }
        return false;
    }

    protected User getUserDetails(UserType userType){
        System.out.println(PrintStatements.ENTER_FULL_NAME);
        String fullName=InputHelper.getStringInput();
        String email=getEmail(userType);
        if(email==null){
            return null;
        }
        long phoneNumber=getPhoneNumber(userType);
        if(phoneNumber==-1){
            return null;
        }

        String password,confirmPassword;
        while(true){
            System.out.println(PrintStatements.ENTER_PASSWORD);
            password=InputHelper.getStringInput();
            System.out.println(PrintStatements.CONFIRM_PASSWORD);
            confirmPassword=InputHelper.getStringInput();

            if(Validator.confirmPasswordValidatator(password,confirmPassword)){
                break;
            }
            System.out.println(PrintStatements.PASSWORD_NOT_MATCH);
        }

        User user=new User(fullName, userType);
        user.setPhoneNumber(phoneNumber);
        user.setMailID(email);
        if(userType==UserType.CUSTOMER){
            userAuthenticationDB.addCustomerAuth(phoneNumber,email,password);
        }
        else if(userType==UserType.HOTEL_OWNER){
            userAuthenticationDB.addHotelAdminAuth(phoneNumber,email,password);
        }

        return user;
    }

    private String getEmail(UserType userType){
        String email;
        do{
            System.out.println(PrintStatements.ENTER_EMAIL);
            email=InputHelper.getEmailInput();

            if((userType==UserType.CUSTOMER&&userAuthenticationDB.isCustomerEmailExist(email))||(userType==UserType.HOTEL_OWNER&& userAuthenticationDB.isHotelEmailExist(email))){
                System.out.println("E-Mail already exist");
                System.out.println("1."+ PrintStatements.TRY_AGAIN);
                System.out.println("2."+ PrintStatements.GO_BACK);
                System.out.println(PrintStatements.ENTER_INPUT);
                int choice=InputHelper.getInputWithinRange(2,null);
                if(choice==2){
                    return null;
                }
            }
            else{
                break;
            }

        }while(true);
        return email;
    }

    private long getPhoneNumber(UserType userType){
        long phoneNumber;
        do{
            System.out.println(PrintStatements.ENTER_PHONE_NUMBER);
            phoneNumber=InputHelper.getPhoneNumber();
            if((userType==UserType.CUSTOMER&&userAuthenticationDB.isCustomerPhoneNumberExist(phoneNumber))||(userType==UserType.HOTEL_OWNER&& userAuthenticationDB.isHotelPhoneNumberExist(phoneNumber))){
                System.out.println(PrintStatements.PHONE_NUMBER_ALREADY_EXIST);
                System.out.println("1."+ PrintStatements.TRY_AGAIN);
                System.out.println("2."+ PrintStatements.GO_BACK);
                System.out.println(PrintStatements.ENTER_INPUT);
                int choice=InputHelper.getInputWithinRange(2,null);
                if(choice==2){
                    return -1;
                }
            }
            else{
                break;
            }
        }while(true);
        return phoneNumber;
    }
}
