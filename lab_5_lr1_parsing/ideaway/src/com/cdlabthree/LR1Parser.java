package com.cdlabthree;

import java.util.*;

public class LR1Parser {
    /**
     * 1. Get the Grammar, the Terminals, the Non Terminals
     * 2. Perform Left Factorization
     * 3. Remove Left Recursion
     * 4. Find the Firsts for each Non Terminal
     * 5. Build the Automaton
     * 6.
     */

    HashMap<String, List<String>> theGrammar;
    List<String> theNonTerminals;
    List<String> theTerminals;
    String theStartSymbol;
    List<String> stringsToBeParsed;
    HashMap<String, List<String>> theFirstSet;

    LR1Parser (
            HashMap<String, List<String>> theGrammar,
            List<String> theNonTerminals,
            List<String> theTerminals,
            String theStartSymbol,
            List<String> stringsToBeParsed ) {

        this.theGrammar = theGrammar;
        this.theNonTerminals = theNonTerminals;
        this.theTerminals = theTerminals;
        this.theStartSymbol = theStartSymbol;
        this.stringsToBeParsed = stringsToBeParsed;
    }

    public List<Boolean> run() {
        printMyGrammar("BEFORE ANY OPERATION");

        // Left Factor the Grammar
        ProduceLeftFactoredGrammar plfg = new ProduceLeftFactoredGrammar(theGrammar, theNonTerminals);
        theGrammar = plfg.run();
        printMyGrammar("AFTER LEFT FACTORING");

        // Update the Non Terminals (new additions due to Left Factoring)
        theNonTerminals = new ArrayList<>(theGrammar.keySet());

        // Remove Left Recursion
        RemoveLeftRecursion rlr = new RemoveLeftRecursion(theGrammar, theNonTerminals);
        theGrammar = rlr.runbeta();
        printMyGrammar("AFTER REMOVING LEFT RECURSION");

        // Update the Non Terminals (new additions due to Left Recursion Removal)
        theNonTerminals = new ArrayList<>(theGrammar.keySet());

        // Find First Sets for each Non Terminal
        FindFirstSet ffs = new FindFirstSet(theGrammar, theNonTerminals, theTerminals);
        theFirstSet = ffs.run(theStartSymbol);

        System.out.println("THE FIRST SETS ARE:");
        for (String nt : theFirstSet.keySet()) {
            System.out.println(nt + "\t-->\t" + theFirstSet.get(nt));
        }
        System.out.println("\n==================================\n");

        // Build the DFA now
        theTerminals.add("$"); // ensure this is taken as the default LookAhead
        DFABuilder dfb = new DFABuilder(theGrammar, theNonTerminals, theTerminals, theFirstSet);
        Set<String> theLookAheadSet = new HashSet<>();
        theLookAheadSet.add("$");
        Item seedItem = new Item(theStartSymbol + "'", "." + theStartSymbol, theLookAheadSet);
        Set<Item> seedItemSet = new HashSet<>();
        seedItemSet.add(seedItem);
        HashMap<Integer, DFAState> theDFA = dfb.run(seedItemSet);

        // Build Parse Table and Parse the Input String
        theTerminals.remove("#"); // we don't want the epsilon in the parse table
        Parser prsr = new Parser(theTerminals, theNonTerminals, theDFA, theStartSymbol);

        List<Boolean> results = new ArrayList<>();
        for (String theStringToBeParsed : stringsToBeParsed) {
            results.add(prsr.parse(theStringToBeParsed));
        }
        System.out.println("--------\n");
        return results;
    }

    public void ackMyItems() {
        System.out.println("Grammar:\n" + theGrammar);
        System.out.println("NTerms:\n" + theNonTerminals);
        System.out.println("Terms:\n" + theTerminals);
        System.out.println("Start Symbol:\n" + theStartSymbol);
//        System.out.println("Input To be Parsed:\n" + theStringToBeParsed);
    }

    public void printMyGrammar(String tag) {
        System.out.println("==================================\n");
        System.out.println(tag);
        System.out.println("- - - - - - - - -\n");
        for (String sym : theGrammar.keySet()) {
            System.out.print(sym + "\t-->\t");
            for (String prod : theGrammar.get(sym)) {
                System.out.print(prod + " | ");
            }
            System.out.print("\n");
        }
        System.out.println("\n==================================\n");
    }
}
