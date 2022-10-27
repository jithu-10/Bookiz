package user;

public class User {
    private String userName;
    private long phoneNumber;
    private int userID;
    private String mailID;

    protected String getUserName() {
        return userName;
    }

    protected void setUserName(String userName) {
        this.userName = userName;
    }

    protected long getPhoneNumber() {
        return phoneNumber;
    }

    protected void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    protected int getUserID() {
        return userID;
    }

    protected void setUserID(int userID) {
        this.userID = userID;
    }

    protected String getMailID() {
        return mailID;
    }

    protected void setMailID(String mailID) {
        this.mailID = mailID;
    }
}
