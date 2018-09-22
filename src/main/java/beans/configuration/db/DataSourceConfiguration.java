package beans.configuration.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 20/2/16
 * Time: 3:49 PM
 */
@Configuration
@PropertySource("classpath:db.properties")
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.user}")
    private String user;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(url, user, password);
        driverManagerDataSource.setDriverClassName(driver);
//        driverManagerDataSource
//                .getConnection(user, password)
//                .prepareStatement("create table if not exists persistent_logins (username varchar(64) not null," +
//                "series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)").execute();
        return driverManagerDataSource;
    }
}
