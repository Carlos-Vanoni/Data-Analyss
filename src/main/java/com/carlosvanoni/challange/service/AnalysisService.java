package com.carlosvanoni.challange.service;

import com.carlosvanoni.challange.exception.AcessRepositoryException;
import com.carlosvanoni.challange.model.Customer;
import com.carlosvanoni.challange.model.SaleData;
import com.carlosvanoni.challange.model.Saleman;
import com.carlosvanoni.challange.dao.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class AnalysisService {

    private static final Logger logger = (Logger) LogManager.getLogger(AnalysisService.class);

    @Autowired
    private Repository repository;


    public String getDataInbox(String fileName) {
        try {
            return repository.readInbox(fileName);
        } catch (AcessRepositoryException e) {
            logger.error(e.getMessage());
            return null;
        } catch (IndexOutOfBoundsException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    public void setDataOutbox(String fileName, String dataOutput) {
        try {
            repository.writeOutBox(fileName, dataOutput);
        } catch (AcessRepositoryException e) {
            logger.error(e.getMessage());
        }
    }

    public String separatorDataOnSameLine(String data) {
        String dataRefactoredOne = data.replaceAll("001", "\n001");
        String dataRefactoredTwo = dataRefactoredOne.replaceAll("002", "\n002");
        String dataRefactoredTree = dataRefactoredTwo.replaceAll("003", "\n003");
        String[] dataWithSpaces = dataRefactoredTree.split("\n");
        StringBuilder dataRefactored = new StringBuilder();
        for (String line : dataWithSpaces) {
            if (!line.trim().equals(""))
                dataRefactored.append(line).append("\n");
        }
        return dataRefactored.toString();
    }

    public String removeCedilhaOnPersonName(String[] lineSplited) {
        StringBuilder salemanName = new StringBuilder(lineSplited[2]);
        if (lineSplited.length > 4) {
            for (int i = 3; i < lineSplited.length - 1; i++) {
                salemanName.append("ç").append(lineSplited[i]);
            }
        }
        return salemanName.toString();
    }

    public String removeCedilhaOnSale(String[] lineSplited) {
        StringBuilder salemanNameOnSaleNote = new StringBuilder(lineSplited[3]);
        if (lineSplited.length > 4) {
            for (int i = 4; i < lineSplited.length - 1; i++) {
                salemanNameOnSaleNote.append("ç").append(lineSplited[i]);
            }
        }
        return salemanNameOnSaleNote.toString();
    }

    public Saleman searchSaleman(String name, List<Saleman> salesmanList) {
        List<Saleman> list = salesmanList.stream().filter(o -> o.getName().contains(name)).collect(Collectors.toList());
        if (list.isEmpty())
            return null;
        else return list.get(0);
    }

    public String dataOuput(int amountCustomer, List<Saleman> salemanList, List<SaleData> mostExpanssivesSale) {
        StringBuilder dataOutput;
        dataOutput = new StringBuilder("Amount customers: " + amountCustomer + "\n"
                + "Amount saleman: " + salemanList.size() + "\n");
        for (SaleData saleData : mostExpanssivesSale) {
            dataOutput.append("Best sale  ID: ").append(saleData.getId()).append(" - Price: ").append(saleData.getPrice()).append("\n");
        }
        dataOutput.append("Worse saleman(s)" + "\n");
        double worsesSalePrice = salemanList.stream().sorted(Comparator.comparing(Saleman::getPriceOfSales)).collect(Collectors.toList()).get(0).getPriceOfSales();
        List<Saleman> worsesSaleman = salemanList.stream().filter(o -> o.getPriceOfSales() == worsesSalePrice).collect(Collectors.toList());
        for (Saleman saleman : worsesSaleman) {
            dataOutput.append("Saleman: ").append(saleman.getName()).append(" - price of sale: ").append(saleman.getPriceOfSales()).append("\n");
        }
        return dataOutput.toString();
    }

    public List<String> getFilesWithoutMatch() {
        try {
            List<String> fileNamesInbox = repository.listFileNamesInbox();
            List<String> fileNamesOutbox = repository.listFileNamesOutbox();
            fileNamesInbox.removeAll(fileNamesOutbox);
            return fileNamesInbox;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public String processData(String data) {
        List<Saleman> salemanList = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        List<SaleData> mostExpanssivesSale = new ArrayList<>();
        try {
            String Semicolon = data.replaceAll("ç", ";");
            String dataDiferentLines = separatorDataOnSameLine(Semicolon);
            String[] dataSplited = dataDiferentLines.split("\n");
            for (String line : dataSplited) {
                String[] lineSplited = line.split(";");
                switch (lineSplited[0]) {
                    case "001":
                        String salemanName = removeCedilhaOnPersonName(lineSplited);
                        String salemanSalary = lineSplited[lineSplited.length - 1];
                        Saleman salemanRegistred = new Saleman(Long.parseLong(lineSplited[0]), salemanName, Double.parseDouble(salemanSalary));
                        if (!salemanList.contains(salemanRegistred))
                            salemanList.add(salemanRegistred);
                        break;
                    case "002":
                        String customerName = removeCedilhaOnPersonName(lineSplited);
                        String customerArea = lineSplited[lineSplited.length - 1];
                        Customer customer = new Customer(Long.parseLong(lineSplited[0]), customerName, customerArea);
                        if (!customerList.contains(customer))
                            customerList.add(customer);
                        break;
                    case "003":
                        String salemanNameOnSaleNote = removeCedilhaOnSale(lineSplited);
                        String salesWithBrackets = lineSplited[2];
                        String sales = salesWithBrackets.substring(2, salesWithBrackets.length() - 2);
                        String[] saleNotesSplited = sales.split(",");
                        double priceOfSale = 0;
                        for (String sale : saleNotesSplited) {
                            String[] saleSplited = sale.split("-");
                            if (saleSplited.length != 3) {
                                logger.error("error processing data: missing information for the product sold");
                                break;
                            }
                            priceOfSale += Double.parseDouble(saleSplited[1]) * Double.parseDouble(saleSplited[2]);
                        }
                        Saleman saleman = searchSaleman(salemanNameOnSaleNote, salemanList);
                        if (saleman == null) {
                            logger.error("seleman related on data sale, is not registered: " + salemanNameOnSaleNote);
                            break;
                        }
                        saleman.addPriceOfSale(priceOfSale);
                         if (mostExpanssivesSale.isEmpty()) {
                            mostExpanssivesSale.add(new SaleData(Long.parseLong(lineSplited[1]), priceOfSale, lineSplited[3]));
                        } else if (priceOfSale >= mostExpanssivesSale.get(0).getPrice()) {
                        mostExpanssivesSale.add(new SaleData(Long.parseLong(lineSplited[1]), priceOfSale, lineSplited[3]));
                    }
                        break;
                    default:
                        logger.error("processData: line format is incorrect");
                }
            }
            return dataOuput(customerList.size(), salemanList, mostExpanssivesSale);
        } catch (Exception e) {
            logger.error("error reported in process");
            return "The content of this file is badly formatted or does not meet the standards for analysis";
        }
    }

    public void execute() {
        List<String> filesWithoutMatch = getFilesWithoutMatch();
        for (String fileName : filesWithoutMatch) {
            String dataInbox = getDataInbox(fileName);
            String dataProcessed = processData(dataInbox);
            setDataOutbox(fileName, dataProcessed);
        }
    }
}
