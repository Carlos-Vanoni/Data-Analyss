package com.carlosvanoni.challange;


import com.carlosvanoni.challange.config.Config;
import com.carlosvanoni.challange.service.AnalysisService;
import com.carlosvanoni.challange.service.RunService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        AnalysisService analysisService = (AnalysisService) context.getBean(("analysisService"));
        RunService runService = (RunService) context.getBean("runService");

        analysisService.execute();
        //runService.run();
    }
}
