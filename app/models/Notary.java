package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
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

    public String phno;
    public String feeDetails;

    public double lat;
    public double lng;

    @Transient
    public String addressLC;
    @Transient
    public String areaLC;

    @PostLoad
    public void postLoad(){
        addressLC = address.toLowerCase();
        areaLC = area.toLowerCase();
    }

}
