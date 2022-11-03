package user;

public class User {
    private UserType userType;
    private String userName;
    private long phoneNumber;
    private int userID;
    private String mailID;

    public void setUserType(UserType userType){
        this.userType=userType;
    }
    public UserType getUserType(){
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }
}
