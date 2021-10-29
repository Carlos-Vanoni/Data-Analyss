package com.carlosvanoni.challange.service;

import org.springframework.beans.factory.annotation.Autowired;

public class RunService implements Runnable {

    @Autowired
    AnalysisService analysisServicece;

    public void run() {
        try {
            while (true) {
                analysisServicece.execute();
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
