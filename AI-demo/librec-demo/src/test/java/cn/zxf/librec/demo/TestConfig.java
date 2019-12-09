package cn.zxf.librec.demo;

import java.io.IOException;

import net.librec.common.LibrecException;
import net.librec.conf.Configuration;
import net.librec.conf.Configuration.Resource;
import net.librec.job.RecommenderJob;

public class TestConfig {
    public void testRecommender() throws ClassNotFoundException, LibrecException, IOException {
        Configuration conf = new Configuration();
        Resource resource = new Resource( "rec/cf/itemknn-test.properties" );
        conf.addResource( resource );
        RecommenderJob job = new RecommenderJob( conf );
        job.runJob();
    }
}
