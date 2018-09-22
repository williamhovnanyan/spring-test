package beans.services;

import beans.daos.BookingDAO;
import beans.daos.UserAccountDAO;
import beans.daos.UserDAO;
import beans.models.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userAccountServiceImpl")
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountDAO userAccountDAO;

    @Autowired
    public UserAccountServiceImpl(@Qualifier("userAccountDAO") UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public UserAccount refill(long id, double amount) {
        UserAccount userAccount = userAccountDAO.get(id);
        return userAccountDAO.update(new UserAccount(id, userAccount.getUserBalance() + amount));
    }

    @Override
    public UserAccount withdraw(long id, double amount) {
        UserAccount userAccount = userAccountDAO.get(id);
        if(userAccount.getUserBalance() < amount) {
            throw new IllegalArgumentException(String.format("The amount [%s] can not be withdrawn from the account [%s]",
                    amount, userAccount.getUserID()));
        }

        return userAccountDAO.update(new UserAccount(id, userAccount.getUserBalance() - amount));
    }

    @Override
    public double getBalance(long id) {
        UserAccount userAccount = userAccountDAO.get(id);
        return userAccount != null ? userAccount.getUserBalance() : -1;
    }
}
