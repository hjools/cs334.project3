package hj.project3.cs334;


/**
 * Represents a custom general Node class for the
 * Syntax Tree of the language MOUSEYCAT
 *
 * @author Helen Lee
 */
class TNode {

    private SNType type;
    private Token token;

    TNode(String type) {
        setType(type);
    }
    TNode(String type, Token t) {
        setType(type);
        this.token = t;
    }

    private void setType(String type) {
        switch(type) {
            case "size":
                this.type = SNType.SIZE;
                break;
            case "cat":
                this.type = SNType.CAT;
                break;
            case "mouse":
                this.type = SNType.MOUSE;
                break;
            case "hole":
                this.type = SNType.HOLE;
                break;
            case "sequence":
                this.type = SNType.SEQ;
                break;
            case "move":
                this.type = SNType.MOVE;
                break;
            case "clockwise":
                this.type = SNType.CLOCKWISE;
                break;
            case "repeat":
                this.type = SNType.REPEAT;
                break;
            case "general":
                this.type = SNType.GENERAL;
            default:
                break;
        }
    }

    SNType getType() { return this.type; }

    Token getToken() {
        return this.token;
    }

}
