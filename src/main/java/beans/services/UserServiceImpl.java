package beans.services;

import beans.daos.BookingDAO;
import beans.daos.UserDAO;
import beans.models.Ticket;
import beans.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 2/1/2016
 * Time: 7:30 PM
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService{

    private final UserDAO userDAO;
    private final BookingDAO bookingDAO;

    @Autowired
    public UserServiceImpl(@Qualifier("userDAO") UserDAO userDAO, @Qualifier("bookingDAO") BookingDAO bookingDAO) {
        this.userDAO = userDAO;
        this.bookingDAO = bookingDAO;
    }

    public User register(User user) {
        return userDAO.create(user);
    }

    public void remove(User user) {
        userDAO.delete(user);
    }

    public User getById(long id) {
        return userDAO.get(id);
    }

    public User getUserByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    public List<User> getUsersByName(String name) {
        return userDAO.getAllByName(name);
    }

    @Override
    public List<Ticket> getBookedTickets(User user) {
        return bookingDAO.getTickets(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByEmail(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    private List<GrantedAuthority> buildUserAuthority(String roles) {
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
