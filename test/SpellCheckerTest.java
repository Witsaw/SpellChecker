
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.wilchen.spellchecker.SpellChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashSet;

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