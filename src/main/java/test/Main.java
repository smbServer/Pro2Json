package test;

import xyz.frish2021.pro2Json.Pro2Json;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File inFile = new File(System.getProperty("user.dir"), "mapping.txt");
        File outFile = new File(System.getProperty("user.dir"), "mapping.json");

        Pro2Json pro2Json = new Pro2Json(inFile, outFile);
        pro2Json.genJsonMapping();
    }
}
