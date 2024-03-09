package entity;

public class User {
    private int userId;
    private String userName;
    private String userPass;
    private String userGmail;
    private Boolean isAdmin;

    public User(String userName, String userPass, String userGmail, Boolean isAdmin) {
        this.userName = userName;
        this.userPass = userPass;
        this.userGmail = userGmail;
        this.isAdmin = isAdmin;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserGmail() {
        return userGmail;
    }

    public void setUserGmail(String userGmail) {
        this.userGmail = userGmail;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userGmail='" + userGmail + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
