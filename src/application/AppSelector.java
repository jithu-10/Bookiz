package application;

public class AppSelector {
    public static void selectApp(){
        Application application=null;
        loop:do{
            System.out.println(PrintStatements.WELCOME_MESSAGE);
            System.out.println(PrintStatements.STARTUP_MENU);
            System.out.println(PrintStatements.ENTER_INPUT_IN_INTEGER);
            int choice = InputHelper.getInputWithinRange(4,null);

            switch (choice){
                case 1:
                    application = new AdminApp();
                    break;
                case 2:
                    application = new HotelAdminApp();
                    break;
                case 3:
                    application = new CustomerApp();
                    break;
                case 4:
                    break loop;
            }

            if(application!=null){
                application.startApp();
            }

        }while(true);


    }
}
