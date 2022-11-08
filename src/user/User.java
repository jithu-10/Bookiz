package user;

public class User {
    private String userName;
    private long phoneNumber;
    private int userID;
    private String mailID;

    public User(String userName){
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
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

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }
    public String getMailID(){
        return  mailID;
    }
}
