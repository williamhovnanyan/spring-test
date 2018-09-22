package beans.controllers;

import beans.models.Event;
import beans.models.User;
import beans.services.AuditoriumService;
import beans.services.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EventController {

    private final EventService eventService;
    private final AuditoriumService auditoriumService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public EventController(@Qualifier("eventServiceImpl") EventService eventService,
                           @Qualifier("auditoriumServiceImpl") AuditoriumService auditoriumService) {
        this.eventService = eventService;
        this.auditoriumService = auditoriumService;
    }

    @RequestMapping(path = "/events", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ModelAndView createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.create(event);
        Map<String, Event> eventMap = new HashMap<String, Event>() { { put("event", event); } };

        return new ModelAndView(new MappingJackson2JsonView(objectMapper), eventMap);
    }


    @RequestMapping(path = "/events/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@PathVariable Long id) {
        Event event = eventService.getEvent(id);
        eventService.remove(event);
    }

    @RequestMapping(path = "/events/auditorium", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void assignAuditorium(@RequestBody Map<String, Object> body) {
        Long eventID = ((Integer) body.get("eventID")).longValue();
        String auditoriumName = (String) body.get("auditoriumName");

        Event event = eventService.getEvent(eventID);
        eventService.assignAuditorium(event,
            auditoriumService.getByName(auditoriumName), event.getDateTime());
    }

    @RequestMapping(path = "/events/{id}", method = RequestMethod.GET)
    public String getEvent(@PathVariable Long id, @ModelAttribute("model") ModelMap model) {
        Event event = eventService.getEvent(id);
        model.addAttribute("events", Collections.singletonList(event));

        return "events";
    }

    @RequestMapping(path = "/events", method = RequestMethod.GET, params = "name")
    public String getEventsByName(@RequestParam("name") String eventName, @ModelAttribute("model") ModelMap model)  {
        List<Event> eventList = eventService.getByName(eventName);
        model.addAttribute("events", eventList);

        return "events";
    }

    @RequestMapping(path = "/events", method = RequestMethod.GET)
    public String getAllEvents(@ModelAttribute("model") ModelMap model) {
        model.addAttribute("events", eventService.getAll());

        return "events";
    }

}
