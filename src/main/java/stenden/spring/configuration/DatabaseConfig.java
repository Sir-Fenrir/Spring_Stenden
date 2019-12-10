package stenden.spring.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableLoadTimeWeaving
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
    return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .addScript("classpath:insert-data.sql")
            .build();
  }

  /**
   * When just using JPA, you could also use this transaction manager.
   *
   * @return
   */
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(emf);
    return jpaTransactionManager;
  }

  @Bean
  public BeanPostProcessor persistenceTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
    adapter.setDatabase(Database.H2);
    return adapter;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
    entityManagerFactoryBean.setPackagesToScan("stenden.spring.data.model");
    return entityManagerFactoryBean;
  }
}
