package hj.project3.cs334;


/**
 * Represents a valid 'token' for a
 * MOUSEYCAT program as identified by
 * the Scanner class.
 *
 * @author Helen Lee
 */
class Token {

    enum Type {
        KEYWORD,
        VARIABLE,
        INTEGER,
        ERROR
    }

    String chVals;
    int intVals;
    Type type;
    Keywords keywords;

    Token() {
        this.type = Type.ERROR;
    }

    Token(String type, String chars, String vals) {
        setter(type, chars, vals);
    }

    private void setter(String type, String chars, String vals) {
        switch(type) {
            case "keyword":
                this.type = Type.KEYWORD;
                this.chVals = chars;
                if(chars.equals(";")) {
                    this.intVals = -2;
                } else {
                    this.intVals = -1;
                }
                break;
            case "variable":
                this.type = Type.VARIABLE;
                this.chVals = chars;
                this.intVals = 0;
                break;
            case "integer":
                this.type = Type.INTEGER;
                this.chVals = chars;
                this.intVals = Integer.parseInt(vals);
                break;
            default:
                break;
        }
    }

    // Basic getter functions follow

    String getType() {
        switch(this.type) {
            case KEYWORD:
                return "keyword";
            case VARIABLE:
                return "variable";
            case INTEGER:
                return "integer";
            default:
                return "empty";
        }
    }

    String getChVals() {
        if(this.chVals != null) {
            return this.chVals;
        }
        return "";
    }
    int getIntVals() {
        return this.intVals;
    }

}
