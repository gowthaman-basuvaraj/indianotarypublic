package controllers;

import jobs.FetchJob;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index(String district, String state) {
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
                "Puducherry",

        };
        render(district, state);
    }

    public static void fetch() throws Exception {
        new FetchJob().doJob();
        renderText("OK");
    }
}