package com.arpit.multipleDbConnection.mySQLDb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
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
        entityManagerFactoryRef = "entityManagerFactoryBean",
        basePackages = {"com.arpit.multipleDbConnection.mySQLDb.repo"},
        transactionManagerRef = "transactionManager"
)
public class MySQLDbConfig {
    private Environment env;

    @Bean
    @Primary
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("first.datasource.url"));
        dataSource.setUsername(env.getProperty("first.datasource.username"));
        dataSource.setPassword(env.getProperty("first.datasource.password"));
        dataSource.setDriverClassName((env.getProperty("first.datasource.driver-class-name")));
        return dataSource;

    }
    @Bean(name = "entityManagerFactoryBean")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(adapter);
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props. put("hibernate. show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update") ;
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(props); ;
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.arpit.multipleDbConnection.mySQLDb.entities");
        return localContainerEntityManagerFactoryBean;
    }

    @Bean(name = "transactionManager")

    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return  manager;
    }
}
