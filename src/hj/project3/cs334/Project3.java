package hj.project3.cs334;

public class Project3 {

    public static void main(String[] args) {
	    // prompt user input for program name
        java.util.Scanner in = new java.util.Scanner(System.in);
        String programName;
        while(true){
            System.out.println("Enter the name of the MOUSEYCAT program to test.");
            programName = in.next();
            if(programName.endsWith(".mc")) {
                break;
            }
            System.out.print("This program only takes MOUSEYCAT files as input. ");
        }

        // start parsing
        Parser parser = new Parser(programName);
        Boolean parsedCorrectly = parser.parse();
        if(parsedCorrectly) {
            parser.runProgram();
        }

    }
}
