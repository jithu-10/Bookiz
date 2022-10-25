import driver.MainDriver;
import testing.Init;
import utility.InputHelper;

public class Main {
    public static void main(String[] args) {

        Init.init();
        MainDriver.selectUser();
        Init.endingInit();

    }
}
