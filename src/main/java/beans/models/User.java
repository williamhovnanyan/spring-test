package beans.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import util.serialization.DateDeserializers;
import util.serialization.DateSerializers;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 2/1/2016
 * Time: 7:35 PM
 */
@Entity
@Table(name = "users")
public class User {

    private long      id;
    private String    email;
    private String    name;

    private String    password;
    private String    roles;

    @JsonSerialize(using = DateSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = DateDeserializers.LocalDateDeserializer.class)
    private LocalDate birthday;

    public User() {
    }

    public User(long id, String email, String name, LocalDate birthday, String passwordHash, String roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthday = birthday;
        this.password = passwordHash;
        this.roles = roles;
    }

    public User(long id, String email, String name, LocalDate birthday, String passwordHash) {
        this(id, email, name, birthday, passwordHash, "REGISTERED_USER");
    }

    public User(String email, String name, LocalDate birthday, String passwordHash) {
        this(-1, email, name, birthday, passwordHash);
    }

    public User withId(long id) {
        return new User(id, email, name, birthday, password, roles);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordHash) {
        this.password = passwordHash;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (id != user.id)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null)
            return false;
        if (name != null ? !name.equals(user.name) : user.name != null)
            return false;
        return birthday != null ? birthday.equals(user.birthday) : user.birthday == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", name='" + name + '\'' +
               ", birthday=" + birthday +
               '}';
    }
}
