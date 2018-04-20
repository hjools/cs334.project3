package hj.project3.cs334;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class that parses a given MOUSEYCAT program,
 * populates a symbol table,
 * and prints out the parsed output
 * as well as any errors during parsing.
 *
 * @author Helen Lee
 */
class Scanner {
    private static String printOutput = "N";
    private static String outputHeading = "OUTPUT FOR PROGRAM ";

    private Keywords keywords;
    private int longest;
    private int programLine;
    private String programName;
    private ArrayList<String[]> parsedProgram;
    private ArrayList<String> errors;
    private ArrayList<Token> output;
    HashMap<String, Integer> symbolTable;

    private int currentToken;
    private ArrayList<String> tokenList;


    /**
     * Default constructor. Provided with the name
     * of the MOUSEYCAT program being scanned,
     * constructs a symbol table
     */
    Scanner(String program) {
        keywords = new Keywords();
        longest = 1;
        programLine = 1;
        currentToken = 0;
        parsedProgram = new ArrayList<String[]>();
        errors = new ArrayList<String>();
        output = new ArrayList<Token>();
        symbolTable = new HashMap<String, Integer>();
        tokenList = new ArrayList<String>();
        scan(program);
    }


    /**
     * Parses a MOUSEYCAT program passed as input
     * and stores the results & errors into two
     * ArrayLists, errors and output
     *
     * @param program   name of MOUSEYCAT file
     */
    private void scan(String program) {
        programName = program;
        String inputLine = null;

        try {
            FileReader reader = new FileReader(programName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            while((inputLine = bufferedReader.readLine()) != null) {

                // removes all leading and trailing whitespaces and leading tabs
                String line = inputLine.trim().replaceFirst("\\t", "");

                // processes tokens on each nonempty program line
                if(!line.isEmpty() || !line.equals("")) {
                    String[] tokens = line.split("\\s+");
                    identifyTokens(tokens);
                }

                programLine++;
            }

            bufferedReader.close();

        } catch(FileNotFoundException e) {
            System.out.println(
                    programName + " not found"
            );
        } catch(IOException e) {
            System.out.println(
                    "Error reading file " + programName
            );
        }
        // end of string marker
        output.add(new Token("variable", "$", "$"));

        if(printOutput == "Y") {
            printResults();
        }

    }


    /**
     * Identifies if the given word is a valid token or not
     * as per specifications of the MOUSEYCAT language, and
     * logs results & errors.
     *
     * @param word      unprocessed word parsed from MOUSEYCAT program
     * @return          a Token populated with reference to the word's
     *                  location in the symbol table and its type, or
     *                  null values indicating the word is a dud
     */
    private Token idToken(String word) {
        // if token is a punctuation or a keyword,
        // just return the type and null value
        if(word.equals(";") || keywords.checkToken(word)) {
            Token v = new Token("keyword", word.toLowerCase(), word);
            output.add(v);
            return v;
        }
        // check if token is an integer
        if(checkInt(word)) {
            // if integer starts with 0, must be length 1
            // otherwise, it's a variable
            if(word.startsWith("0") && word.length() != 1) {
                // create new token
                Token v = new Token("variable", word, word);
                // add to output
                output.add(v);
                // check if already entered in symbol table
                // and if not, add to symbol table
                if(!symbolTable.containsKey(word)) {
                    symbolTable.put(word, 0);
                }
                return v;
            }
            // integer token must be max 3 digits
            // otherwise, it's a variable
            if(word.length() > 3) {
                Token v = new Token("variable", word, word);
                output.add(v);
                if(!symbolTable.containsKey(word)) {
                    symbolTable.put(word, 0);
                }
                return v;
            }
            // otherwise the token is really an integer
            Token v = new Token("integer", word, word);
            output.add(v);
            if(!symbolTable.containsKey(word)) {
                symbolTable.put(word, Integer.parseInt(word));
            }
            return v;
        } // end of checking if token is integer

        // is the token a variable?
        if(checkVar(word)) {
            Token v = new Token("variable", word, word);
            output.add(v);
            if(!symbolTable.containsKey(word)) {
                symbolTable.put(word, 0);
            }
            return v;
        }

        // otherwise this is an invalid token
        // log error and move on
        errors.add(
                "ERROR line " + programLine + ": " + word + " is invalid."
        );
        return new Token();

    }


    /**
     * Parses a line of the MOUSEYCAT program
     * and identifies each potential token.
     *
     * @param tokens    parsed list of a line of the input program
     */
    private void identifyTokens(String[] tokens) {

        // process each token on the line
        for(String token : tokens) {
            // identifying maximum length of token
            // in entire file for later output formatting
            if(token.length() > longest) {
                longest = token.length();
            }

            // skip rest of line if
            // encountering a comment
            if(token.startsWith("//")) {
                return;
            }

            // otherwise identify token
            Token processed = idToken(token);
            // if we got an invalid token
            // attempt re-identification
//            if(processed.isEmpty()) {
//
//            }

        } // end of line processing for loop
    }


    /**
     * Checks to see if given string fits the
     * criteria for variables in the
     * MOUSEYCAT language.
     *
     * @param s     string being checked
     * @return      boolean variable identification
     */
    private boolean checkVar(String s) {

        int digitCounter = 0;
        int letterCounter = 0;
        // are there any nonletter / nondigit symbols?
        for(int i = 0; i < s.length(); i++){
            if(!Character.isLetterOrDigit(s.charAt(i))) {
                return false;
            }
            else if(Character.isDigit(s.charAt(i))) {
                digitCounter++;
            } else {
                letterCounter++;
            }
        }
        // if length is <= 3, contains a digit, and there are no letters
        // this is an invalid variable
        if(s.length() <= 3 && digitCounter > 0 && letterCounter == 0) {
            return false;
        }

        return true;
    }


    /**
     * Checks to see if input string is an integer.
     * Source: https://stackoverflow.com/questions/1486077/good-way-to-encapsulate-integer-parseint
     *
     * @param input     string token
     * @return          whether or not input is integer
     */
    private boolean checkInt(String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( NumberFormatException e ) {
            return false;
        }
    }


    /**
     *
     * Prints out results and errors of
     * the parsed MOUSEYCAT program.
     *
     */
    private void printResults() {
        longest = longest + 5;
        System.out.println(outputHeading + programName);
        System.out.printf(
                "%-14s%-" + longest + "s%-10s\n",
                "TYPE", "CH VALUE", "INT VALUE"
        );

        // first print out each processed token:
        // type, character value, int value
        for(int i = 0; i < output.size(); i++) {
            Token v = output.get(i);
            String type, chVal, intVal;
            type = v.getType();
            switch (type) {
                case "keyword":
                    type = v.getChVals();
                    chVal = "";
                    intVal = Integer.toString(v.getIntVals());
                    break;
                default:
                    chVal = v.getChVals();
                    intVal = Integer.toString(v.getIntVals());
                    break;
            }

            System.out.printf(
                    "%-14s%-" + longest + "s%-10s\n",
                    type, chVal, intVal
            );
        }

        printErrors();

    }


    /**
     * A getter function for the symbol table.
     * Ideally used after the scan() function.
     *
     * @return the symbol table for the program parsed
     */
    HashMap<String, Integer> getSymbolTable() {
        return symbolTable;
    }


    /**
     * Print out errors found while scanning file.
     * If no errors, print "NO ERRORS" to console.
     */
    private void printErrors() {
        if(errors.size() > 0) {
            System.out.println("\n\nERRORS\n");
            for (int i = 0; i < errors.size(); i++) {
                System.out.println(errors.get(i));
            }
        } else {
            System.out.println("NO ERRORS FOUND");
        }
    }


    /**
     * Self-explanatory
     */
    public boolean hasErrors() {
        return errors.size() > 0;
    }


    /**
     * Gives the next valid token of the
     * MOUSEYCAT program scanned.
     *
     * @return      String value of token
     */
    Token next() {
        Token ret = output.get(currentToken);
        currentToken++;
        return ret;
    }


    /**
     * Checks if there are still tokens
     * that need to be put onto parsing stack
     * @return      are we at end of token array
     */
    int ongoing() {
        if (currentToken < output.size()) {
            return 1;
        } else if (currentToken == output.size()) {
            return 0;
        } else return -1;
    }
}