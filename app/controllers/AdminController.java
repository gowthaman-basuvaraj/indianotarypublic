package controllers;

import models.Notary;
import models.NotaryEdit;
import play.db.jpa.JPABase;
import play.mvc.Controller;
import play.mvc.With;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gowthaman
 * Date: 10/26/12
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
@With({Secure.class, Application.class})
@Check("isSuperAdmin")
public class AdminController extends Controller {

    public static void authorizeChanges() {
        List<NotaryEdit> edits = NotaryEdit.find("authorized = false").fetch(10);
        render(edits);
    }

    public static void discard(NotaryEdit notary) {
        notary.delete();
        authorizeChanges();
    }

    public static void authorize(NotaryEdit notary) {
        notary.save();
        Notary notary1 = Notary.findById(notary.notaryId);
        notary.upstream(notary1);
        notary1.save();
        authorizeChanges();
    }
}
