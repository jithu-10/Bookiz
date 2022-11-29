package application;

import hotelbooking.AdminDB;


public abstract class AbstractApp implements Application {

    public boolean acceptTermsAndConditions(){
        if(AdminDB.getInstance().getTermsAndConditions().isEmpty()){
            return true;
        }
        InputHelper.printFile(AdminDB.getInstance().getTermsAndConditions());
        System.out.println(PrintStatements.ACCEPT_DECLINE_OPTION);
        int choice=InputHelper.getInputWithinRange(2,null);
        if(choice==1){
            return true;
        }
        return false;
    }

}
