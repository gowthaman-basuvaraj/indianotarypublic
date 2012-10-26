package controllers.crud;

import controllers.CRUD;
import controllers.Check;
import controllers.Secure;
import play.mvc.With;

/**
 * Created with IntelliJ IDEA.
 * User: gowthaman
 * Date: 10/26/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
@With(Secure.class)
@Check("isSuperAdmin")
public class AppConfigs extends CRUD {
}
