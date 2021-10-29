package com.carlosvanoni.challange.service;

import com.carlosvanoni.challange.config.Config;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;


class AnalysisServiceTest {

    private final ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private final AnalysisService analysisService = (AnalysisService) context.getBean(("analysisService"));


    @Test
    public void shouldProcessRegularData() {
        String data = "001ç1234567891234çDiegoç50000\n" +
                "001ç3245678865434çRenatoç40000.99\n" +
                "002ç2345675434544345çJose da SilvaçRural\n" +
                "002ç2345675433444345çEduardoPereiraçRural\n" +
                "003ç10ç [1-10-100,2-30-2.50,3-40-3] çDiego\n" +
                "003ç08ç [1-34-10,2-33-1.50,3-40-0] çRenato";
        String dataExcepted = "Amount customers: 2\n" +
                "Amount saleman: 2\n" +
                "Best sale  ID: 10 - Price: 1195.0\n" +
                "Worse saleman(s)\n" +
                "Saleman: Renato - price of sale: 389.5\n";
        String dataProcessed = analysisService.processData(data);
        assertEquals(dataExcepted, dataProcessed);
    }

    @Test
    public void shouldProcessDataWithNamesWithCedilha() {
        String data = "001ç1234567891234çCaçioç50000\n" +
                "001ç3245678865434çFogaçaç40000.99\n" +
                "002ç2345675434544345çMaria da GraçaçRural\n" +
                "002ç2345675433444345çEduardoPereiraçRural\n" +
                "003ç10ç [1-10-100,2-30-2.50,3-40-3] çCaçio\n" +
                "003ç08ç [1-34-10,2-33-1.50,3-40-0] çFogaça";
        String dataExcepted = "Amount customers: 2\n" +
                "Amount saleman: 2\n" +
                "Best sale  ID: 10 - Price: 1195.0\n" +
                "Worse saleman(s)\n" +
                "Saleman: Fogaça - price of sale: 389.5\n";
        String dataProcessed = analysisService.processData(data);
        assertEquals(dataExcepted, dataProcessed);
    }

    @Test
    public void canReturnDataWithOneOrMoreThenOneWorseSaleman() {
        String data = "001ç1234567891234çDiegoç50000\n" +
                "001ç3245678865434çRenatoç40000.99\n" +
                "001ç5555678865434çLucasç43340.50\n" +
                "002ç2345675434544345çJose da silvaçRural\n" +
                "002ç2345675433444345çEduardoPereiraçRural\n" +
                "003ç10ç [1-10-100,2-30-2.50,3-40-3] çDiego\n" +
                "003ç08ç [1-34-10,2-33-1.50,3-40-0] çRenato\n" +
                "003ç06ç [1-34-10,2-33-1.50,3-40-0] çLucas";
        String dataExcepted = "Amount customers: 2\n" +
                "Amount saleman: 3\n" +
                "Best sale  ID: 10 - Price: 1195.0\n" +
                "Worse saleman(s)\n" +
                "Saleman: Renato - price of sale: 389.5\n" +
                "Saleman: Lucas - price of sale: 389.5\n";
        String dataProcessed = analysisService.processData(data);
        assertEquals(dataExcepted, dataProcessed);
    }

    @Test
    public void shouldProcessDataWithElementsInTheSameLIne() {
        String data = "001ç1234567891234çCaçioç50000 001ç3245678865434çRenatoç40000.99 002ç2345675434544345çMaria da GraçaçRural\n" +
                "002ç2345675433444345çEduardoPereiraçRural\n" +
                "003ç10ç [1-10-100,2-30-2.50,3-40-3] çCaçio 003ç08ç [1-34-10,2-33-1.50,3-40-0] çRenato";
        String dataExcepted = "Amount customers: 2\n" +
                "Amount saleman: 2\n" +
                "Best sale  ID: 10 - Price: 1195.0\n" +
                "Worse saleman(s)\n" +
                "Saleman: Renato - price of sale: 389.5\n";
        String dataProcessed = analysisService.processData(data);
        assertEquals(dataExcepted, dataProcessed);
    }

    @Test
    public void shouldIgnoreIncompleteElementsOfSale() {
        String data = "001ç1234567891234çDiegoç50000\n" +
                "001ç3245678865434çRenatoç40000.99\n" +
                "002ç2345675434544345çJose da SilvaçRural\n" +
                "002ç2345675433444345çEduardoPereiraçRural\n" +
                "003ç10ç [1-10-100,2-30-2.50,3-40-3,2-3] çDiego\n" +
                "003ç08ç [1-34-10,2-33-1.50,3-40-0,10] çRenato";
        String dataExcepted = "Amount customers: 2\n" +
                "Amount saleman: 2\n" +
                "Best sale  ID: 10 - Price: 1195.0\n" +
                "Worse saleman(s)\n" +
                "Saleman: Renato - price of sale: 389.5\n";
        String dataProcessed = analysisService.processData(data);
        assertEquals(dataExcepted, dataProcessed);
    }

    @Test
    public void shouldReturnAWarningWhenReciveAInvalidData() {
        String dataOne = "";
        String dataTwo = "AAAAAAAAAAAAAAAAAAAAAA";
        String dataTree = "3453454T43534";
        String dataExcepted = "The content of this file is badly formatted or does not meet the standards for analysis";
        assertEquals(dataExcepted, analysisService.processData(dataOne));
        assertEquals(dataExcepted, analysisService.processData(dataTwo));
        assertEquals(dataExcepted, analysisService.processData(dataTree));
    }



}