package admin;


import user.User;
import user.UserAuthenticationDB;
import utility.QA;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class AdminDB {

    private final LinkedHashSet<Integer> priceUpdatedHotelList=new LinkedHashSet<>();
    private final ArrayList<QA> faq=new ArrayList<>();
    private final ArrayList<QA> newQuestions=new ArrayList<>();
    private ArrayList<String> termsAndConditions=new ArrayList<>();
    private static final AdminDB ADMIN_DB =new AdminDB();
    private final User admin = new User("admin");
    private AdminDB(){
        UserAuthenticationDB.getInstance().addAdminAuth(admin.getUserName(),"pass");
    }

    public static AdminDB getInstance(){
        return ADMIN_DB;
    }
    public User getAdmin(){
        return admin;
    }



    public void addPriceUpdatedHotelList(int id){
        priceUpdatedHotelList.add(id);
    }

    public LinkedHashSet<Integer> getPriceUpdatedHotelList(){
        return priceUpdatedHotelList;
    }

    public void removeHotelFromPriceUpdatedHotelList(int id){
        if(priceUpdatedHotelList.contains(id)){
            priceUpdatedHotelList.remove(id);
        }
    }

    public void addTermsAndConditions(ArrayList<String> termsAndConditions){
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
        this.newQuestions.add(question);
    }

    public void removeNewQuestion(QA question){
        newQuestions.remove(question);
    }

    public void addFaqQuestion(QA question){
        faq.add(question);
    }

}
