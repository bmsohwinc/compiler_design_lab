package com.cdlabthree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class ProduceLeftFactoredGrammar {
    HashMap<String, List<String>> theGrammar = new HashMap<>();
    Queue<String> avlSymbols = new PriorityQueue<>();

    ProduceLeftFactoredGrammar(HashMap<String, List<String>> theGrammar, List<String> theNonTerminals) {
        this.theGrammar = theGrammar;
        // add all NonTerminals present in the Grammar
        Set<String> temp = new HashSet<>(theNonTerminals);

//        // get all NonTerminals present in the Grammar
//        for (String s : theGrammar.keySet()) {
//            for (int i = 0; i < s.length(); i++) {
//                char x = s.charAt(i);
//                if (x >= 'A' && x <= 'Z') {
//                    temp.add("" + x);
//                }
//            }
//
//            for (String aprod : theGrammar.get(s)) {
//                for (int i = 0; i < aprod.length(); i++) {
//                    char x = aprod.charAt(i);
//                    if (x >= 'A' && x <= 'Z') {
//                        temp.add("" + x);
//                    }
//                }
//            }
//        }

        // store the available Symbols
        char jst = 'A';
        for (int i = 0; i < 26; i++) {
            if (!temp.contains("" + jst)) {
                avlSymbols.add("" + jst);
            }
            jst += 1;
        }

    }

    public HashMap<String, List<String>> factorize(String symbol) {

        List<String> allProds = theGrammar.get(symbol);
        HashMap<String, List<String>> res = new HashMap<>();

        if (allProds.size() == 1) {
            return res;
        }

        // store the new strings for the same symbol
        List<String> newProductions = new ArrayList<>();

        int idx = 0, n = allProds.size();
        char chx = allProds.get(0).charAt(0);
        for (int i = 1; i < n; i++) {
            char curr_chx = allProds.get(i).charAt(0);
            if (curr_chx != chx) {
                if (idx == i - 1) {
                    newProductions.add(allProds.get(idx));
                }
                else {
                    // add new production!
                    // But first find the longest common prefix
                    int z = 0;
                    String s1, s2;
                    s1 = allProds.get(idx);
                    s2 = allProds.get(i - 1);
                    System.out.println("\nmatching s1 = " + s1 + " and s2 = " + s2);
                    for (z = 0; z < Math.min(s1.length(), s2.length()); z++) {
                        if (s1.charAt(z) != s2.charAt(z)) {
                            break;
                        }
                    }

                    String thePrefix = s1.substring(0, z);
                    System.out.println("z = " + z + " and prefix: " + thePrefix);
                    String newSymbol = avlSymbols.remove();

                    // replacing earlier set of productions by a simgle production for the original symbol
                    newProductions.add(thePrefix + newSymbol);

                    // add the set of productions to a new symbol
                    List<String> newSymbolProds = new ArrayList<>();
                    for (z = idx; z < i; z++) {
                        System.out.println(".. newString as is: " + allProds.get(z));
                        String newString = allProds.get(z).replaceFirst(thePrefix, "");
                        // Check if any string is itself a prefix, so that epsilon should be added
                        newSymbolProds.add(newString.length() == 0 ? "#" : newString);
                    }

                    // add this new symbol + its prods to the hashmap
                    res.put(newSymbol, newSymbolProds);
                }

                idx = i;
                chx = curr_chx;
                if (idx == n - 1) newProductions.add(allProds.get(idx));
            }
            else {
                // the first-char matched till the last string!
                if (i == n - 1) {

                    // add new production!
                    // But first find the longest common prefix
                    int z = 0;
                    String s1, s2;
                    s1 = allProds.get(idx);
                    s2 = allProds.get(i);
                    System.out.println("\nmatching s1 = " + s1 + " and s2 = " + s2);
                    for (z = 0; z < Math.min(s1.length(), s2.length()); z++) {
                        if (s1.charAt(z) != s2.charAt(z)) {
                            break;
                        }
                    }

                    String thePrefix = s1.substring(0, z);
                    System.out.println("z = " + z + " and prefix: " + thePrefix);
                    String newSymbol = avlSymbols.remove();

                    // replacing earlier set of productions by a simgle production for the original symbol
                    newProductions.add(thePrefix + newSymbol);

                    // add the set of productions to a new symbol
                    List<String> newSymbolProds = new ArrayList<>();
                    for (z = idx; z <= i; z++) {
                        String newString = allProds.get(z).replaceFirst(thePrefix, "");
                        newSymbolProds.add(newString.length() == 0 ? "#" : newString);
                    }

                    // add this new symbol + its prods to the hashmap
                    res.put(newSymbol, newSymbolProds);
                }
            }
        }

        // changing the original set of productions for this symbol
        theGrammar.replace(symbol, newProductions);

        return res;
    }

    public HashMap<String, List<String>> run() {
        boolean done = false;
        while (!done) {
            done = true;
            HashMap<String, List<String>> newKeysToPut = new HashMap<>();

            for (String symbol : theGrammar.keySet()) {
                List<String> allProds = theGrammar.get(symbol);
                Collections.sort(allProds);
                theGrammar.replace(symbol, allProds);

                HashMap<String, List<String>> temp = factorize(symbol);
                if (temp.keySet().size() != 0) {
                    done = false;
                    newKeysToPut.putAll(temp);
                }
            }

            theGrammar.putAll(newKeysToPut);

            // System.out.println("--- Factored ---");
            // printGrammar();
        }
        System.out.println("--- FINAL ---");
        printGrammar();

        return theGrammar;
    }

    public void printGrammar() {
        for (String key : theGrammar.keySet()) {
            System.out.println(key + " --> " + theGrammar.get(key));
        }
    }
}
