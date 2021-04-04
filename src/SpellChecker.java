import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Locale;


public class SpellChecker {

    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private HashSet<String> dictionary;

    public SpellChecker() {
        dictionary = new HashSet<String>();
    }

    /*********************************
     * Name: loadDictionary
     *
     * @param dictFile -- dictionary file
     */
    public void loadDictionary(String dictFile) throws FileNotFoundException, IOException {

        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(dictFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                dictionary.add(strLine);
            }
        } catch (FileNotFoundException e1) {
            System.out.println("Error : Dictionary file " + dictFile + " file not found.");
            throw e1;
        } catch (IOException e2) {
            System.out.println("Error : Error occurred while read " + dictFile + e2.getMessage());
            throw e2;
        } finally {
            if (fstream != null) {
                try {
                    fstream.close();
                } catch (Exception ee) {
                }
            }

        }

    }

    /************************************************
     * Name : generatePossibleWords
     *
     * @param s -- input word
     * @return set of possible words from dictionary
     */
    private HashSet<String> generatePossibleWords(String s) {
        HashSet<String> possibleWords = new HashSet<String>();
        for (int i = 0; i < s.length() - 1; i++) {
            possibleWords.add(s.substring(0, i) + s.substring(i + 1));
            for (int j = 0; j < ALPHABET.length(); j++) {
                // Replacement
                possibleWords.add(s.substring(0, i) + ALPHABET.charAt(j) + s.substring(i + 1));
                // Addition
                possibleWords.add(s.substring(0, i) + ALPHABET.charAt(j) + s.substring(i));
            }
        }
        return possibleWords;
    }

    /*****************************************************************
     * Name : isKnown
     *
     * @param possibleWords -- possible words (need to be confirmed)
     * @return distinct list of confirmed words
     */
    private HashSet<String> isKnown(HashSet<String> possibleWords) {
        HashSet<String> realWords = new HashSet<String>();
        for (String s : possibleWords) {
            if (dictionary.contains(s)) {
                System.out.println("Did you mean " + s + "?");
                realWords.add(s);
            }
        }
        return realWords;
    }

    /************************************************************
     * Name processSpellChecker
     *
     * @param inputFile -- input file requires spelling check
     * @throws FileNotFoundException
     */
    public void processSpellChecker(String inputFile) throws FileNotFoundException, IOException {
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(inputFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String thisLine;
            int row = 1;
            while ((thisLine = br.readLine()) != null) {
                String[] words = thisLine.split("[^a-zA-Z]+");
                for (String word : words) {
                    if (dictionary.contains(word.toLowerCase()) || word.length() == 0) {
                        continue;
                    }
                    System.out.println("Error: " + word + " in row " + row + " is misspelled in line " + thisLine);
                    HashSet<String> possibleWords = generatePossibleWords(word);
                    HashSet<String> concurrentModificationExceptionAvoider = new HashSet<>();
                    for (String s : possibleWords) {
                        concurrentModificationExceptionAvoider.addAll(generatePossibleWords(s));
                    }
                    possibleWords = isKnown(possibleWords);
                    possibleWords.addAll(isKnown(concurrentModificationExceptionAvoider));
                    if (possibleWords.isEmpty()) {
                        System.out.println("Word was not found in the dictionary.");
                    }
                }
                row++;
            }
        } catch (FileNotFoundException e1) {
            System.out.println("Error : Dictionary file " + inputFile + " file not found.");
            throw e1;
        } catch (IOException e2) {
            System.out.println("Error : Error occurred while reading " + inputFile + e2.getMessage());
            throw e2;
        } finally {
            if (fstream != null) {
                try {
                    fstream.close();
                } catch (Exception ee) {
                }
            }

        }
    }

    public static void help(){
        System.out.println("Usage: SpellChecker dictionFile inputFile");
        System.out.println("     dictionFile -- dictionary absolute path of file ");
        System.out.println("     inputFile -- input file requires a spelling check.");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            help();
            return;
        }
        SpellChecker spellChecker = new SpellChecker();
        try {
            spellChecker.loadDictionary(args[0]);
            spellChecker.processSpellChecker(args[1]);
        } catch (Exception e) {
            System.out.println("Please fix the error and run it again!");
        }

    }

}
