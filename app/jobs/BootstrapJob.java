package jobs;

import models.AppConfig;
import models.Notary;
import models.User;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * Created with IntelliJ IDEA.
 * User: gowthaman
 * Date: 10/26/12
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
@OnApplicationStart
public class BootstrapJob extends Job {
    @Override
    public void doJob() throws Exception {
        if(User.count()==0){
            Logger.warn("Loading from yml");
            Fixtures.loadModels("data.yml");
        }
        if(AppConfig.count()==0){
            new AppConfig().save();
        }
        if(Notary.count()==0){
            new FetchJob().doJob();
        }
    }
}
