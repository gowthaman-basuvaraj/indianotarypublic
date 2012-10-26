package controllers;

import jobs.FetchJob;
import org.apache.commons.lang.StringUtils;
import play.*;
import play.db.jpa.JPA;
import play.db.jpa.JPABase;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    @Before
    public static void before() {
        String[] states = new String[]{
                "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Goa",
                "Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jammu and Kashmir",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Odisha",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Tripura",
                "Uttarakhand",
                "Uttar Pradesh",
                "West Bengal",

                "Andaman and Nicobar Islands",
                "Chandigarh",
                "The Government of NCT of Delhi",
                "Dadra and Nagar Haveli",
                "Daman and Diu",
                "Lakshadweep",
                "Puducherry"

        };

        User.current();
        renderArgs.put("states", states);
    }

    public static void index(String district, String state) {

        //its  a small database, & its in memory, lets fetchit and then do some stuff
        List<Notary> all = Notary.findAll();

        List<Notary> search = new ArrayList<Notary>();

        if (StringUtils.isNotBlank(state)) {
            String s = state.toLowerCase();
            for (Notary n : all) {
                if (contains(s, n)) {
                    search.add(n);
                }
            }
        }
        List<Notary> search2 = new ArrayList<Notary>();
        if (StringUtils.isNotBlank(district)) {
            String s = district.toLowerCase();
            List<Notary> iter = search.size() == 0 ? all : search;
            for (Notary n : iter) {
                if (contains(s, n)) {
                    search2.add(n);
                }
            }
        }
        String mesg = null;
        if (search2.size() != 0) {
            search = search2;
        }

        if (search.size() == 0 && (StringUtils.isBlank(district) && StringUtils.isBlank(state))) {
            int size = all.size();
            int fromIndex = new Random().nextInt(size / 10);
            search = all.subList(fromIndex, fromIndex + 10);
        } else {
            mesg = "No Results Found for " + district + "/" + state;
        }

        render(district, state, search, mesg);
    }

    private static boolean contains(String s, Notary n) {
        String s1 = n.areaLC;
        String s2 = n.addressLC;
        return s1.contains(s) || s2.contains(s);
    }

    public static void view(long notaryId) {
        JPABase notary = Notary.findById(notaryId);
        render(notary);
    }

    public static void fetch() throws Exception {
        if (!request.remoteAddress.equals("127.0.0.1")) {
            //triggerjob only from Local Address
            renderText("Oh Yeah!");
        }
        if (Notary.count() != 0) {
            renderText("Have Some Data");
        }
        new FetchJob().doJob();
        renderText("OK");
    }

    public static void editAddress(NotaryEdit notary){
        User current = User.current();
        if(current!=null && !current.tempUser){
            notary.editor = current;
        }
        notary.save();
        view(notary.notaryId);
    }
}