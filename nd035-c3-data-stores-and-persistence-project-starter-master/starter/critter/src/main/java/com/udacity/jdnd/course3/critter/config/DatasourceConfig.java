package com.udacity.jdnd.course3.critter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DatasourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "com.udacity.datasource")
    public DataSource getDatasource(){
        DataSourceBuilder dsb = DataSourceBuilder.create();
        //dsb.username("sa");
        //dsb.password(securePasswordService());
        dsb.url("jdbc:mysql://localhost:3306/critterdb");
//        dsb.url("jdbc:mysql://localhost:3306/plant?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useTimezone=true&serverTimezone=Europe/Berlin");
        return dsb.build();
    }

    private String securePasswordService(){
        return "sa1234";
    }

}
