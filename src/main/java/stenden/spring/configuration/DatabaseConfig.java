package stenden.spring.configuration;

import org.mariadb.jdbc.MariaDbPoolDataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig extends AbstractDatabaseConfig {

    @Override
    public DataSource dataSource() throws Exception {
        MariaDbPoolDataSource dataSource = new MariaDbPoolDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mariadb://localhost:3306/example");
        return dataSource;
    }

    @Override
    public Database getDatabaseType() {
        return Database.MYSQL;
    }
}
