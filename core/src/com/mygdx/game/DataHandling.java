package com.mygdx.game;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class DataHandling {

    public static void writeFile(ArrayList<String> data_upgrade,int x) {
        Path path;
        if (x == 1){
            path = Paths.get("data_save/dataHighscore.txt");
        }else {
            path = Paths.get("data_save/dataUpgrade.txt");
        }
        System.out.println(path);

        boolean path_exits = Files.notExists(path);
        if (path_exits) {
            try {
                BufferedWriter bw = Files.newBufferedWriter(path,
                        StandardCharsets.ISO_8859_1,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
                for (int i = 0; i < data_upgrade.size(); i++) {
                    bw.write(data_upgrade.get(i));
                    bw.newLine();
                }
                bw.close();
                System.out.println("File telah terwrite");
            } catch (IOException ioe) {
                System.out.println("Error Writting File");
                System.exit(0);
            }
        }
    }

    static void readFile(ArrayList<String> listTulisan, int x) {
        Path path;
        if (x == 1){
            path = Paths.get("data_save/dataHighscore.txt");
        }else {
            path = Paths.get("data_save/dataUpgrade.txt");
        }
        String line = "";
//        Path path = Paths.get("data_save/dataHighscore.txt");

        try {
            BufferedReader fileInput = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"));

            line = fileInput.readLine();
            while (line != null) {
                listTulisan.add(line);
                line = fileInput.readLine();
            }
            fileInput.close();
            System.out.println("File telah diread");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (EOFException eofe) {
            System.out.println("No more lines to read");
        } catch (IOException ioe) {
            System.out.println("Error reading file");
        }
    }
    static String getdata(int baris,int x) {
        Path path;
        int i=0;
        if (x == 1){
            path = Paths.get("data_save/dataHighscore.txt");
        }else {
            path = Paths.get("data_save/dataUpgrade.txt");
        }
        String line = "";
//        Path path = Paths.get("data_save/dataHighscore.txt");

        try {
            BufferedReader fileInput = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"));

            line = fileInput.readLine();
            while (line != null) {
                if(i==baris){
                    return line;
                }
                line = fileInput.readLine();
                i++;
            }
            fileInput.close();
            System.out.println("File telah diread");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (EOFException eofe) {
            System.out.println("No more lines to read");
        } catch (IOException ioe) {
            System.out.println("Error reading file");
        }
        return "";
    }

    public static void editFile(ArrayList<String> listTulisan,int baris,int data, int i) {
        Path path;
        if (i == 1){
            path = Paths.get("data_save/dataHighscore.txt");
        }else {
            path = Paths.get("data_save/dataUpgrade.txt");
        }
        String data1=String.valueOf(data);
        listTulisan.set(baris, data1);
//        Path path = Paths.get("data_save/dataHighscore.txt");
        try {
            BufferedWriter bw = Files.newBufferedWriter(path,
                    Charset.forName("ISO-8859-1"),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            for (int x = 0; x < listTulisan.size(); x++) {
                bw.write(listTulisan.get(x));
                bw.newLine();
            }
            bw.close();
            System.out.println("File telah diedit");
        } catch (IOException ioe) {
            System.out.println("Error Writting File");
            System.exit(0);
        }
    }
    public static void editFile(ArrayList<String> listTulisan,int baris,float data, int i) {
        Path path;
        if (i == 1){
            path = Paths.get("data_save/dataHighscore.txt");
        }else {
            path = Paths.get("data_save/dataUpgrade.txt");
        }
        System.out.println(path);
        String data1=String.valueOf(data);
        listTulisan.set(baris, data1);
//        Path path = Paths.get("data_save/dataHighscore.txt");
        try {
            BufferedWriter bw = Files.newBufferedWriter(path,
                    Charset.forName("ISO-8859-1"),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            for (int x = 0; x < listTulisan.size(); x++) {
                bw.write(listTulisan.get(x));
                bw.newLine();
            }
            bw.close();
            System.out.println("File telah diedit");
        } catch (IOException ioe) {
            System.out.println("Error Writting File");
            System.exit(0);
        }
    }

}