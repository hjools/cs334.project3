package hj.project3.cs334;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

/**
 * An SLR(1) parser for the MOUSEYCAT program
 * that produces rightmost derivations.
 *
 * @author Helen Lee
 */
class Parser {
    private static String ERRORMESSAGE = "This program has syntactical errors.";
    private static String PASSMESSAGE = "This program is syntactically correct.\n" +
            "The following production rules are used to derive this program:";
    private static String printPTABLE = "N";
    private static String parseTableFile = "parsedata.txt";
    private static String grammarFile = "grammar.txt";

    private ArrayList<ArrayList<String>> parseTable;
    private HashMap<String, Integer> parseTableHeader;
    private ArrayList<String> pHeader;

    private ArrayList<ArrayList<String>> grammar;

    private Scanner scanner;
    private HashMap<String, Integer> symbolTable;


    /**
     * Default constructor
     */
    Parser(String program) {
        createParseTable();
        writeGrammar();
        scanner = new Scanner(program);
        symbolTable = scanner.getSymbolTable();

    }

    /**
     * After parsing a text file specified by the static
     * variable 'parseTableFile', constructs a 37x21 nested
     * ArrayList SLR(1) parse table for further use by
     * the parser.
     */
    private void createParseTable() {
        parseTableHeader = new HashMap<String, Integer>();
        parseTable = new ArrayList<ArrayList<String>>();
        pHeader = new ArrayList<String>();

        String inputLine = null;
        int fileLine = 1;
        int headerCount = 0;

        try {
            FileReader reader = new FileReader(parseTableFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            while((inputLine = bufferedReader.readLine()) != null) {

                // removes all leading and trailing whitespaces and leading tabs
                String line = inputLine.trim().replaceFirst("\\t", "");

                // these are header rows for terminals and variables
                if(fileLine == 1 || fileLine == 40) {
                    String[] cells = line.replaceFirst("&", "").split("&");
                    for(String cell : cells) {
//                        System.out.println(cell);
                        if(!parseTableHeader.containsKey(cell) && cell != "") {
                            parseTableHeader.put(cell, headerCount);
//                            System.out.println(headerCount);
                            pHeader.add(cell);
                            headerCount++;
                        }
                    }
                }
                else {
                    String[] parsedRow = line.split("&",-1);
                    // get actual SLR parse table's row number
                    int row = Integer.parseInt(parsedRow[0]);

//                    System.out.println("Parsing row number \t\t" + row);

                    // create a new Array that excludes the row number column
                    String[] tempRow = Arrays.copyOfRange(parsedRow, 1, parsedRow.length);
                    // remove null / empty entries from Array
                    replaceBlanks(tempRow);
                    // convert to ArrayList for easier handling
                    ArrayList<String> cells = new ArrayList<>(Arrays.asList(tempRow));

//                    System.out.print("Parsed row content \t\t");
//                    printArrayList(cells);
//                    System.out.println();

                    // add above ArrayList to the table at the correct row number.
                    // because there's two parts to the parse table's row, only
                    // add right away if we haven't added anything before
                    // otherwise combine the previous entry with this one to create
                    // complete entry for this row
                    try {
                        ArrayList<String> current = parseTable.get(row);

//                        System.out.print("Contents of row " + row + "\t\t");
//                        printArrayList(current);
//                        System.out.println();

                        // temporary ArrayList to hold previous entry and new entry
                        ArrayList<String> temp = new ArrayList<String>();
                        temp.addAll(current);
                        temp.addAll(cells);

//                        System.out.print("New combined row \t\t");
//                        printArrayList(temp);
//                        System.out.println();

                        // add combined ArrayList to the parse table
                        parseTable.set(row, temp);
                    } catch (IndexOutOfBoundsException e) {
                        parseTable.add(row, cells);
                    }

//                    System.out.print("Final row \t\t\t\t");
//                    printArrayList(parseTable.get(row));
//                    System.out.println();
//                    System.out.println();

                }

                fileLine++;

            }
            if(printPTABLE == "Y") {
                printTable();
            }
            bufferedReader.close();

        } catch(FileNotFoundException e) {
            System.out.println(
                    parseTableFile + " not found"
            );
        } catch(IOException e) {
            System.out.println(
                    "Error reading file " + parseTableFile
            );
        }

    }

    /**
     * Goes through the parsed String array and replaces
     * null / empty strings with the character 'b'
     * to denote a blank.
     *
     * @param row   a String array that may contain null / empty entries
     */
    private void replaceBlanks(String[] row) {
        for(int i = 0; i < row.length; i++) {
            if(row[i] == null || row[i].isEmpty()) {
                row[i] = "b";
            }
        }
    }


    /**
     * Reads in grammar for MOUSEYCAT language
     * from file specified by static variable 'grammarFile'
     * and populates an ArrayList called 'grammar'.
     *
     */
    private void writeGrammar() {
        grammar = new ArrayList<ArrayList<String>>();

        int programLine = 0;
        String inputLine = null;
        try {
            FileReader reader = new FileReader(grammarFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            while((inputLine = bufferedReader.readLine()) != null) {

                // removes all leading and trailing whitespaces and leading tabs
                String line = inputLine.trim().replaceFirst("\\t", "");

                // processes a production rule on each nonempty program line
                if(!line.isEmpty() || !line.equals("")) {
                    String[] tokens = line.split("\\s+");
                    grammar.add(new ArrayList<String>(Arrays.asList(tokens)));
                }

                programLine++;
            }

            bufferedReader.close();

        } catch(FileNotFoundException e) {
            System.out.println(
                    grammarFile + " not found"
            );
        } catch(IOException e) {
            System.out.println(
                    "Error reading file " + grammarFile
            );
        }

        // check to see if grammar correctly read from file
//        for(int i = 0; i < grammar.size(); i++) {
//            printArrayList(grammar.get(i));
//            System.out.println();
//        }
    }


    /**
     * Performs a rightmost derivation on the
     * MOUSEYCAT program given to the Parser object.
     * Prints out productions used if program is
     * syntactically correct. Otherwise displays
     * error message.
     *
     */
    void parse() {
        // if errors during initial scanning
        // of program, immediately exit.
        if(scanner.hasErrors()) {
            System.out.println(ERRORMESSAGE);
            return;
        }

        Stack<Integer> rStack = new Stack<Integer>(); // keeps track of production rules
        Stack<String> pStack = new Stack<String>(); // parsing stack
        Stack<TNode> STstack = new Stack<TNode>(); // syntax tree stack

        // pushing first production rule onto stack
        pStack.push("0");
        Token v_token = scanner.next();
        String token = tokenToGrammar(v_token);
        boolean newToken = false;

        int row = 0;
        int col = 0;

        // push all tokens of program onto
        // parsing stack before parsing
        while(scanner.ongoing()) {
            // examine top of stack
            String top = pStack.peek();
//            System.out.println("top of stack " + top);
            // retrieve next valid token of program
            if(newToken) {
                v_token = scanner.next();
//                while(v_token.getIntVals() == -2) {
//                    v_token = scanner.next();
//                }
                token = tokenToGrammar(v_token);
                col = parseTableHeader.get(token);
            }
//            System.out.print("lookahead " + token +", ");

            try {
                // is the top of the stack a row number?
                row = Integer.parseInt(top);
                // get value of cell in parse table
                String cell = parseTable.get(row).get(col);
//                System.out.println("cell value " + cell);
                String[] cellParts = cell.split("");

                // if cell value is 'b', this is
                // an error and program is not
                // syntactically correct
                if(cellParts[0].equals("b")) {
                    System.out.println(ERRORMESSAGE);
                    return;
                }

                // if the cell entry is a shift
                else if(cellParts[0].equals("s")) {
                    // push the lookahead on stack
                    pStack.push(token);

                    // set the shift value as current row
                    row = Integer.parseInt(
                            String.join("", Arrays.copyOfRange(cellParts, 1, cellParts.length))
                    );
//                    System.out.println("new row " + row);

                    // push row value to pStack
                    pStack.push(Integer.toString(row));

                    // set newToken param
                    newToken = true;
                }

                // if cell entry is a reduce
                else if(cellParts[0].equals("r")) {
                    // first pop off the current row number
                    pStack.pop();

                    // pop right side of production
                    // number off stack
                    int prodIdx = Integer.parseInt(
                            String.join("", Arrays.copyOfRange(cellParts, 1, cellParts.length))
                    );
//                    System.out.println("production number " + prodIdx);

                    // get the production rule we are reducing by
                    ArrayList<String> production = grammar.get(prodIdx);

                    // put all elements of the right side of
                    // production rule onto a new stack so
                    // we can keep track of them while
                    // popping off the actual parsing stack
                    Stack<String> rules = new Stack<String>();
                    for(int i = 1; i < production.size(); i++) {
                        rules.push(production.get(i));
                    }

                    // now pop off right side of
                    // production from parsing stack
                    while(!rules.empty()){
                        String t = pStack.pop();
                        if(t.equals(rules.peek())) {
                            rules.pop();
                        }
                    }

                    // push production number to rStack
                    rStack.push(prodIdx);

                    // check what current top of pStack is
                    // to check for next row
                    row = Integer.parseInt(pStack.peek());

                    // push left side of production
                    // onto stack
                    pStack.push(production.get(0));

                    // identify column of the left side of production
                    col = parseTableHeader.get(pStack.peek());

                    // set new row number
                    row = Integer.parseInt(parseTable.get(row).get(col));
//                    System.out.print("new row " + row + ", ");

                    // set new col number
                    col = parseTableHeader.get(token);
//                    System.out.println("new col " + col);

                    // push row value to pStack
                    pStack.push(Integer.toString(row));

                    // set newToken param
                    newToken = false;

                }

                // accept
                else if(cellParts[0].equals("a")) {
                    rStack.push(1);
                    break;
                }

            } catch (NumberFormatException e) {

            }

        }

        System.out.println(PASSMESSAGE);

        // Prints out the production rules used to derive program.
        while(!rStack.isEmpty()) {
            int idx = rStack.pop();
            ArrayList<String> production = grammar.get(idx);
            System.out.print(production.get(0) + " -> ");
            for(int i = 1; i < production.size(); i++) {
                System.out.print(production.get(i) + " ");
            }
            System.out.println();
        }


    }


    /**
     * Converts full MOUSEYCAT token into
     * abbreviated version for use with the
     * parse table
     *
     * @param v     Token being processed
     * @return      String, one letter
     */
    private String tokenToGrammar(Token v) {
        String type = v.getType();

        if(type.equals("keyword")) {
            type = v.getChVals();
        }

        switch (type) {
            case "begin":
                return "b";
            case "halt":
                return "t";
            case "cat":
                return "c";
            case "mouse":
                return "m";
            case "clockwise":
                return "l";
            case "move":
                return "o";
            case "north":
                return "n";
            case "south":
                return "s";
            case "east":
                return "e";
            case "west":
                return "w";
            case "hole":
                return "h";
            case "repeat":
                return "r";
            case "size":
                return "z";
            case "end":
                return "d";
            case "integer":
                return "i";
            case "variable":
                return "v";
            default:
                return type;
        }
    }


    /**
     * Prints out the lookaheads & variables to console.
     * For debugging.
     *
     */
    private void printHeader () {
        System.out.println("COMPLETED HEADER PARSING");
        System.out.println("________________________");
        for(String header : parseTableHeader.keySet()) {
            System.out.print(header);
            System.out.println(parseTableHeader.get(header));
        }
    }

    /**
     * Prints out the complete SLR(1) parse table to console.
     * For debugging.
     *
     */
    private void printTable() {
        System.out.println("COMPLETED SLR PARSE TABLE");
        System.out.println("_________________________");
        System.out.print("\t|");
        // print header
        for(int i = 0; i < pHeader.size(); i++) {
            pHeader.get(i);
            System.out.print("\t" + i + "\t|");
        }
        System.out.println();
        // print body
        for(int i = 0; i < parseTable.size(); i++) {
            System.out.print(i + "\t|");
            for(int j = 0; j < parseTable.get(i).size(); j++) {
                System.out.print("\t" + parseTable.get(i).get(j) + "\t|");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("END OF SLR PARSE TABLE");
        System.out.println();
    }

    /**
     * Prints out the contents of an ArrayList to console.
     * For debugging.
     * @param printThis     ArrayList of strings
     */
    private void printArrayList(ArrayList<String> printThis) {
        for(int i = 0; i < printThis.size(); i++) {
            System.out.print(printThis.get(i) + "\t");
        }

    }

}
