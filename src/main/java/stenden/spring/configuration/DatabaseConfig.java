package stenden.spring.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement // Required for Hibernate
@EnableJpaRepositories("stenden.spring.data")
public class DatabaseConfig {

    /**
     * The {@link DataSource} representing the database connection.
     * In our case we're creating an in-memory database using H2,
     * so the setup is simple.
     *
     * @return The connection to the database
     */
    @Bean
    public DataSource dataSource() {
        // I could also use MariaDBDataSource
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mariadb://localhost:3306/example");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");
        return basicDataSource;
    }

    /**
     * JDBC by itself is tough to use, so we wrap it in the {@link JdbcTemplate} from Spring,
     * which handles a lot of the boilerplate for us.
     * Pure JDBC has its uses though. While it gives a lot of boilerplate,
     * it also gives a lot of control, which can be useful for certain applications.
     * Think of having to make very complex queries for very specific situations.
     *
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
