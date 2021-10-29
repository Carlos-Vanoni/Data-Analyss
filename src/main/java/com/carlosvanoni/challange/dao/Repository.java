package com.carlosvanoni.challange.dao;

import com.carlosvanoni.challange.exception.AcessRepositoryException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Repository {

    private final static String filesInbox = "%HOMEPATH%/data/in/";
    private final static String filesOutbox = "%HOMEPATH%/data/out/";
    private final  String FILEEXTINSION = "dat";
    private final long MAXFILESIZE = 30000;


    public String readInbox(String fileName) {
        try {
            File file = new File(filesInbox + fileName + "." + FILEEXTINSION);
            if (file.length() >= MAXFILESIZE) {
                throw new IndexOutOfBoundsException(("Length of the file is out of bounds"));
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1));
            Scanner in = new Scanner(reader);
            StringBuilder data = new StringBuilder();
            String line = "";
            while (in.hasNextLine()) {
                line = in.nextLine();
                if (!line.trim().equals(""))
                    data.append(line).append("\n");
            }
            in.close();
            return data.toString();
        }catch (IOException e) {
            throw new AcessRepositoryException("Error writing file to output repository");
        }
    }

    public void writeOutBox(String fileName, String dataOutput)  {
        try {
            FileWriter file = new FileWriter(filesOutbox + fileName + ".done." + FILEEXTINSION);
            PrintWriter writer = new PrintWriter(file);
            writer.print(dataOutput);
            writer.close();
        } catch (IOException e) {
            throw new AcessRepositoryException("Error writing file to output repository");
        }
    }

    public List<String> listFileNamesInbox() throws IOException {
        File directoryInbox = new File(filesInbox);
        File[] filesInbox = directoryInbox.listFiles();
        List<File> filesListInbox = Arrays.asList(filesInbox);
        List<String> filesListInboxNames = new ArrayList<>();
        for (File fileIn : filesListInbox) {
            String nameFileIn = fileIn.getName();
            String[] nameFileInSplited = fileIn.getName().split("\\.");
            if(nameFileInSplited.length == 2 && nameFileInSplited[nameFileInSplited.length-1].equals(FILEEXTINSION))
                filesListInboxNames.add(nameFileInSplited[0]);
        }
        return filesListInboxNames;
    }

    public List<String> listFileNamesOutbox() throws IOException {
        File directoryOutbox = new File(filesOutbox);
        File[] filesOutbox = directoryOutbox.listFiles();
        List<File> filesListOutbox = Arrays.asList(filesOutbox);
        List<String> filesListOutboxNames = new ArrayList<>();
        for (File fileOut : filesListOutbox) {
            String[] nameFileOutSplited = fileOut.getName().split("\\.");
            if(nameFileOutSplited.length == 3 && nameFileOutSplited[nameFileOutSplited.length-1].equals(FILEEXTINSION))
                filesListOutboxNames.add(nameFileOutSplited[0]);
        }
        return filesListOutboxNames;
    }
}
