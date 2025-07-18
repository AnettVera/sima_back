package mx.edu.utez.sima.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration // This annotation indicates that this class can be used by the Spring IoC container as a source of bean definitions.
public class DBConnection {
    @Value("${db.url}") // This annotation is used to inject the value of the property db.url from application.properties or application.yml.
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Bean // This annotation indicates that a method produces a bean to be managed by the Spring container.
    public DataSource getConnection(){
        try{
            DriverManagerDataSource conf = new DriverManagerDataSource();
            conf.setUrl(DB_URL);
            conf.setUsername(DB_USERNAME);
            conf.setPassword(DB_PASSWORD);
            conf.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Set the driver class name for MySQL
            return conf;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // In case of an error, return null or handle it as needed
        }
    }
}

