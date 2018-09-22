package beans.controllers;

import beans.models.User;
import beans.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public UserController(@Qualifier("userServiceImpl") UserService userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST,  consumes = { "application/json" }, produces = "application/json")
    public ModelAndView registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User registeredUser = userService.register(user);
        Map<String, User> userMap = new HashMap<>();
        userMap.put("user", registeredUser);

        return new ModelAndView(new MappingJackson2JsonView(objectMapper), userMap);
    }

//    @RequestMapping(path = "/sign-in", method = RequestMethod.POST)
//    public ModelAndView loginUser(@RequestParam(value = "email") String email,
//                                  @RequestParam(value = "password") String password,
//                                  @ModelAttribute("model") ModelMap model) {
//
//        User foundUser = userService.getUserByEmail(email);
//        model.put("users", Collections.singletonList(foundUser));
//
//        return new ModelAndView("users", model);
//    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@PathVariable Long id) {
        User user = userService.getById(id);
        userService.remove(user);
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable Long id, @ModelAttribute("model") ModelMap model) {
        model.addAttribute("users", Collections.singletonList(userService.getById(id)));
        return "users";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String getUserByEmail(@RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "name", required = false) String name,
                                 @ModelAttribute("model") ModelMap model) {
        if(email != null) {
            model.addAttribute("users", Collections.singletonList(userService.getUserByEmail(email)));
        } else if(name != null) {
            model.addAttribute("users", userService.getUsersByName(name));
        } else {
            model.addAttribute("users", Collections.emptyList());
        }

        return "users";
    }

    @RequestMapping(path = "/users/current", method = RequestMethod.GET)
    public String getCurrentUser(@ModelAttribute("model")ModelMap model) {
        org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        model.addAttribute("users", Collections.singletonList(userService.getUserByEmail(authUser.getUsername())));

        return "users";
    }

}
