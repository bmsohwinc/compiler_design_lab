package com.cdlabthree;

import java.util.*;
import java.util.logging.Logger;

public class DFABuilder {

    HashMap<String, List<String>> theGrammar;
    List<String> theNonTerminals;
    List<String> theTerminals;
    HashMap<String, List<String>> theFirstSet;

    HashMap<Integer, List<Integer>> theDFAGraph; // links diff states by their ids
    HashMap<Integer, DFAState> theIdToState; // map state_id to state
    HashMap<String, Integer> theStatesSoFar; // map state_as_string to state_id
    Queue<DFAState> theStateQueue; // control state growth


    public DFABuilder(HashMap<String, List<String>> theGrammar, List<String> theNonTerminals, List<String> theTerminals, HashMap<String, List<String>> theFirstSet) {
        this.theGrammar = theGrammar;
        this.theNonTerminals = theNonTerminals;
        this.theTerminals = theTerminals;
        this.theFirstSet = theFirstSet;
        this.theDFAGraph = new HashMap<>();
        this.theIdToState = new HashMap<>();
        this.theStateQueue = new LinkedList<>();
        this.theStatesSoFar = new HashMap<>();
    }

    public void betarun(Set<Item> seedItem) {
//    public void run(Set<Item> seedItem) {
        System.out.println("Seed Item: " + seedItem);

        Integer stateId = 0;
        DFAState seedState = new DFAState(stateId, getClosure(seedItem));
        theStateQueue.add(seedState);
        theIdToState.put(0, seedState);
        theStatesSoFar.put(seedState.toString(), stateId);

        System.out.println("The State: ");
        System.out.println(seedState);
        System.out.println("----");
        System.out.println("Queue len: " + theStateQueue.size());
        theStateQueue.remove();
        System.out.println("Queue len: " + theStateQueue.size());
        System.out.println("----");
    }

//    public void betarun(Set<Item> seedItem) {
    public HashMap<Integer, DFAState> run(Set<Item> seedItem) {
        // state id
        // prods
        // nxt set is calculated in the goto func

        System.out.println("Seed Item: " + seedItem);

        Integer stateId = 0;
        DFAState seedState = new DFAState(stateId, getClosure(seedItem));
        theStateQueue.add(seedState);
        theIdToState.put(0, seedState);
        theStatesSoFar.put(seedState.toString(), stateId);

        while (!theStateQueue.isEmpty()) {
            DFAState topState = theStateQueue.remove();
            Set<Item> theItems = topState.StateItems;
            HashMap<String, Set<Item>> nxtEleMap = new HashMap<>();

            System.out.println("* Checking state #" + topState.StateId + ": " + topState);
            for (Item item : theItems) {
                String prod = item.Production;
                int dotPosition = prod.indexOf('.');
                int prodLength = prod.length();

                System.out.println("\tItem: " + item);

                // if dot is not the last element
                if (dotPosition < prodLength - 1) {
                    // get the next element after dot
                    String nxtElem = Character.toString(prod.charAt(dotPosition + 1));
                    // move the dot one step to right
                    Item newItem = new Item(item);
                    newItem.Production = prod.substring(0, dotPosition) + nxtElem + "." + prod.substring(dotPosition + 2);
                    System.out.println("\t\t... The GOTO: Before: " + prod + " and After: " + newItem.Production);
                    // add this modified Item to the nxtElem Map
                    if (nxtEleMap.containsKey(nxtElem)) {
                        // this nxtElem was already found
                        Set<Item> prevItems = nxtEleMap.get(nxtElem);
                        prevItems.add(newItem);
                        nxtEleMap.put(nxtElem, prevItems);
                        System.out.println("\t\tAdded (" + nxtElem + ", " + prevItems + ") to the nxtEleMap");
                    }
                    else {
                        // this nxtElem was freshly found
                        Set<Item> tempSetOfOneItem = new HashSet<>();
                        tempSetOfOneItem.add(newItem);
                        nxtEleMap.put(nxtElem, tempSetOfOneItem);
                        System.out.println("\t\tAdded (" + nxtElem + ", " + tempSetOfOneItem + ") to the nxtEleMap");
                    }
                }
            }

            System.out.println("\t-- Completed grouping the Items based on nextElement");

            // With the nxtEleMap map, we can discover new state, if any
            for (String nxtElem : nxtEleMap.keySet()) {
                // get all the items of the potential state
                Set<Item> itemsAfterClosure = getClosure(nxtEleMap.get(nxtElem));
                // assign a unique id for the potential state
                stateId++;
                // create the state
                DFAState potentialState = new DFAState(stateId, itemsAfterClosure);
                String stateItemsAsAString = potentialState.toString();

                System.out.println("\t\tNxt Elem: " + nxtElem + " and Created state: " + potentialState);

                // check if the potential state is a duplicate one
                if (theStatesSoFar.containsKey(stateItemsAsAString)) {
                    stateId--;
                    // here update the nextSet of the topState;
                    Integer nxtStateId = theStatesSoFar.get(stateItemsAsAString);
                    topState.NextState.put(nxtElem, nxtStateId);
                    System.out.println("\t\t\t! Duplicate with state# : " + nxtStateId);
                }
                else {
                    // preserve this as the new state explored.
                    theStateQueue.add(potentialState);
                    // update the nextSet of the topState;
                    topState.NextState.put(nxtElem, stateId);
                    // add this new State to the StatesSoFar map and the Id Map
                    theStatesSoFar.put(stateItemsAsAString, stateId);
//                    theIdToState.put(stateId, potentialState);
                    System.out.println("\t\t\tFresh State #" + stateId + " added to Queue");
                }
//                System.out.println("Yo... Howdddddy??");
            }

            // update the original state too (as the NextState parameter was updated)
            theIdToState.put(topState.StateId, topState);
            System.out.println("\t\t. Updated the topState #" + topState.StateId + ":" + topState + " to add NextState: " + topState.NextState);
        }

        printTheDFA();

        return theIdToState;
    }

    public Set<Item> getClosure(Set<Item> stateItems) {
        Set<Item> justACopy = new HashSet<>(stateItems);
        HashMap<String, Boolean> visitedItems = new HashMap<>();
        // while 1
        //  for each item,
            // mark it visited in the Map
            // get the position of `.`
                // if front-of is NTerm
                    // get new LookAhead from Firsts
                    // for every prod of NTerm
                        // create new Item with this Header, Prod, NewLookAhead
                        // if this is already in the visitedItems, continue
                        // else, add to the new list of items
        // if no new items were found, break
        // else,
            // add this to the original set of stateItems
            // loop again to see if new Items will be found from this

        while (true) {
            Set<Item> newItemSet = new HashSet<>();
            for (Item anItem : stateItems) {
                if ( (visitedItems.get(anItem.toString()) != null) && (visitedItems.get(anItem.toString())) ) {
                    continue;
                }

                visitedItems.put(anItem.toString(), true);
                int dotPosition = anItem.Production.indexOf('.');
                int itemProdLength = anItem.Production.length();

                // if the dot is not the last element
                if (dotPosition < itemProdLength - 1) {
                    String nextElement = Character.toString(anItem.Production.charAt(dotPosition + 1));
                    // if the next element is a Non Terminal
                    if (theNonTerminals.contains(nextElement)) {
                        // Get the new LookAheads
                        Set<String> newLookAheads = new HashSet<>();
                        if (dotPosition < itemProdLength - 2) {
                            String posterior = anItem.Production.substring(dotPosition + 2);
                            for (String s : anItem.LookAheads) {
                                newLookAheads.addAll(getFirsts(posterior + s));
                            }
                        }
                        else {
                            newLookAheads = anItem.LookAheads;
                        }

                        // for each production, create an Item and check
                        for (String prod : theGrammar.get(nextElement)) {
                            if (prod.equals("#")) {
                                prod = "";
                            }
                            Item tempItem = new Item(nextElement, "." + prod, newLookAheads);
                            if (!visitedItems.containsKey(tempItem.toString())) {
                                visitedItems.put(tempItem.toString(), false);
                                newItemSet.add(tempItem);
                            }
                        }
                    }
                }
            }

            if (newItemSet.size() == 0) {
                // No new items were found, break
                break;
            }
            else {
                stateItems.addAll(newItemSet);
            }
        }


        System.out.println(">>> Closure: ");
        System.out.println("\tBefore: " + justACopy);
        System.out.println("\tAfter:  " + stateItems);
        System.out.println("<<< ");
        return stateItems;
    }

    public Set<String> getFirsts(String seed) {
        Set<String> answer = new HashSet<>();
        String priori = Character.toString(seed.charAt(0));
        if (theTerminals.contains(priori)) {
            answer.add(priori);
        }
        else {
            boolean allHaveEpsilon = true;
            for (int i = 0; i < seed.length(); i++) {
                String thatElement = Character.toString(seed.charAt(i));
                if (theTerminals.contains(thatElement)) {
                    allHaveEpsilon = false;
                    answer.add(thatElement);
                    break;
                }
                else {
                    Set<String> currFirst = new HashSet<>(theFirstSet.get(thatElement));
                    if (currFirst.contains("#")) {
                        currFirst.remove("#");
                        answer.addAll(currFirst);
                    }
                    else {
                        allHaveEpsilon = false;
                        answer.addAll(currFirst);
                        break;
                    }
                }
            }

            if (allHaveEpsilon) {
                answer.add("#");
            }
        }

        return answer;
    }

//    public int performGoto(DFAState seedState, int stateId) {
//
//    }

    public void printTheDFA()  {
        System.out.println("\n==============================================\nTHE DFA CONSTRUCTED:\n");

        for (Integer stateId : theIdToState.keySet()) {
            System.out.println("State ID: " + stateId);
            DFAState theState = theIdToState.get(stateId);
            int rough = 0;
            for (Item item : theState.StateItems) {
                System.out.println("\tItem #" + (rough++) + ": " + item.Header + " -> " + item.Production + " | " + item.LookAheads);
            }
            System.out.println("\tNext: " + theState.NextState);
            System.out.println("\t------------------------------");
        }
        System.out.println("==============================================");
    }
}
