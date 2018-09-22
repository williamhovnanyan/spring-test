package beans.daos.db;

import beans.daos.AbstractDAO;
import beans.daos.UserAccountDAO;
import beans.daos.UserDAO;
import beans.models.Event;
import beans.models.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository(value = "userAccountDAO")
public class UserAccountDAOImpl extends AbstractDAO implements UserAccountDAO {
    @Override
    public UserAccount create(UserAccount userAccount) {
        UserAccountDAO.validateUserAccount(userAccount);
        UserAccount userAccount1 = get(userAccount.getUserID());

        if(Objects.nonNull(userAccount1)) {
            throw new IllegalStateException(
                    String.format("Unable to store user account: [%s]. User account with id: [%s] is already created.", userAccount,
                            userAccount.getUserID()));
        } else {
            getCurrentSession().save(userAccount);
            return userAccount;
        }
    }

    @Override
    public void delete(UserAccount userAccount) {
        UserAccountDAO.validateUserAccount(userAccount);
        getCurrentSession().delete(userAccount);
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        UserAccountDAO.validateUserAccount(userAccount);
        return ((UserAccount) getCurrentSession().merge(userAccount));
    }

    @Override
    public UserAccount get(long id) {
        return getCurrentSession().get(UserAccount.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getAll() {
        return (List<UserAccount>) createBlankCriteria(UserAccount.class).list();
    }
}
