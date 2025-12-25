package model;

public class UserData {
    private User user;
    private Wallet wallet;
    private String hashPassword;

    public UserData() {
    }

    public void createData(User user, Wallet wallet, String hashPassword) {
        this.user = user;
        this.wallet = wallet;
        this.hashPassword = hashPassword;
    }

    public User getUser() {
        return user;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getHashPassword() {
        return hashPassword;
    }
}
