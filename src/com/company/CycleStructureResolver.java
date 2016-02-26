package com.company;


import com.sun.tools.javac.util.ArrayUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class CycleStructureResolver {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/nathaniel/IdeaProjects/Engima/src/com/company/CycleStructureResolverInput.in"));
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

        generateCycleSignatureBasedOnMaps(sigmaOneComposeFour, sigmaTwoComposeFive, sigmaThreeComposeSix);

    }

    public static String generateCycleSignatureBasedOnMaps(HashMap<Character, Character> sigmaOneComposeFour, HashMap<Character, Character> sigmaTwoComposeFive, HashMap<Character, Character> sigmaThreeComposeSix) {
        if (sigmaOneComposeFour.size() < 26 || sigmaTwoComposeFive.size() < 26 || sigmaThreeComposeSix.size() < 26) {
            System.out.println("Not enough data to generate compositions. Please provide additional intercepts");
            return "";
        }
        else if (sigmaOneComposeFour.size() > 26 || sigmaTwoComposeFive.size() > 26 || sigmaThreeComposeSix.size() > 26) {
            System.out.println("Different keys used to generate intercepts. Overlapping exists");
            return "";
        }






        String alphabetStringOne = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
        List<Character> alphabetCharArrayOne = new ArrayList<Character>();
        for(char c : alphabetStringOne.toCharArray()) {
            alphabetCharArrayOne.add(c);
        }
        ArrayList<ArrayList<Character>> cycleStructuresigmaOneComposeFour = new ArrayList<ArrayList<Character>>();
        while (alphabetCharArrayOne.size() > 0) {
            ArrayList<Character> newStructure = new ArrayList<Character>();
            newStructure.add(alphabetCharArrayOne.get(0)); //Get first character of remaining alphabet string
            alphabetCharArrayOne.remove(0); //Remove the first character

            while (true) {
                Character nextCharacterToAdd = sigmaOneComposeFour.get(newStructure.get(newStructure.size() - 1));
                alphabetCharArrayOne.remove(nextCharacterToAdd);
                if (!newStructure.contains(nextCharacterToAdd)) {
                    newStructure.add(nextCharacterToAdd);
                } else {
                    cycleStructuresigmaOneComposeFour.add(newStructure);
                    break;
                }
            }
        }

        String alphabetStringTwo = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
        List<Character> alphabetCharArrayTwo = new ArrayList<Character>();
        for(char c : alphabetStringTwo.toCharArray()) {
            alphabetCharArrayTwo.add(c);
        }
        ArrayList<ArrayList<Character>> cycleStructuresigmaTwoComposeFive = new ArrayList<ArrayList<Character>>();
        while (alphabetCharArrayTwo.size() > 0) {
            ArrayList<Character> newStructure = new ArrayList<Character>();
            newStructure.add(alphabetCharArrayTwo.get(0)); //Get first character of remaining alphabet string
            alphabetCharArrayTwo.remove(0); //Remove the first character

            while (true) {
                Character nextCharacterToAdd = sigmaTwoComposeFive.get(newStructure.get(newStructure.size() - 1));
                alphabetCharArrayTwo.remove(nextCharacterToAdd);
                if (!newStructure.contains(nextCharacterToAdd)) {
                    newStructure.add(nextCharacterToAdd);
                } else {
                    cycleStructuresigmaTwoComposeFive.add(newStructure);
                    break;
                }
            }
        }


        String alphabetStringThree = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
        List<Character> alphabetCharArrayThree = new ArrayList<Character>();
        for(char c : alphabetStringThree.toCharArray()) {
            alphabetCharArrayThree.add(c);
        }
        ArrayList<ArrayList<Character>> cycleStructureSigmaThreeComposeSix = new ArrayList<ArrayList<Character>>();
        while (alphabetCharArrayThree.size() > 0) {
            ArrayList<Character> newStructure = new ArrayList<Character>();
            newStructure.add(alphabetCharArrayThree.get(0)); //Get first character of remaining alphabet string
            alphabetCharArrayThree.remove(0); //Remove the first character

            while (true) {
                Character nextCharacterToAdd = sigmaThreeComposeSix.get(newStructure.get(newStructure.size() - 1));
                alphabetCharArrayThree.remove(nextCharacterToAdd);
                if (!newStructure.contains(nextCharacterToAdd)) {
                    newStructure.add(nextCharacterToAdd);
                } else {
                    cycleStructureSigmaThreeComposeSix.add(newStructure);
                    break;
                }
            }
        }

        Collections.sort(cycleStructuresigmaOneComposeFour, new Comparator<ArrayList<Character>>(){
            public int compare(ArrayList<Character> o1, ArrayList<Character> o2){
                if (o1.size() > o2.size()) {
                    return -1;
                }
                else if (o1.size() < o2.size()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });

        Collections.sort(cycleStructuresigmaTwoComposeFive, new Comparator<ArrayList<Character>>(){
            public int compare(ArrayList<Character> o1, ArrayList<Character> o2){
                if (o1.size() > o2.size()) {
                    return -1;
                }
                else if (o1.size() < o2.size()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });

        Collections.sort(cycleStructureSigmaThreeComposeSix, new Comparator<ArrayList<Character>>(){
            public int compare(ArrayList<Character> o1, ArrayList<Character> o2){
                if (o1.size() > o2.size()) {
                    return -1;
                }
                else if (o1.size() < o2.size()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });


        String buildCycleStructureString = "";
        buildCycleStructureString += "(";

        for (int i = 0; i < cycleStructuresigmaOneComposeFour.size(); i++) {
            buildCycleStructureString += cycleStructuresigmaOneComposeFour.get(i).size();
            if (i != cycleStructuresigmaOneComposeFour.size() - 1) {
                buildCycleStructureString += "-";
            }
        }

        buildCycleStructureString += ", ";

        for (int i = 0; i < cycleStructuresigmaTwoComposeFive.size(); i++) {
            buildCycleStructureString += cycleStructuresigmaTwoComposeFive.get(i).size();
            if (i != cycleStructuresigmaTwoComposeFive.size() - 1) {
                buildCycleStructureString += "-";
            }
        }

        buildCycleStructureString += ", ";

        for (int i = 0; i < cycleStructureSigmaThreeComposeSix.size(); i++) {
            buildCycleStructureString += cycleStructureSigmaThreeComposeSix.get(i).size();
            if (i != cycleStructureSigmaThreeComposeSix.size() - 1) {
                buildCycleStructureString += "-";
            }
        }

        buildCycleStructureString += ")";

        return buildCycleStructureString;
    }

    public static ArrayList<ArrayList<ArrayList<Character>>> generateCycles(HashMap<Character, Character> sigmaOneComposeFour, HashMap<Character, Character> sigmaTwoComposeFive, HashMap<Character, Character> sigmaThreeComposeSix) {
        if (sigmaOneComposeFour.size() < 26 || sigmaTwoComposeFive.size() < 26 || sigmaThreeComposeSix.size() < 26) {
            System.out.println("Not enough data to generate compositions. Please provide additional intercepts");
            //return "";
        }
        else if (sigmaOneComposeFour.size() > 26 || sigmaTwoComposeFive.size() > 26 || sigmaThreeComposeSix.size() > 26) {
            System.out.println("Different keys used to generate intercepts. Overlapping exists");
            //return "";
        }






        String alphabetStringOne = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
        List<Character> alphabetCharArrayOne = new ArrayList<Character>();
        for(char c : alphabetStringOne.toCharArray()) {
            alphabetCharArrayOne.add(c);
        }
        ArrayList<ArrayList<Character>> cycleStructuresigmaOneComposeFour = new ArrayList<ArrayList<Character>>();
        while (alphabetCharArrayOne.size() > 0) {
            ArrayList<Character> newStructure = new ArrayList<Character>();
            newStructure.add(alphabetCharArrayOne.get(0)); //Get first character of remaining alphabet string
            alphabetCharArrayOne.remove(0); //Remove the first character

            while (true) {
                Character nextCharacterToAdd = sigmaOneComposeFour.get(newStructure.get(newStructure.size() - 1));
                alphabetCharArrayOne.remove(nextCharacterToAdd);
                if (!newStructure.contains(nextCharacterToAdd)) {
                    newStructure.add(nextCharacterToAdd);
                } else {
                    cycleStructuresigmaOneComposeFour.add(newStructure);
                    break;
                }
            }
        }

        String alphabetStringTwo = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
        List<Character> alphabetCharArrayTwo = new ArrayList<Character>();
        for(char c : alphabetStringTwo.toCharArray()) {
            alphabetCharArrayTwo.add(c);
        }
        ArrayList<ArrayList<Character>> cycleStructuresigmaTwoComposeFive = new ArrayList<ArrayList<Character>>();
        while (alphabetCharArrayTwo.size() > 0) {
            ArrayList<Character> newStructure = new ArrayList<Character>();
            newStructure.add(alphabetCharArrayTwo.get(0)); //Get first character of remaining alphabet string
            alphabetCharArrayTwo.remove(0); //Remove the first character

            while (true) {
                Character nextCharacterToAdd = sigmaTwoComposeFive.get(newStructure.get(newStructure.size() - 1));
                alphabetCharArrayTwo.remove(nextCharacterToAdd);
                if (!newStructure.contains(nextCharacterToAdd)) {
                    newStructure.add(nextCharacterToAdd);
                } else {
                    cycleStructuresigmaTwoComposeFive.add(newStructure);
                    break;
                }
            }
        }


        String alphabetStringThree = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
        List<Character> alphabetCharArrayThree = new ArrayList<Character>();
        for(char c : alphabetStringThree.toCharArray()) {
            alphabetCharArrayThree.add(c);
        }
        ArrayList<ArrayList<Character>> cycleStructureSigmaThreeComposeSix = new ArrayList<ArrayList<Character>>();
        while (alphabetCharArrayThree.size() > 0) {
            ArrayList<Character> newStructure = new ArrayList<Character>();
            newStructure.add(alphabetCharArrayThree.get(0)); //Get first character of remaining alphabet string
            alphabetCharArrayThree.remove(0); //Remove the first character

            while (true) {
                Character nextCharacterToAdd = sigmaThreeComposeSix.get(newStructure.get(newStructure.size() - 1));
                alphabetCharArrayThree.remove(nextCharacterToAdd);
                if (!newStructure.contains(nextCharacterToAdd)) {
                    newStructure.add(nextCharacterToAdd);
                } else {
                    cycleStructureSigmaThreeComposeSix.add(newStructure);
                    break;
                }
            }
        }

        Collections.sort(cycleStructuresigmaOneComposeFour, new Comparator<ArrayList<Character>>(){
            public int compare(ArrayList<Character> o1, ArrayList<Character> o2){
                if (o1.size() > o2.size()) {
                    return -1;
                }
                else if (o1.size() < o2.size()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });

        Collections.sort(cycleStructuresigmaTwoComposeFive, new Comparator<ArrayList<Character>>(){
            public int compare(ArrayList<Character> o1, ArrayList<Character> o2){
                if (o1.size() > o2.size()) {
                    return -1;
                }
                else if (o1.size() < o2.size()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });

        Collections.sort(cycleStructureSigmaThreeComposeSix, new Comparator<ArrayList<Character>>(){
            public int compare(ArrayList<Character> o1, ArrayList<Character> o2){
                if (o1.size() > o2.size()) {
                    return -1;
                }
                else if (o1.size() < o2.size()) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });

        ArrayList<ArrayList<ArrayList<Character>>> returnData = new ArrayList<>();
        returnData.add(cycleStructuresigmaOneComposeFour);
        returnData.add(cycleStructuresigmaTwoComposeFive);
        returnData.add(cycleStructureSigmaThreeComposeSix);

        return returnData;
    }
}
