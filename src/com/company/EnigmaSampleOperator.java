package com.company;

import org.jetbrains.annotations.NotNull;
import sun.jvm.hotspot.utilities.Assert;

import java.io.*;
import java.util.*;

/*
The input file must look like the following:
1: Rotor Order (123)
2: Rotor Settings (AAA)
3: NumberOfPlugboardWiring (6)
4: PlugboardSetting 1 (AU)
...
10: Message To Encipher

Output:
Message/Error
*/

public class EnigmaSampleOperator {
    EnigmaMachine machine;
    String message;

    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("/Users/nathaniel/IdeaProjects/Engima/src/com/company/EnigmaOutput.out")));
        EnigmaSampleOperator operator = new EnigmaSampleOperator();
        operator.buildMachine();
        String cipherText = operator.cipherMessages(operator.message);

        pw.println(cipherText);
        pw.close();
    }

    private void buildMachine() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/nathaniel/IdeaProjects/Engima/src/com/company/EnigmaInput.in"));

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
        this.setMessage(br.readLine().toLowerCase());
    }

    @NotNull
    private String cipherMessages(String plaintext) {
        return machine.cipherMessages(plaintext);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
