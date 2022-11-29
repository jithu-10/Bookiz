package hotelbooking;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;

public class AdminDB {

    private final LinkedHashSet<Integer> priceUpdatedHotelList=new LinkedHashSet<>();
    private final ArrayList<QA> faq=new ArrayList<>();
    private final ArrayList<QA> newQuestions=new ArrayList<>();
    private ArrayList<String> termsAndConditions=new ArrayList<>();
    private final User admin = new User("admin",1234567890,"admin@gmail.com");
    private static final AdminDB ADMIN_DB =new AdminDB();
    private AdminDB(){
        UserAuthenticationDB.getInstance().addAdminAuth(admin.getUserName(),"pass");
    }

    public static AdminDB getInstance(){
        return ADMIN_DB;
    }


    public User getAdmin(String userName,String password){
        Helper.validateString(userName,"User name Can't be null or empty");
        Helper.validateString(password,"Password name Can't be null or empty");
        if(!UserAuthenticationDB.getInstance().authenticateAdmin(userName,password)){
            return null;
        }
        return admin;
    }



    public void addPriceUpdatedHotelList(int id){
        priceUpdatedHotelList.add(id);
    }

    public LinkedHashSet<Integer> getPriceUpdatedHotelList(){
        return priceUpdatedHotelList;
    }

    public void removeHotelFromPriceUpdatedHotelList(int id){
        priceUpdatedHotelList.remove(id);

    }

    public void addTermsAndConditions(ArrayList<String> termsAndConditions){
        Helper.validateNull(termsAndConditions,"Terms and Conditions can't be null");
        this.termsAndConditions=termsAndConditions;
    }

    public ArrayList<String> getTermsAndConditions(){
        return this.termsAndConditions;
    }

    public ArrayList<QA> getFaq(){
        return faq;
    }

    public ArrayList<QA> getNewQuestions(){
        return newQuestions;
    }

    public void addNewQuestion(QA question){
        Helper.validateNull(question,"Question can't be null");
        this.newQuestions.add(question);
    }

    public void removeNewQuestion(QA question){
        Helper.validateNull(question,"Question can't be null");
        newQuestions.remove(question);
    }

    public void addFaqQuestion(QA question){
        Helper.validateNull(question,"Question can't be null");
        faq.add(question);
    }

}
