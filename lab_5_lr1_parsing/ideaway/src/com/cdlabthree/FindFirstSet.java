package com.cdlabthree;

import java.util.*;

public class FindFirstSet {

    HashMap<String, List<String>> theGrammar;
    List<String> theNonTerminals;
    List<String> theTerminals;
    HashMap<String, List<String>> theFirstSet;
//    HashMap<String, Set<String>> aTempFirstSet;
    HashMap<String, Boolean> visited;

    FindFirstSet(HashMap<String, List<String>> theGrammar, List<String> theNonTerminals, List<String> theTerminals) {
        this.theGrammar = theGrammar;
        this.theNonTerminals = theNonTerminals;
        this.theTerminals = theTerminals;
        this.theFirstSet = new HashMap<>();
//        this.aTempFirstSet = new HashMap<>();
        this.visited = new HashMap<>();
    }

    public HashMap<String, List<String>> run(String theStartSymbol) {

        for (String nterm : theNonTerminals) {
            // initialize with empty Set
//            aTempFirstSet.put(nterm, new HashSet<>());
            theFirstSet.put(nterm, new ArrayList<>());
        }

        for (String nterm : theNonTerminals) {
//            System.out.println("FF: Checking for symbol: " + nterm);
            // reset the visited nodes
            resetVisited();
            // get the Firsts
//            Set<String> temp = getFirsts(nterm);
//            // Insert into the answer list
//            theFirstSet.put(nterm, new ArrayList<>(temp));
//            // replace the tempset
//            aTempFirstSet.put(nterm, temp);
            // ack
            getFirsts(nterm);
//            printFirstSets(nterm);
        }

        return theFirstSet;
    }

    public Set<String> getFirsts(String sym) {

//        System.out.println("\tSub symbol: " + sym);

//        // check if this sym already has a First Set () and return
//        if ((aTempFirstSet.get(sym).size() > 0) || (visited.get(sym))) {
//            System.out.println("\t\t\t(1) Returning for symbol " + sym + ": " + aTempFirstSet.get(sym));
//            return aTempFirstSet.get(sym);
//        }

        // check if this sym already has a First Set () and return
        if ((theFirstSet.get(sym).size() > 0) || (visited.get(sym))) {
//            System.out.println("\t\t\t(1) Returning for symbol " + sym + ": " + theFirstSet.get(sym));
            return new HashSet<>(theFirstSet.get(sym));
        }

//        // else, check if it was already visited
//        if (visited.get(sym)) {
//            return
//        }

        // set the current symbol as a visited one
        visited.replace(sym, true);

        Set<String> temp = new HashSet<>();
        for (String prod : theGrammar.get(sym)) {

//            System.out.println("\t\t(" + sym + ") Checking prod: " + prod);

            // epsilon found as a production... add and move on
            if (prod.equals("#")) {
                temp.add("#");
//                System.out.println("\t\t\t(#) was found.. added to temp: " + temp);
            }
            else {
                // first term of the production
                String frontal = Character.toString(prod.charAt(0));

                // terminal found as the first term... add and continue
                if (theTerminals.contains(frontal)) {
                    temp.add(frontal);
//                    System.out.println("\t\t\tTerminal was found.. added to temp: " + temp);
                }
                else {
                    // non terminal found as the first term...
                    int prodLength = prod.length();
                    Set<String> xtraTemp = new HashSet<>();
                    boolean allHaveEpsilon = true;
                    // loop thru the elements of the production
                    for (int i = 0; i < prodLength; i++) {
                        String elementInProd = Character.toString(prod.charAt(i));
                        if (theTerminals.contains(elementInProd)) {
                            // the production element is a Terminal, so just add and break
                            allHaveEpsilon = false;
                            xtraTemp.add(elementInProd);
                            break;
                        }
                        else {
                            // the production element is a Non Terminal, so do recursive call
                            Set<String> firstsOfNonTerms = getFirsts(elementInProd);
                            if (!firstsOfNonTerms.contains("#")) {
                                allHaveEpsilon = false;
                                xtraTemp.addAll(firstsOfNonTerms);
                                break;
                            }
                            else {
                                firstsOfNonTerms.remove("#");
                                xtraTemp.addAll(firstsOfNonTerms);
                            }
                        }
                    }

                    // combine Firsts of all the elements in the production
                    temp.addAll(xtraTemp);

                    // add epsilon if all productions have the epsilon in them
                    if (allHaveEpsilon) {
                        temp.add("#");
//                        System.out.println("\t\t\t(#) was found in all elements of the Prod " + prod + ".. added to temp: " + temp);
                    }
                }
            }
//            System.out.println("\t\t..(" + sym + ") Done prod: " + prod + ". And temp: " + temp);
        }

//        System.out.println("\t\t\t++++ Will put this: " + temp + " to sym: " + sym);
//        aTempFirstSet.put(sym, temp);
        theFirstSet.put(sym, new ArrayList<>(temp));
//        System.out.println("\t\t\t(2) Returning for symbol " + sym + ": " + aTempFirstSet.get(sym));
//        System.out.println("\t\t\t(2) Returning for symbol " + sym + ": " + theFirstSet.get(sym));
//        return aTempFirstSet.get(sym);
        return new HashSet<>(theFirstSet.get(sym));
    }

    public void resetVisited() {
        for (String x : theNonTerminals) {
            visited.put(x, false);
        }
    }

    public void printFirstSets(String sym) {
        System.out.println("---------------------------------\nTHE FIRSTSET and ATEMPFIRSTS After symbol: " + sym);
        for (String nterm : theFirstSet.keySet()) {
//            System.out.println(nterm + ":\t" + theFirstSet.get(nterm) + "\t" + aTempFirstSet.get(nterm));
            System.out.println(nterm + ":\t" + theFirstSet.get(nterm));
        }
        System.out.println("---------------------------------");
    }
}
