package driver;

import admin.AdminDB;
import utility.InputHelper;
import utility.PrintStatements;


public abstract class AbstractDriver implements Driver{

    private final AdminDB adminDB=AdminDB.getInstance();
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

}
