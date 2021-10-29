package com.carlosvanoni.challange;


import com.carlosvanoni.challange.config.Config;
import com.carlosvanoni.challange.service.AnalysisService;
import com.carlosvanoni.challange.service.RunService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
       
        RunService runService = (RunService) context.getBean("runService");

        runService.run();
    }
}
