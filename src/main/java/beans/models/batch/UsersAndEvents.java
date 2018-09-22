package beans.models.batch;

import beans.models.Event;
import beans.models.User;

import java.util.List;

public class UsersAndEvents {

    List<User> users;
    List<Event> events;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
