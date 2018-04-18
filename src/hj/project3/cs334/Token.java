package hj.project3.cs334;


/**
 * Represents a valid 'token' for a
 * MOUSEYCAT program as identified by
 * the Scanner class.
 *
 * @author Helen Lee
 */
class Token {
    String location;
    String type;


    /**
     * Default constructor for empty token.
     * Represents invalid token.
     *
     */
    Token() {
        this(null,null);
    }

    /**
     * Constructor for punctuation and keywords.
     * Because they are not put into the symbol
     * table, the location (reference) parameter
     * is set to null.
     *
     * @param b
     */
    Token(String b) {
        this(null, b);
    }

    /**
     * Constructor for a valid token, used
     * for variables and integers.
     *
     * @param a     reference to location in symbol table (key)
     * @param b     type of token (variable or integer)
     */
    Token(String a, String b) {
        location = a;
        type = b;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String location) {
        this.location = location;
    }


    /**
     * Checks to see if token is invalid.
     *
     */
    public boolean isEmpty() {
        return (location == null && type == null);
    }
}
