package models;

import play.db.jpa.Model;
import play.mvc.Scope;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: gowthaman
 * Date: 10/26/12
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class User extends Model {
    public String name;
    public String userName;
    public String email;
    public String password;
    public boolean tempUser;

    public User() {
        name = "Guest" + new Random().nextInt(10000);
        userName = "Guest" + new Random().nextInt(10000);
        tempUser = true;
    }

    private static ThreadLocal<User> _current = new ThreadLocal<User>();

    public static User current() {

        User _cu = _current.get();
        if (_cu != null) {
            return _cu;
        }

        Scope.Session current = Scope.Session.current();
        User newUser = new User();
        if (current == null) {
            return newUser;
        }
        String loggedUser = current.get("loggedUser");
        if (loggedUser == null) {
            Scope.Session.current().put("loggedUser", newUser.userName);
            return newUser;
        }
        User first = User.find("userName = ?", loggedUser).first();
        _current.set(first);
        return first;
    }

    public static void set(User user) {
        _current.set(user);
    }

    @PrePersist
    public void pre(){
        tempUser = false;
    }
}
