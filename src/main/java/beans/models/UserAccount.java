package beans.models;

public class UserAccount {
    private double userBalance;
    private long   userID;

    public UserAccount(long id, double balance) {
        this.userBalance = balance;
        this.userID = id;
    }

    public double getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
