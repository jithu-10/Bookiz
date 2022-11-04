package user;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDB {
    private static int idHelper=0;
    private static UserDB userDB=new UserDB();
    private ArrayList<User> userList=new ArrayList<>();

    private UserDB(){

    }

    public static UserDB getInstance(){
        return userDB;
    }
    private static int generateId(){
        return ++idHelper;
    }
    public void addUser(User user){
        user.setUserID(generateId());
        userList.add(user);
    }

    public ArrayList<Integer> getUserList(UserType userType){
        ArrayList<Integer> users=new ArrayList<>();
        for(User user: userList){
            if(user.getUserType()==userType){
                users.add(user.getUserID());
            }
        }
        return users;
    }

    public User getUserByPhoneNumber(long phoneNumber,UserType userType){
        for(User user: userList){
            if(user.getUserType()==userType){
                if(user.getPhoneNumber()==phoneNumber){
                    return user;
                }
            }
        }
        return null;
    }

    public User getUserByPhoneNumber_Mail(Object phone_mail,UserType userType){
        for(User user:userList){
            if(phone_mail instanceof Long){
                if(user.getUserType()==userType){
                    if(phone_mail.equals(user.getPhoneNumber())){
                        return user;
                    }
                }
            }
            else if(phone_mail instanceof String){
                if(user.getUserType()==userType){
                    if(phone_mail.equals(user.getMailID())){
                        return user;
                    }
                }
            }
        }
        return null;
    }

    public User getUserByID(int userID,UserType userType){
        for(User user: userList){
            if(user.getUserType()==userType){
                if(user.getUserID()==userID){
                    return user;
                }
            }
        }
        return null;
    }
}
