package beans.daos;

import beans.models.UserAccount;

import java.util.List;
import java.util.Objects;

public interface UserAccountDAO {

    UserAccount create(UserAccount userAccount);

    void delete(UserAccount userAccount);

    UserAccount update(UserAccount userAccount);

    UserAccount get(long id);

    List getAll();

    static void validateUserAccount(UserAccount userAccount) {
        if (Objects.isNull(userAccount)) {
            throw new NullPointerException("User Account is [null]");
        }
        if (userAccount.getUserID() == 0) {
            throw new NullPointerException("User ID is not set");
        }
    }
}
