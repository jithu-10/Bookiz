package admin;


import hotel.Hotel;
import user.User;
import user.UserAuthenticationDB;
import user.UserType;
import utility.QA;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class AdminDB {

    private LinkedHashSet<Integer> priceUpdatedHotelList=new LinkedHashSet<>();
    private ArrayList<String> termsAndConditions=new ArrayList<>();
    private ArrayList<QA> faq=new ArrayList<QA>();
    private ArrayList<QA> newQuestions=new ArrayList<QA>();
    private static AdminDB adminDB=new AdminDB();
    private User admin = new User("admin",UserType.ADMIN);
    private AdminDB(){
        UserAuthenticationDB.getInstance().addAdminAuth(admin.getUserName(),"pass");
    }

    public static AdminDB getInstance(){
        return adminDB;
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

    public void removeHotelfromPriceUpdatedHotelList(int id){
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
