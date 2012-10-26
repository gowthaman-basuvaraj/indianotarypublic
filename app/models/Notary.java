package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public String city;
    public String district;
    public String state;
    public String pincode;

    @PostLoad
    public void postLoad() {
        if (address != null)
            addressLC = address.toLowerCase();
        if (area != null)
            areaLC = area.toLowerCase();
        if (pincode == null) {
            Matcher matcher = Pattern.compile(".*?(\\d{6}).*?").matcher(address);
            if (matcher.matches()) {
                pincode = matcher.group(1);
            }
        }
    }

}
