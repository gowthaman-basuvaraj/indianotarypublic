package models;

import play.db.jpa.Model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: gowthaman
 * Date: 10/26/12
 * Time: 11:04 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class NotaryEdit extends Model {

    public long notaryId;

    public String city;
    public String district;
    public String state;
    public String pincode;
    private String address;


    @ManyToOne
    public User editor;

    public boolean authorized;

    @Transient
    public Notary notary;

    public void upstream(Notary notary) {

        notary.city = city;
        notary.district = district;
        notary.state = state;
        notary.pincode = pincode;
        notary.address = address;
    }

    @PostLoad
    public void postload(){
        notary = Notary.findById(notaryId);
    }
}
