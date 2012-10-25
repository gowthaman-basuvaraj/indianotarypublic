package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: gowthaman
 * Date: 10/24/12
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Notary extends Model {
    public String name;
    public Date validTill;
    public Date dateOfRenewal;
    public String address;
    public String slno;
    public String area;

    public double lat;
    public double lng;

}
