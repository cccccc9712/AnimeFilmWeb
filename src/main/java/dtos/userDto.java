package dtos;

import model.User;

public class userDto extends User {
    private String remember_Token;
    private boolean isPremium;

    public userDto() {
    }

    public userDto(String userName, String userPass, String userGmail, Boolean isAdmin, String remember_Token) {
        super(userName, userPass, userGmail, isAdmin);
        this.remember_Token = remember_Token;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getRemember_Token() {
        return remember_Token;
    }

    public void setRemember_Token(String remember_Token) {
        this.remember_Token = remember_Token;
    }

    @Override
    public String toString() {
        return super.toString() +
                "remember_Token='" + remember_Token + '\'' +
                ", isPremium=" + isPremium +
                '}';
    }
}