package jobs;

import models.Notary;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.WS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: gowthaman
 * Date: 10/24/12
 * Time: 9:46 PM
 * To change this template use File | Settings | File Templates.
 */
//@OnApplicationStart
public class FetchJob extends Job {
    @Override
    public void doJob() throws Exception {
        if(Play.mode.isDev()){
            String s = FileUtils.readFileToString(new File("/var/tmp/1.html"));
            List<Notary> parse = parse("", s);
            Logger.warn("Found " + parse.size() + " Notaries");
            for (Notary notary : parse) {
                notary.save();
            }
            return;
        }
        //http://lawmin.nic.in/la/notarylist3.htm
        //http://lawmin.nic.in/la/notarylist1.htm
        //http://lawmin.nic.in/la/notarylist2.htm
        fetch("http://lawmin.nic.in/la/notarylist3.htm");
        fetch("http://lawmin.nic.in/la/notarylist1.htm");
        fetch("http://lawmin.nic.in/la/notarylist2.htm");
    }

    public void fetch(String url) {
        Logger.warn("Fetching " + url);
        WS.HttpResponse httpResponse = WS.url(url).get();
        Logger.warn("Fetched " + url);
        String string = httpResponse.getString();
        List<Notary> parse = parse(url, string);
        Logger.warn("Found " + parse.size() + " Notaries");
        for (Notary notary : parse) {
            notary.save();
        }
    }

    private List<Notary> parse(String url, String string) {
        Document finalPage = Jsoup.parse(string);
        Logger.warn("Parsed " + url);
        Elements trs = finalPage.select("tr");

        List<Notary> ret = new ArrayList<Notary>();
        Notary notary = null;
        for (int i = 0; i < trs.size(); i++) {
            if (i < 2) {
                //first few lines are headers
                continue;
            }
            Element tr = trs.get(i);
            Elements tds = tr.select("td");
            String slno = tds.get(0).html().trim();
            String addr = tds.get(3).html();
            if (StringUtils.isBlank(slno) && notary != null) {
                //continue previous address
                notary.address += addr;
            } else {
                //new address
                if (notary != null) {
                    ret.add(notary);
                }
                notary = new Notary();
                notary.slno = tds.get(1).html();
                notary.name = tds.get(2).html();

                notary.address = addr;
                notary.area = tds.get(4).html();
                //todo: try to fetch latlng and store it, so we can show it in a map


            }
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        String s = FileUtils.readFileToString(new File("/var/tmp/1.html"));
        new FetchJob().parse("", s);
    }
}
