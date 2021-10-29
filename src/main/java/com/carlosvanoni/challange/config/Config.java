package com.carlosvanoni.challange.config;

import com.carlosvanoni.challange.dao.Repository;
import com.carlosvanoni.challange.service.AnalysisService;
import com.carlosvanoni.challange.service.RunService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.carlosvanoni.challange")
public class Config {

    @Bean
    public Repository repository() {
        return new Repository();
    }

    @Bean
    public AnalysisService analysisService() {
        return new AnalysisService();
    }

    @Bean
    public RunService runService() {
        return new RunService();
    }


}
