package com.wilchen.spellchecker;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpellCheckerTest {

    private SpellChecker spellChecker;

    @BeforeEach
    public void setUp() throws Exception {
        spellChecker = new SpellChecker();
    }
    @Test
    public void SpellCheckerPresentTest(){
        assertNotNull(spellChecker);
    }

}