package com.arpit.multipleDbConnection.postgresDB.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondEntityManagerFactory",
        basePackages = {"com.arpit.multipleDbConnection.psotgresDb.repo"},
        transactionManagerRef = "secondTransactionManger"
)
public class PostgresDBConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("second.datasource.url");
        dataSource.setDriverClassName("second.datasource.driver-class-nane");
        dataSource.setUsername("second.datasource.username");
        dataSource.setPassword("second.datasource.password");
        return dataSource;
    }

    @Bean(name = "secondEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate. show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update");
        bean.setJpaPropertyMap(props);
        bean.setPackagesToScan("com.arpit.multipleDbConnection.postgresDB.entities");
        return bean;
    }

    @Bean(name = "secondTransactionManger")
    @Primary
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(localContainerEntityManagerFactoryBean().getObject());
        return manager;
    }
}
