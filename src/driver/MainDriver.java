package driver;

import utility.InputHelper;
import utility.PrintStatements;

public class MainDriver {
    public static void selectUser(){
        Driver d=null;
        loop:do{
            System.out.println(PrintStatements.WELCOME_MESSAGE);
            System.out.println(PrintStatements.STARTUP_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(4,null);

            switch (choice){
                case 1:
                    d= AdminDriver.getInstance();
                    break;
                case 2:
                    d=HotelDriver.getInstance();
                    break;
                case 3:
                    d=CustomerDriver.getInstance();
                    break;
                case 4:
                    break loop;
            }

            if(d!=null){
                d.startDriver();
            }

        }while(true);


    }
}
