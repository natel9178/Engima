package com.company;

import java.util.*;

public class EnigmaMachine {
    EngimaMachineSettings machineSettings;
    HashMap<Character, Character> defaultPlugboardSettings;
    String defaultRotorOrder;
    String defaultRotorSetting;

    public EnigmaMachine(HashMap<Character, Character> plugboardSettings, String rotorOrder, String rotorSetting) {
        this.machineSettings = new EngimaMachineSettings(plugboardSettings, rotorOrder, rotorSetting);
        this.defaultPlugboardSettings = plugboardSettings;
        this.defaultRotorOrder = rotorOrder;
        this.defaultRotorSetting = rotorSetting;
    }

    public void resetMachine() {
        this.machineSettings = new EngimaMachineSettings(defaultPlugboardSettings, defaultRotorOrder, defaultRotorSetting);
    }

    public String cipherMessages(String plaintext) {

        this.resetMachine();
        String cipherText = "";
        plaintext = plaintext.replaceAll("[^a-zA-Z]", "").toLowerCase();
        for (int i = 0; i < plaintext.length(); i++) {
            cipherText = cipherText + String.valueOf(this.doCipher(plaintext.charAt(i)));
        }

        return cipherText.toUpperCase();
    }

    private char swapPlugboard(char c) {
        if (machineSettings.getPlugboardMap().keySet().contains(c)) {
            return machineSettings.getPlugboardMap().get(c);
        }
        else {
            return c;
        }
    }

    private int validLetter(int n) {
        if (n <= 0) {
            // rotating backwards
            n = 26 + n;
        } else if (n >= 27) {
            // rotating forwards
            n = n - 26;
        }

        return n;
    }

    private int[] rotateCogs(int r, int m, int l) {
        int numberIndexOfRightSetting = machineSettings.getPlaintext().indexOf(machineSettings.getRightRotorSetting());
        int numberIndexOfMiddleSetting = machineSettings.getPlaintext().indexOf(machineSettings.getMidRotorSetting());
        int numberIndexOfLeftSetting = machineSettings.getPlaintext().indexOf(machineSettings.getLeftRotorSetting());

        if (numberIndexOfRightSetting == machineSettings.getArrknockpoints()[r][0] || numberIndexOfRightSetting == machineSettings.getArrknockpoints()[r][1]) {
            if (numberIndexOfMiddleSetting == machineSettings.getArrknockpoints()[m][0] || numberIndexOfMiddleSetting == machineSettings.getArrknockpoints()[m][1]) {
                numberIndexOfLeftSetting++;
            }
            numberIndexOfMiddleSetting++;
        } else {
            if (numberIndexOfMiddleSetting == machineSettings.getArrknockpoints()[m][0] || numberIndexOfMiddleSetting == machineSettings.getArrknockpoints()[m][1]) {
                numberIndexOfLeftSetting++;
                numberIndexOfMiddleSetting++;
            }
        }

        numberIndexOfRightSetting++;

        if (numberIndexOfRightSetting > 26) {numberIndexOfRightSetting = 1;}
        if (numberIndexOfMiddleSetting > 26) {numberIndexOfMiddleSetting = 1;}
        if (numberIndexOfLeftSetting > 26) {numberIndexOfLeftSetting = 1;}

        machineSettings.setRightRotorSetting(machineSettings.getPlaintext().charAt(numberIndexOfRightSetting));
        machineSettings.setMidRotorSetting(machineSettings.getPlaintext().charAt(numberIndexOfMiddleSetting));
        machineSettings.setLeftRotorSetting(machineSettings.getPlaintext().charAt(numberIndexOfLeftSetting));

        return new int[]{numberIndexOfRightSetting, numberIndexOfMiddleSetting, numberIndexOfLeftSetting};
    }

    private int mapLetter(int number, int pos, int wheelPosition, int wheel, int pass) {
        number = number - pos;
        number = validLetter(number);
        number = number + wheelPosition;
        number = validLetter(number);
        if (pass == 2) {
            char ch = machineSettings.getPlaintext().charAt(number);
            number = machineSettings.getArrayOfRotors()[wheel].indexOf(ch);
        } else {
            char ch = machineSettings.getArrayOfRotors()[wheel].charAt(number);
            number = machineSettings.getPlaintext().indexOf(ch);
        }

        number = number - wheelPosition;
        number = validLetter(number);
        number = number + pos;
        number = validLetter(number);

        return number;
    }

    public char doCipher(char input) {
        int rightWheel = machineSettings.getRightRotorNumber();
        int middleWheel = machineSettings.getMidRotorNumber();
        int leftWheel = machineSettings.getLeftRotorNumber();
        int[] wheelPosition = rotateCogs(rightWheel,middleWheel,leftWheel);
        int rightStart = wheelPosition[0];
        int middleStart = wheelPosition[1];
        int leftStart = wheelPosition[2];



        char pluggedChar = swapPlugboard(input);

        // Input
        int numberEncodedInput = machineSettings.getPlaintext().indexOf(pluggedChar);
        // 1 R Wheel
        numberEncodedInput = mapLetter(numberEncodedInput,1,rightStart,rightWheel, 1);
        // 1 M Wheel
        numberEncodedInput = mapLetter(numberEncodedInput,1,middleStart,middleWheel, 1);
        // 1 L Wheel
        numberEncodedInput = mapLetter(numberEncodedInput,1,leftStart,leftWheel, 1);
        // reflector
        numberEncodedInput = machineSettings.getPlaintext().indexOf(machineSettings.getArrReflector().charAt(numberEncodedInput));
        // 1 Wheel
        numberEncodedInput = mapLetter(numberEncodedInput,1,leftStart,leftWheel, 2);
        // 1 Wheel
        numberEncodedInput = mapLetter(numberEncodedInput,1,middleStart,middleWheel, 2);
        // 1 R Wheel
        numberEncodedInput = mapLetter(numberEncodedInput,1,rightStart,rightWheel, 2);


        // Plugboard

        return swapPlugboard(machineSettings.getPlaintext().charAt(numberEncodedInput));
    }
}

class EngimaMachineSettings {
    private String plaintext;
    private String[] arrRotors;

    private int[][] arrknockpoints;
    private String arrReflector = ".YRUHQSLDPXNGOKMIEBFZCWVJAT".toLowerCase();


    private HashMap<Character, Character> plugboardMap = new HashMap<>();
    private char rightRotorSetting;
    private char midRotorSetting;
    private char leftRotorSetting;

    private int rightRotorNumber;
    private int midRotorNumber;
    private int leftRotorNumber;

    public EngimaMachineSettings(HashMap<Character, Character> plugboardSettings, String rotorOrder, String rotorSetting) {

        this.plaintext = ".ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();

        this.arrRotors = new String[4];
        this.arrRotors[0] = " ";
        this.arrRotors[1] = ".EKMFLGDQVZNTOWYHXUSPAIBRCJ".toLowerCase();
        this.arrRotors[2] = ".AJDKSIRUXBLHWTMCQGZNPYFVOE".toLowerCase();
        this.arrRotors[3] = ".BDFHJLCPRTXVZNYEIWGAKMUSQO".toLowerCase();

        this.arrknockpoints = new int[4][];
        this.arrknockpoints[0] = new int[0];
        this.arrknockpoints[1] = new int[]{17, 17};
        this.arrknockpoints[2] = new int[]{5, 5};
        this.arrknockpoints[3] = new int[]{22, 22};

        String rotorOrder1 = rotorOrder;
        this.leftRotorNumber = Integer.parseInt(String.valueOf(rotorOrder1.charAt(0)));
        this.midRotorNumber = Integer.parseInt(String.valueOf(rotorOrder1.charAt(1)));
        this.rightRotorNumber = Integer.parseInt(String.valueOf(rotorOrder1.charAt(2)));

        this.leftRotorSetting = rotorSetting.charAt(0);
        this.midRotorSetting = rotorSetting.charAt(1);
        this.rightRotorSetting = rotorSetting.charAt(2);

        this.plugboardMap = plugboardSettings;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public String[] getArrayOfRotors() {
        return arrRotors;
    }

    public int[][] getArrknockpoints() {
        return arrknockpoints;
    }

    public String getArrReflector() {
        return arrReflector;
    }

    public HashMap<Character, Character> getPlugboardMap() {
        return plugboardMap;
    }


    public char getRightRotorSetting() {
        return rightRotorSetting;
    }

    public void setRightRotorSetting(char rightRotorSetting) {
        this.rightRotorSetting = rightRotorSetting;
    }

    public char getMidRotorSetting() {
        return midRotorSetting;
    }

    public void setMidRotorSetting(char midRotorSetting) {
        this.midRotorSetting = midRotorSetting;
    }

    public char getLeftRotorSetting() {
        return leftRotorSetting;
    }

    public void setLeftRotorSetting(char leftRotorSetting) {
        this.leftRotorSetting = leftRotorSetting;
    }

    public int getRightRotorNumber() {
        return rightRotorNumber;
    }

    public int getMidRotorNumber() {
        return midRotorNumber;
    }

    public int getLeftRotorNumber() {
        return leftRotorNumber;
    }
}