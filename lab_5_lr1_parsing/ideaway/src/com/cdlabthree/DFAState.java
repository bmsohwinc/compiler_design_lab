package com.cdlabthree;

import java.util.*;

public class DFAState {
    public Integer StateId;
    public Set<Item> StateItems;
    public HashMap<String, Integer> NextState;

    DFAState(Integer stateId, Set<Item> stateItems) {
        StateId = stateId;
        StateItems = stateItems;
        NextState = new HashMap<>();
    }

    @Override
    public String toString() {
        List<String> itemToStrings = new ArrayList<>();
        for (Item item : StateItems) {
            itemToStrings.add(item.toString());
        }

        // Sort the item.toStrings for comparison purposes
        Collections.sort(itemToStrings);

//        String temp = String.join(",", itemToStrings);

        return String.join(",", itemToStrings);

//        StringBuilder theState = new StringBuilder();
//        for (String item : itemToStrings) {
//            theState.append(item);
//        }
//
//        return theState.toString();
    }
}
