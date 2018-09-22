package beans.services;

import beans.models.Ticket;
import beans.models.User;
import beans.models.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service("bookingFacade")
public class BookingFacade implements BookingService {

    private BookingService bookingService;
    private UserAccountService userAccountService;

    @Autowired
    public BookingFacade(@Qualifier("bookingServiceImpl") BookingService bookingService,
                         @Qualifier("userAccountServiceImpl") UserAccountService userAccountService) {
        this.bookingService = bookingService;
        this.userAccountService = userAccountService;
    }

    @Override
    public double getTicketPrice(String event, String auditorium, LocalDateTime dateTime, List<Integer> seats, User user) {
        return bookingService.getTicketPrice(event, auditorium, dateTime, seats, user);
    }

    @Override
    @Transactional
    public Ticket bookTicket(User user, Ticket ticket) {
        userAccountService.withdraw(user.getId(), ticket.getPrice());
        return bookingService.bookTicket(user, ticket);
    }

    @Override
    public List<Ticket> getTicketsForEvent(String event, String auditorium, LocalDateTime date) {
        return bookingService.getTicketsForEvent(event, auditorium, date);
    }

    @Transactional
    public UserAccount refillAccount(long id, double amount) {
        return userAccountService.refill(id, amount);
    }
}
