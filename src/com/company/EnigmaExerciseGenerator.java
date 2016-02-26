package com.company;

import org.jetbrains.annotations.NotNull;
import sun.jvm.hotspot.utilities.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/*
The input file must look like the following:
1: Rotor Order (123)
2: Rotor Settings (AAA)
3: NumberOfPlugboardWiring (6)
4: PlugboardSetting 1 (AU)
...

Output:
List of "intercepted" first 6 letters.
*/

public class EnigmaExerciseGenerator {
    EnigmaMachine machine;

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("/Users/nathaniel/IdeaProjects/Engima/src/com/company/ExercizeOutput.out")));
        EnigmaExerciseGenerator operator = new EnigmaExerciseGenerator();
        operator.buildMachine();

        String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabetString.length(); i++) {
            String sequenceToCipher = "";
            for (int j = 0; j < 6; j++) {
                sequenceToCipher += alphabetString.substring(i, i+1);
            }

            pw.println(operator.cipherMessages(sequenceToCipher));
        }
        pw.close();
    }

    private void buildMachine() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/nathaniel/IdeaProjects/Engima/src/com/company/ExercizeInput.in"));

        String rotorOrder = br.readLine().toLowerCase();
        String rotorSettings = br.readLine().toLowerCase();
        int numberOfWirings = Integer.parseInt(br.readLine());

        HashMap<Character, Character> plugboardMap = new HashMap<Character, Character>();

        for (int i = 0; i < numberOfWirings; i++) {
            String line = br.readLine().toLowerCase();
            Character firstLetter = line.charAt(0);
            Character secondLetter = line.charAt(1);
            Assert.that(!plugboardMap.containsKey(firstLetter), "There should not be duplicate plugboard mappings");
            Assert.that(firstLetter != secondLetter, "Plugboard cannot map to itself in the real world");
            plugboardMap.put(firstLetter, secondLetter);
            plugboardMap.put(secondLetter, firstLetter);
        }

        this.machine = new EnigmaMachine(plugboardMap, rotorOrder, rotorSettings);
    }

    public static ArrayList<String> generateInterceptList(String order, String settings) {
        EnigmaMachine sampleMachine = new EnigmaMachine(new HashMap<Character, Character>(), order, settings);

        ArrayList<String> intercepts = new ArrayList<String>();
        String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabetString.length(); i++) {
            String sequenceToCipher = "";
            for (int j = 0; j < 6; j++) {
                sequenceToCipher += alphabetString.substring(i, i+1);
            }

            intercepts.add(sampleMachine.cipherMessages(sequenceToCipher));
        }

        return intercepts;
    }

    @NotNull
    private String cipherMessages(String plaintext) {
        return machine.cipherMessages(plaintext);
    }
}
