package beans.services;

import beans.models.UserAccount;

public interface UserAccountService {
    UserAccount refill(long id, double amount);
    UserAccount withdraw(long id, double amount);
    double getBalance(long id);
}
