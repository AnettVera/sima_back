package mx.edu.utez.sima.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration 
public class DBConnection {
    @Value("${db.url}") 
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Bean 
    public DataSource getConnection(){
        try{
            DriverManagerDataSource conf = new DriverManagerDataSource();
            conf.setUrl(DB_URL);
            conf.setUsername(DB_USERNAME);
            conf.setPassword(DB_PASSWORD);
            conf.setDriverClassName("com.mysql.cj.jdbc.Driver"); 
            return conf;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

