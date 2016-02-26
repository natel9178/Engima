package com.company;

import sun.jvm.hotspot.oops.Array;
import sun.jvm.hotspot.utilities.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nathaniel on 1/21/16.
 */
public class EnigmaCracker {
    public static void main (String[] args) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ROTORSETTINGS.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet rs;

            //Crack Enigma
            /*
            Input file:
            1) Message to decrypt (first six letters the key encrypted with daily key settings, followed by the message encrypted by the operator picked key)
            2) Lines of intercepts where line number should be >= 26 for the 26 letters.
            3) Next lines should be the number of intercepts.
             */

            BufferedReader br = new BufferedReader(new FileReader("/Users/nathaniel/IdeaProjects/Engima/src/com/company/EngimaCrack.in"));

            String message = br.readLine();
            int numberOfIntercepts = Integer.parseInt(br.readLine());

            HashMap<Character, Character> sigmaOneComposeFour = new HashMap<Character, Character>();
            HashMap<Character, Character> sigmaTwoComposeFive = new HashMap<Character, Character>();
            HashMap<Character, Character> sigmaThreeComposeSix = new HashMap<Character, Character>();

            for (int i = 0; i < numberOfIntercepts; i++) {
                String interceptedLetters = br.readLine().toLowerCase();
                sigmaOneComposeFour.put(interceptedLetters.charAt(0), interceptedLetters.charAt(3));
                sigmaTwoComposeFive.put(interceptedLetters.charAt(1), interceptedLetters.charAt(4));
                sigmaThreeComposeSix.put(interceptedLetters.charAt(2), interceptedLetters.charAt(5));
            }

            String structure = CycleStructureResolver.generateCycleSignatureBasedOnMaps(sigmaOneComposeFour, sigmaTwoComposeFive, sigmaThreeComposeSix);
            ArrayList<ArrayList<ArrayList<Character>>> notationStructure = CycleStructureResolver.generateCycles(sigmaOneComposeFour, sigmaTwoComposeFive, sigmaThreeComposeSix);

            rs = stmt.executeQuery("SELECT ROTORORDER, SETTING FROM ROTORSETTINGS WHERE FINGERPRINT = " + structure);
            while ( rs.next() ) {
                String numberOrder = rs.getString("ROTORORDER");
                String setting = rs.getString("ROTORSETTINGS");

                ArrayList<String> list = EnigmaExerciseGenerator.generateInterceptList(numberOrder.toLowerCase(), setting.toLowerCase());

                HashMap<Character, Character> sigmaOneComposeFour2 = new HashMap<Character, Character>();
                HashMap<Character, Character> sigmaTwoComposeFive2 = new HashMap<Character, Character>();
                HashMap<Character, Character> sigmaThreeComposeSix2 = new HashMap<Character, Character>();

                for (int l = 0; l < list.size(); l++) {
                    String interceptedLetters = list.get(l).toLowerCase();

                    sigmaOneComposeFour2.put(interceptedLetters.charAt(0), interceptedLetters.charAt(3));
                    sigmaTwoComposeFive2.put(interceptedLetters.charAt(1), interceptedLetters.charAt(4));
                    sigmaThreeComposeSix2.put(interceptedLetters.charAt(2), interceptedLetters.charAt(5));
                }

                ArrayList<ArrayList<ArrayList<Character>>> notationStructure2 = CycleStructureResolver.generateCycles(sigmaOneComposeFour2, sigmaTwoComposeFive2, sigmaThreeComposeSix2);

            }
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    private static HashMap<Character, Character> processNotations(ArrayList<ArrayList<ArrayList<Character>>> notationStructure1, ArrayList<ArrayList<ArrayList<Character>>> notationStructure2) {
        ArrayList<ArrayList<Character>> pluggedComp1First = notationStructure1.get(0);
        ArrayList<ArrayList<Character>> pluggedComp1Second = notationStructure1.get(1);
        ArrayList<ArrayList<Character>> pluggedComp1Third = notationStructure1.get(2);

        ArrayList<ArrayList<Character>> pluggedComp2First = notationStructure2.get(0);
        ArrayList<ArrayList<Character>> pluggedComp2Second = notationStructure2.get(1);
        ArrayList<ArrayList<Character>> pluggedComp2Third = notationStructure2.get(2);

        for (ArrayList<Character> sectionedNotation: pluggedComp1First) {
            if (sectionedNotation.size() == 1 && pluggedComp2First.contains(sectionedNotation)) {
                pluggedComp1First.remove(sectionedNotation);
                pluggedComp2First.remove(sectionedNotation);
            }
        }

        for (ArrayList<Character> sectionedNotation: pluggedComp1Second) {
            if (sectionedNotation.size() == 1 && pluggedComp2Second.contains(sectionedNotation)) {
                pluggedComp1Second.remove(sectionedNotation);
                pluggedComp2Second.remove(sectionedNotation);
            }
        }

        for (ArrayList<Character> sectionedNotation: pluggedComp1Third) {
            if (sectionedNotation.size() == 1 && pluggedComp2Third.contains(sectionedNotation)) {
                pluggedComp1Third.remove(sectionedNotation);
                pluggedComp2Third.remove(sectionedNotation);
            }
        }

        return new HashMap<Character, Character>();
    }
}
