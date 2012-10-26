package models;

import play.db.jpa.JPABase;
import play.db.jpa.Model;
import play.mvc.Scope;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gowthaman
 * Date: 10/26/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class AppConfig extends Model {
    public String siteVerificationCode;
    public String bingSiteVeriCode;
    public String gaId;

    private static ThreadLocal<AppConfig> _current = new ThreadLocal<AppConfig>();

    public static AppConfig current() {

        AppConfig _cu = _current.get();
        if (_cu != null) {
            return _cu;
        }


        List<AppConfig> all = AppConfig.findAll();

        _current.set(all.get(0));
        return all.get(0);
    }


}
