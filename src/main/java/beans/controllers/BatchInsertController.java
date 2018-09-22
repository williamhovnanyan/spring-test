package beans.controllers;

import beans.models.Event;
import beans.models.User;
import beans.models.batch.UsersAndEvents;
import beans.services.EventService;
import beans.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BatchInsertController {

    private final UserService userService;
    private final EventService eventService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public BatchInsertController(@Qualifier("userServiceImpl")UserService userService,
                                 @Qualifier("eventServiceImpl")EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @RequestMapping(path = "/insert/batch", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void batchInsert(@RequestParam("file") MultipartFile usersAndEventsFile) throws IOException {
        UsersAndEvents usersAndEvents = objectMapper.readValue(usersAndEventsFile.getInputStream(), UsersAndEvents.class);
        usersAndEvents
                .getUsers().stream()
                .peek(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
                .forEach(userService::register);
        usersAndEvents
                .getEvents().forEach(eventService::create);
    }
}
