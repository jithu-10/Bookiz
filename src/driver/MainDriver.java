package driver;

import utility.InputHelper;
import utility.PrintStatements;

public class MainDriver {
    public static void selectUser(){
        Driver driver=null;
        loop:do{
            System.out.println(PrintStatements.WELCOME_MESSAGE);
            System.out.println(PrintStatements.STARTUP_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(4,null);

            switch (choice){
                case 1:
                    driver = new AdminDriver();
                    break;
                case 2:
                    driver = new HotelDriver();
                    break;
                case 3:
                    driver = new CustomerDriver();
                    break;
                case 4:
                    break loop;
            }

            if(driver!=null){
                driver.startDriver();
            }

        }while(true);


    }
}
