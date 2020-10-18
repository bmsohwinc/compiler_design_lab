package com.cdlabthree;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


class SubRoutine {
    public List<Boolean> run(String fileNumber) throws FileNotFoundException {
        HashMap<String, List<String>> theGrammar = new HashMap<>();
        /**
         * Get the Grammar, Terminals and NonTerminals here.
         */
        Scanner fileInput = new Scanner(new File("/home/dell/Documents/Sem-7-Institute's/courses/CD/compiler_design_lab/lab_5_lr1_parsing/ideaway/rsrs/input" + fileNumber + ".txt"));
        // Get Non Terminals
        String lineWithNonTerminals = fileInput.nextLine();
        List<String> theNonTerminals = new ArrayList<>(Arrays.asList(lineWithNonTerminals.split(" ")));

        // Get Terminals
        String lineWithTerminals = fileInput.nextLine();
        List<String> theTerminals = new ArrayList<>(Arrays.asList(lineWithTerminals.split(" ")));

        // Get Start Symbol and String to be parsed
        String[] startSymbolAndInputString = fileInput.nextLine().split(" ");
        String theStartSymbol = startSymbolAndInputString[0];
        List<String> stringsToBeParsed = new ArrayList<>(Arrays.asList(startSymbolAndInputString).subList(1, startSymbolAndInputString.length));

        // Get the Productions Now
        while (fileInput.hasNext()) {
            List<String> symbolAndProductions = new ArrayList<>(Arrays.asList(fileInput.nextLine().split(" ")));

            String symbol = symbolAndProductions.get(0);
            List<String> productions = symbolAndProductions.subList(1, symbolAndProductions.size());
            theGrammar.put(symbol, productions);
        }

        /**
         * Starting the LR(1) Parser
         */
        LR1Parser lr1Parser = new LR1Parser(
                theGrammar,
                theNonTerminals,
                theTerminals,
                theStartSymbol,
                stringsToBeParsed
        );

        return lr1Parser.run();
    }
}



public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        SubRoutine sr = new SubRoutine();
        System.out.println(sr.run("9"));
    }
}

