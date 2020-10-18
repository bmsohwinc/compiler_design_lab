package com.cdlabthree;

import java.util.*;

public class RemoveLeftRecursion {
    HashMap<String, List<String>> theGrammar;
    Queue<String> avlSymbols = new PriorityQueue<>();

    RemoveLeftRecursion(HashMap<String, List<String>> theGrammar, List<String> theNonTerminals) {
        this.theGrammar = theGrammar;
        Set<String> temp = new HashSet<>(theNonTerminals);
        // store the available Symbols
        char jst = 'A';
        for (int i = 0; i < 26; i++) {
            if (!temp.contains("" + jst)) {
                avlSymbols.add("" + jst);
            }
            jst += 1;
        }
    }

    public HashMap<String, List<String>> run() {
        List<String> theKeys =  new ArrayList<>(theGrammar.keySet());
        // for every symbol A_i
        for (int i = 0; i < theKeys.size(); i++) {
            String symbol = theKeys.get(i);
            // for every prev symbol A_j
            for (int j = 0; j < i; j++) {
                String prevSymbol = theKeys.get(j);
                List<String> newProds = new ArrayList<>();
                // for every prod of A_i
                for (String aProd : theGrammar.get(symbol)) {
                    // if A_i --> A_jGamma holds,
                    if (aProd.startsWith(prevSymbol)) {
                        // remove this prod
                        // replace with all prods of A_j, appended with rem prod_part
                        System.out.println("Substituting : " + prevSymbol + " in " + symbol);
                        for (String prevProd : theGrammar.get(prevSymbol)) {
                            newProds.add(prevProd + aProd.replaceFirst(prevSymbol, ""));
                        }
                    }
                    else {
                        newProds.add(aProd);
                    }
                }

                if (newProds.size() != 0) {
                    theGrammar.replace(symbol, newProds);
                }

                checkLeftRecursion(symbol);
            }
        }

        return theGrammar;
    }

    public HashMap<String, List<String>> runbeta() {
        List<String> theKeys =  new ArrayList<>(theGrammar.keySet());
        for (String symbol : theKeys) {
            checkLeftRecursion(symbol);
        }
        System.out.println("\n>> The corrected Grammar...");
        printGrammar();
        return theGrammar;
    }

    public void checkLeftRecursion(String symbol) {

        List<String> lrs = new ArrayList<>();
        List<String> nlrs = new ArrayList<>();

        String newSymbol = avlSymbols.remove();

        for (String aProc : theGrammar.get(symbol)) {
            if (aProc.startsWith(symbol)) {
                System.out.println("Left Recursion Detected!" + " Symbol: " + symbol + " Proc: " + aProc);
                lrs.add(aProc.replaceFirst(symbol, "") + newSymbol);
            }
            else {
                if (aProc.equals("#")) {
                    nlrs.add(newSymbol);
                }
                else {
                    nlrs.add(aProc + newSymbol);
                }
            }
        }

        if (lrs.size() != 0) {
            lrs.add("#");
            theGrammar.replace(symbol, nlrs);
            theGrammar.put(newSymbol, lrs);
        }
        else {
            // since left recursion was not found, I am restoring the removed new symbol
            avlSymbols.add(newSymbol);
        }
    }

    public void printGrammar() {
        for (String key : theGrammar.keySet()) {
            System.out.println(key + " --> " + theGrammar.get(key));
        }
    }
}