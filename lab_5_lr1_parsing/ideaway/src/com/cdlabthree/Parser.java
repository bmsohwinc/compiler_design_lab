package com.cdlabthree;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Parser {
    HashMap<Integer, HashMap<String, String>> theParseTable;
    List<String> theTerminals;
    List<String> theNonTerminals;
    HashMap<Integer, DFAState> theDFA;

    Parser(List<String> theTerminals, List<String> theNonTerminals, HashMap<Integer, DFAState> theDFA, String theStartSymbol) {

        this.theDFA = theDFA;
        this.theNonTerminals = theNonTerminals;
        this.theTerminals = theTerminals;
        theParseTable = new HashMap<>();

        for (Integer stateId : theDFA.keySet()) {
            HashMap<String, Integer> theNexts = theDFA.get(stateId).NextState;
            HashMap<String, String> theTempMap = new HashMap<>();

            // Fill the Terms
            for (String term : theTerminals) {
                theTempMap.put(term, "-");
            }

            // Fill the NTerms
            for (String nterm : theNonTerminals) {
                theTempMap.put(nterm, "-");
            }

            // Add SHIFTS AND GOTOS
            if (theNexts.size() > 0) {
                for (String x : theNexts.keySet()) {
                    if (theTerminals.contains(x)) {
//                        System.out.println("Adding Shift for x: " + x + " and state: " + theNexts.get(x));
                        theTempMap.replace(x, "S" + theNexts.get(x));
                    }
                    else {
//                        System.out.println("Adding Goto for x: " + x + " and state: " + theNexts.get(x));
                        theTempMap.replace(x, "G" + theNexts.get(x));
                    }
                }
            }

            // Fill the REDUCES
            for (Item item : theDFA.get(stateId).StateItems) {
                int dotPosition = item.Production.indexOf('.');
                int itemProdLength = item.Production.length();

                String prod = item.Header + "|" + item.Production.substring(0, itemProdLength - 1);

                if (dotPosition == itemProdLength - 1) {
                    for (String y : item.LookAheads) {
//                        System.out.println("loading look y: " + y + " and prod: " + prod);
                        if (prod.equals(theStartSymbol + "'|" + theStartSymbol)) {
                            theTempMap.replace(y, "Acc");
                        }
                        else {
                            theTempMap.replace(y, "R" + prod);
                        }

                    }
                }
            }

            theParseTable.put(stateId, theTempMap);
        }

        printParseTable();
    }

    public Boolean parse(String theStringToBeParsed) {

        // Add the `$` symbol
        theStringToBeParsed += "$";

        // Initialize the 2 Stacks
        Stack<String> theSymbolStack = new Stack<>();
        Stack<Integer> theStateStack = new Stack<>();

        // Add `0` - the start state
        theStateStack.push(0);
        // Add `$` - the first symbol
        theSymbolStack.push("$");
        // Initialize start position of inputString
        int posInString = 0;

        System.out.println("--------\n Input String: " + theStringToBeParsed + "\n");
        System.out.printf("%30s\t%25s\t%12s\t\t%s\n", "STATE STACK", "SYMBOL STACK", "INPUT", "ACTION");
        System.out.printf("%30s\t%25s\t%12s\t\t%s\n", theStateStack, theSymbolStack, theStringToBeParsed.substring(posInString), "BEGIN");
        while (true) {
            String inputSymbol = Character.toString(theStringToBeParsed.charAt(posInString));
            Integer currState = theStateStack.peek();
            String tableEntry = theParseTable.get(currState).get(inputSymbol);
            if (tableEntry.equals("-")) {
                System.out.printf("%30s\t%25s\t%12s\t\t%s\n", theStateStack, theSymbolStack, theStringToBeParsed.substring(posInString), "`-` Detected. REJECT");
//                System.out.println("`-` Detected in tableEntry for currState: " + currState + " and inputSymbol: " + inputSymbol);
                return false;
            }
            else if (tableEntry.equals("Acc")) {
                System.out.printf("%30s\t%25s\t%12s\t\t%s\n", theStateStack, theSymbolStack, theStringToBeParsed.substring(posInString), "Accept");
                return true;
            }
            else {
                char actionType = tableEntry.charAt(0);
                if (actionType == 'S') {
                    // SHIFT ACTION
                    // push the input to the stack
                    theSymbolStack.push(inputSymbol);
                    // push the new state to the stack
                    Integer nxtState = Integer.parseInt(tableEntry.substring(1));
                    theStateStack.push(nxtState);
                    // proceed over the input
                    posInString++;
                    System.out.printf("%30s\t%25s\t%12s\t\t%s\n", theStateStack, theSymbolStack, theStringToBeParsed.substring(posInString), "Shift: " + tableEntry);
                }
                else {
                    // REDUCE ACTION
                    String header = Character.toString(tableEntry.charAt(1));
                    String prod = tableEntry.substring(3);
                    int prodLength = prod.length();

                    // Pop equal numbers of Symbols and States
                    for (int i = 0; i < prodLength; i++) {
                        if (theStateStack.isEmpty() || theSymbolStack.isEmpty()) {
                            System.out.println("(Reduce) Stack or Symbol empty before popping.");
                            return false;
                        }
                        theStateStack.pop();
                        theSymbolStack.pop();
                    }

                    // Push the header to Symbol stack
                    theSymbolStack.push(header);

                    // Get Goto state
                    Integer newCurrState = theStateStack.peek();
                    String gotoEntry = theParseTable.get(newCurrState).get(header);
                    Integer newNxtState = Integer.parseInt(gotoEntry.substring(1));

                    // Push the new state to Stack
                    theStateStack.push(newNxtState);

                    System.out.printf("%30s\t%25s\t%12s\t\t%s\n", theStateStack, theSymbolStack, theStringToBeParsed.substring(posInString), "Reduce: " + tableEntry + " and Goto: " + gotoEntry);
                }
            }
        }
    }

    public void printParseTable() {

        System.out.println("\n===========================================\nTHE PARSE TABLE\n");

        System.out.printf("%10s\t", "State");
        for (String x : theTerminals) {
            System.out.printf("%10s\t", x);
        }

        for (String x : theNonTerminals) {
            System.out.printf("%10s\t", x);
        }

        System.out.println("");

        for (Integer i : theParseTable.keySet()) {
            System.out.printf("%10d\t", i);
            HashMap<String, String> theStateData = theParseTable.get(i);

            for (String x : theTerminals) {
                System.out.printf("%10s\t", theStateData.get(x));
            }

            for (String x : theNonTerminals) {
                System.out.printf("%10s\t", theStateData.get(x));
            }
            System.out.println("");
        }
        System.out.println("\n===========================================\n");
    }
}
