package beans.controllers;

import beans.models.*;
import beans.services.AuditoriumService;
import beans.services.BookingService;
import beans.services.EventService;
import beans.services.UserService;
import beans.views.PDFTicketsSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.security.AccessControlException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final EventService eventService;
    private final AuditoriumService auditoriumService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public BookingController(@Qualifier("bookingFacade")BookingService bookingService,
                             @Qualifier("userServiceImpl") UserService userService,
                             @Qualifier("eventServiceImpl") EventService eventService,
                             @Qualifier("auditoriumServiceImpl") AuditoriumService auditoriumService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.eventService = eventService;
        this.auditoriumService = auditoriumService;
    }

    @RequestMapping(value = "/ticket/price", method = RequestMethod.GET)
    public String getTicketPrice(@RequestParam("event") String event,
                                 @RequestParam("auditorium") String auditorium,
                                 @RequestParam("eventDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                                 @RequestParam("seats") List<Integer> seats,
                                 @RequestParam("userID") Long userID, @ModelAttribute("model") ModelMap model) {

        User user = userService.getById(userID);
        double price = bookingService.getTicketPrice(event, auditorium, localDateTime, seats, user);

        model.addAttribute("ticketPrice", price);
        model.addAttribute("eventName", event);
        model.addAttribute("eventDate", localDateTime);
        model.addAttribute("auditorium", auditorium);

        return "ticket-price";
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.POST, produces = "application/json")
    public String bookTicket(@RequestParam("userID") Long userID,
                             @RequestParam("event") String eventName,
                             @RequestParam("auditorium") String auditoriumName,
                             @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                             @RequestParam("seats") List<Integer> seats, @ModelAttribute("model") ModelMap model) {
        String email = ((org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).getUsername();

        User user = userService.getById(userID);
        if(!user.getEmail().equals(email)) {
            throw new AccessControlException(String.format("Booking can be performed only by the [%s] account owner", user.getEmail()));
        }

        double ticketPrice = bookingService.getTicketPrice(eventName, auditoriumName, localDateTime, seats, user);
        Event event = eventService.getEvent(eventName, auditoriumService.getByName(auditoriumName), localDateTime);

        Ticket ticket = bookingService.bookTicket(user, new Ticket(event, localDateTime, seats, user, ticketPrice));
        model.addAttribute("ticket", ticket);

        return "ticket";
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public String getTicketsForEvent(@RequestParam("event") String eventName,
                                     @RequestParam("auditorium") String auditorium,
                                     @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                                     @ModelAttribute("model") ModelMap model) {
        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditorium, localDateTime);

        model.addAttribute("tickets", tickets);

        return "ticket-list";
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.GET, headers = "Accept=application/pdf")
    public ModelAndView getTicketsForEventAsPDF(@RequestParam("event") String eventName,
                                                @RequestParam("auditorium") String auditorium,
                                                @RequestParam("eventDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                                                @ModelAttribute("model") ModelMap model) {
        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditorium, localDateTime);

        model.addAttribute("tickets", tickets);

        return new ModelAndView("PDFTicketsSummary", model);
    }


    @RequestMapping(value = "/tickets", method = RequestMethod.GET, params = "userID")
    public String getTicketsByUser(@RequestParam("userID") Long userId, @ModelAttribute("model") ModelMap model) {
        List<Ticket> tickets = userService.getBookedTickets(userService.getById(userId));
        model.addAttribute("tickets", tickets);

        return "ticket-list";
    }


}
