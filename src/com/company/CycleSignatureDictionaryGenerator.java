package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class CycleSignatureDictionaryGenerator {
    public static void main(String[] args) throws IOException {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ROTORSETTINGS.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            c.setAutoCommit(false);
//            String sql = "CREATE TABLE ROTORSETTINGS" +
//                    "(ROTORORDER           TEXT," +
//                    "SETTING            INT," +
//                    "FINGERPRINT        TEXT)";
//            stmt.executeUpdate(sql);

            long startTime = System.nanoTime();
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("/Users/nathaniel/IdeaProjects/Engima/src/com/company/Dictionary.out")));
            ArrayList<String> permutationsOf123 = permutation("123");
            String plaintext = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
            int operations = 0;
            for (String permutation : permutationsOf123) {
                for (int i = 0; i < 26; i++) {
                    System.out.println("Progress: " + String.format("%.2f", (((float) i / 26.0) * 100.0)) + "% of the rotor order, " + permutation);
                    for (int j = 0; j < 26; j++) {
                        for (int k = 0; k < 26; k++) {
                            String stringInScope = String.valueOf(plaintext.charAt(i)) + String.valueOf(plaintext.charAt(j)) + String.valueOf(plaintext.charAt(k));
                            ArrayList<String> intercepts = EnigmaExerciseGenerator.generateInterceptList(permutation, stringInScope);

                            HashMap<Character, Character> sigmaOneComposeFour = new HashMap<Character, Character>();
                            HashMap<Character, Character> sigmaTwoComposeFive = new HashMap<Character, Character>();
                            HashMap<Character, Character> sigmaThreeComposeSix = new HashMap<Character, Character>();

                            for (int l = 0; l < intercepts.size(); l++) {
                                String interceptedLetters = intercepts.get(l).toLowerCase();

                                sigmaOneComposeFour.put(interceptedLetters.charAt(0), interceptedLetters.charAt(3));
                                sigmaTwoComposeFive.put(interceptedLetters.charAt(1), interceptedLetters.charAt(4));
                                sigmaThreeComposeSix.put(interceptedLetters.charAt(2), interceptedLetters.charAt(5));
                            }

                            String structure = CycleStructureResolver.generateCycleSignatureBasedOnMaps(sigmaOneComposeFour, sigmaTwoComposeFive, sigmaThreeComposeSix);

                            pw.println(permutation + "\t" + stringInScope.toUpperCase() + "\t" + structure);
                            String sqlUpdate = "INSERT INTO ROTORSETTINGS (ROTORORDER, SETTING, FINGERPRINT) " +
                                    "VALUES (" + "'" + permutation + "'" + "," + "'" + stringInScope.toUpperCase() + "'" + "," + "'" + structure + "'" + ");";
                            stmt.executeUpdate(sqlUpdate);
                            c.commit();
                            operations++;
                        }
                    }
                }
            }
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            pw.println(operations + " operations have been completed in " + duration + " milliseconds.");
            pw.close();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    public static ArrayList<String> permutation(String s) {
        ArrayList<String> res = new ArrayList<String>();
        if (s.length() == 1) {
            res.add(s);
        } else if (s.length() > 1) {
            int lastIndex = s.length() - 1;
            String last = s.substring(lastIndex);
            String rest = s.substring(0, lastIndex);
            res = merge(permutation(rest), last);
        }
        return res;
    }

    public static ArrayList<String> merge(ArrayList<String> list, String c) {
        ArrayList<String> res = new ArrayList<String>();
        for (String s : list) {
            for (int i = 0; i <= s.length(); ++i) {
                String ps = new StringBuffer(s).insert(i, c).toString();
                res.add(ps);
            }
        }
        return res;
    }
}