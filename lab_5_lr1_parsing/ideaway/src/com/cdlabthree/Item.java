package com.cdlabthree;

import java.util.*;

public class Item {
    public String Header;
    public String Production;
    public Set<String> LookAheads;

    Item(Item item) {
        Header = item.Header;
        Production = item.Production;
        LookAheads = item.LookAheads;
    }

    Item(String header, String production, Set<String> lookAheads) {
        Header = header;
        Production = production;
        LookAheads = lookAheads;
    }

    @Override
    public String toString() {
        List<String> allLookAheads = new ArrayList<>(LookAheads);
        Collections.sort(allLookAheads);
        return Header + "|" + Production + "|" + String.join(",", allLookAheads) + "|";
    }
}
