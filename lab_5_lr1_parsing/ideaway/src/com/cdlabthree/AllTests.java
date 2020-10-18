package com.cdlabthree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AllTests {

    @Test
    public void run8() throws FileNotFoundException {

        SubRoutine sr = new SubRoutine();

        List<Boolean> actualResults = Arrays.asList(false, true, false, true, false);
        List<Boolean> results = sr.run("8");

        for (int i = 0; i < actualResults.size(); i++) {
            Assert.assertEquals(results.get(i), actualResults.get(i));
        }
    }

    @Test
    public void run9() throws FileNotFoundException {

        SubRoutine sr = new SubRoutine();

        List<Boolean> actualResults = Arrays.asList(false, true, false, false, false, true, true);
        List<Boolean> results = sr.run("9");

        for (int i = 0; i < actualResults.size(); i++) {
            Assert.assertEquals(results.get(i), actualResults.get(i));
        }
    }

    @Test
    public void run10() throws FileNotFoundException {

        SubRoutine sr = new SubRoutine();

        List<Boolean> actualResults = Arrays.asList(true, false, true, false, false);
        List<Boolean> results = sr.run("10");

        for (int i = 0; i < actualResults.size(); i++) {
            Assert.assertEquals(results.get(i), actualResults.get(i));
        }
    }

    @Test
    public void run11() throws FileNotFoundException {

        SubRoutine sr = new SubRoutine();

        List<Boolean> actualResults = Arrays.asList(true, false, true, true, true, false);
        List<Boolean> results = sr.run("11");

        for (int i = 0; i < actualResults.size(); i++) {
            Assert.assertEquals(results.get(i), actualResults.get(i));
        }
    }


}
