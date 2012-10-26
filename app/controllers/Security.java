package controllers;


import models.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.Play;

import java.util.Date;

public class Security extends Secure.Security {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Security.class);

    private static String remoteIP;

    static boolean authenticate(String username, String password) {
        Logger.warn("Auth " + username + " pass " + password);
        User user = User.find("userName = ? ", username).first();
        if (null == user) {
            LOG.warn("unable to find CP by UserName " + username);
            return false;
        }
        return Play.mode.isDev() || StringUtils.equalsIgnoreCase(DigestUtils.md5Hex(password), user.password);

    }

    static void onAuthenticated() {
        String userName = Security.connected();
        String ip = getIP();
        User user = User.find("userName = ?", userName).first();
        session.put("loggedUser", user.id);
        session.put("loggedFrom", ip);


        Logger.warn(String.format("%s, logged from %s", userName, ip));
    }

    static String getIP() {
        play.mvc.Http.Header h = request.headers.get("xrealip");
        if (h != null) {
            remoteIP = h.value();
        } else {
            remoteIP = request.remoteAddress;
        }
        return remoteIP;
    }

    static boolean check(String profile) {
        if (Play.mode.isDev()) return true;
        if (StringUtils.equalsIgnoreCase(profile, "isSuperAdmin")) {
            String userName = Security.connected();
            User byUserName = User.find("userName = ? ", userName).first();
            return byUserName.userName.equalsIgnoreCase("admin");
        }

        return false;
    }

    static void onDisconnected() {

    }


}