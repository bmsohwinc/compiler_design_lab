import java.util.*;

class RecursiveDescentParser {
    HashMap<String, List<String>> theGrammar = new HashMap<>();
    String startSymbol;
    String inputString;
    int currentIndex;


    RecursiveDescentParser(HashMap<String, List<String>> theGrammar, String startSymbol, String inputString) {
        this.theGrammar = theGrammar;
        this.startSymbol = startSymbol;
        this.inputString = inputString;
        this.currentIndex = 0;
    }

    public void run() {
        System.out.println("Taking " + startSymbol + " as start symbol and " + inputString + " as inputString");
        if (parse(startSymbol) && (currentIndex == inputString.length())) {            
            System.out.println(">> PARSING SUCCESSFUL <<");
        }
        else {
            System.out.println("// SYNTAX ERROR DETECTED //");
        }
    }

    public boolean parse(String symbol) {
        int prioriIndex = currentIndex;
        boolean epsilonFound = false;
        // choose a production and derive
        for (String aProd : theGrammar.get(symbol)) {
            System.out.println("------- Using prod : " + aProd + " of symbol: " + symbol);
            int initIndex = currentIndex;

            // match each char of that production
            for (int i = 0; i < aProd.length(); i++) {
                String theSymbol = "" + aProd.charAt(i);
                if (theGrammar.containsKey(theSymbol)) {
                    // a Non Terminal
                    if (!parse(theSymbol)) {
                        currentIndex = initIndex;
                        break;
                    }
                }
                else if (theSymbol.equals("e")) {
                    // nothing
                    epsilonFound = true;
                    System.out.println("Making an epsilon transition for symbol: " + symbol + " and aProd: " + aProd );
                }
                else if ((currentIndex < inputString.length()) && (aProd.charAt(i) == inputString.charAt(currentIndex))) {
                    // a Terminal - matched
                    currentIndex++;
                }
                else {
                    // a Terminal - not matched
                    if (currentIndex < inputString.length()) {
                        System.out.println("## Mismatch for aProd: " + aProd + " and inputChar = " + inputString.charAt(currentIndex) + " ... backtrack now!");
                    }
                    else {
                        System.out.println("## Input exhausted for aProd: " + aProd + " and currentIdex: " + currentIndex);
                    }
                    
                    currentIndex = initIndex;
                    break;
                }
            }
        }

        if ((prioriIndex == currentIndex) && (!epsilonFound)) {
            System.out.println("*** None of the productions of symbol " + symbol + " could derive from the index " + currentIndex);
            return false;
        }

        return true;
    }
}


class ProduceLeftFactoredGrammar {
    HashMap<String, List<String>> theGrammar = new HashMap<>();
    Queue<String> avlSymbols = new PriorityQueue<>();

    ProduceLeftFactoredGrammar(HashMap<String, List<String>> theGrammar) {
        this.theGrammar = theGrammar;
        Set<String> temp = new HashSet<>();

        // get all NonTerminals present in the Grammar
        for (String s : theGrammar.keySet()) {
            for (int i = 0; i < s.length(); i++) {
                char x = s.charAt(i);
                if (x >= 'A' && x <= 'Z') {
                    temp.add("" + x);
                }
            }

            for (String aprod : theGrammar.get(s)) {
                for (int i = 0; i < aprod.length(); i++) {
                    char x = aprod.charAt(i);
                    if (x >= 'A' && x <= 'Z') {
                        temp.add("" + x);
                    }
                }
            }
        }

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
                        newSymbolProds.add(newString.length() == 0 ? "_epsilon" : newString);
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
                        newSymbolProds.add(newString.length() == 0 ? "_epsilon" : newString);
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

    public void run() {
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
    }

    public void printGrammar() {
        for (String key : theGrammar.keySet()) {
            System.out.println(key + " --> " + theGrammar.get(key));
        }
    }
}


class RemoveLeftRecursion {
    HashMap<String, List<String>> theGrammar = new HashMap<>();
    
    RemoveLeftRecursion(HashMap<String, List<String>> theGrammar) {
        this.theGrammar = theGrammar;
    }

    public void run() {
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
    }

    public void runbeta() {
        List<String> theKeys =  new ArrayList<>(theGrammar.keySet());
        for (String symbol : theKeys) {
            checkLeftRecursion(symbol);
        }
        System.out.println("\n>> The corrected Grammar...");
        printGrammar();
    }

    public void checkLeftRecursion(String symbol) {

        List<String> lrs = new ArrayList<>();
        List<String> nlrs = new ArrayList<>();

        for (String aProc : theGrammar.get(symbol)) {
            if (aProc.startsWith(symbol)) {
                System.out.println("Left Recursion Detected!" + " Symbol: " + symbol + " Proc: " + aProc);
                lrs.add(aProc.replaceFirst(symbol, "") + symbol + "'");
            }
            else {
                nlrs.add(aProc + symbol + "'");
            }
        }

        if (lrs.size() != 0) {
            lrs.add("e");
            theGrammar.replace(symbol, nlrs);
            theGrammar.put(symbol + "'", lrs);
        }
    }


    public void printGrammar() {
        for (String key : theGrammar.keySet()) {
            System.out.println(key + " --> " + theGrammar.get(key));
        }
    }
}

public class Main {

    public static void main(String[] args) {

        HashMap<String, List<String>> theGrammar = new HashMap<>();

        Scanner sc = new Scanner(System.in);
        System.out.println(">> Enter The Grammar\n");

        while(true) {
            // take symbol
            System.out.print("Enter symbol (-1 to EXIT): ");
            String theSymbol = sc.nextLine();
            if (theSymbol.equals("-1")) {
                break;
            }

            // take productions
            System.out.print("Enter Productions (space separated): ");
            String theProduction = sc.nextLine();
            String[] allProductions = theProduction.split(" ");
            List<String> temp = new ArrayList<>();
            for (String x : allProductions) {
                // if (x.equals("e")) {
                //     x = "";
                // }
                temp.add(x);
            }

            theGrammar.put(theSymbol, temp);

            // ack
            for (String x : allProductions) {
                System.out.println("Prod: " + x);
            }
        }

        System.out.println("\n");

        switch(args[0]) {
            case "1":
                // Q.1
                RemoveLeftRecursion rlr = new RemoveLeftRecursion(theGrammar);
                rlr.runbeta();
            break;
            case "2":
                // Q.2
                ProduceLeftFactoredGrammar plfg = new ProduceLeftFactoredGrammar(theGrammar);
                plfg.run();
            break;
            case "3":
                // Q.3
                System.out.print("Enter start symbol: ");
                String startSymbol = sc.nextLine();
                System.out.print("Enter input String: ");
                String inputString = sc.nextLine();
                RecursiveDescentParser rdp = new RecursiveDescentParser(theGrammar, startSymbol, inputString);
                rdp.run();
            break;
        }
        sc.close();
    }
}
