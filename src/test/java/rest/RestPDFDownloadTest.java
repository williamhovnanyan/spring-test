package rest;

import beans.configuration.AppConfiguration;
import beans.configuration.TestBookingServiceConfiguration;
import beans.configuration.db.DataSourceConfiguration;
import beans.configuration.db.DbSessionFactory;
import com.lowagie.text.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.print.Doc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class,
        TestBookingServiceConfiguration.class})
public class RestPDFDownloadTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testPDFDownload() throws Exception {
        ResponseEntity<Document> documentResponseEntity = restTemplate.getForEntity("http://localhost:8080/springmvc/", Document.class);
    }
}
