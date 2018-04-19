package hj.project3.cs334;


/**
 * Represents a custom general Node class for the
 * Syntax Tree of the language MOUSEYCAT
 *
 * @author Helen Lee
 */
class TNode {

    enum Type {
        SIZE,
        CAT,
        MOUSE,
        HOLE,
        SEQ,
        MOVE,
        CLOCKWISE,
        REPEAT,
        GENERAL
    }

    private Type type;

    TNode(String type) {
        setType(type);
    }

    private void setType(String type) {
        switch(type) {
            case "size":
                this.type = Type.SIZE;
                break;
            case "cat":
                this.type = Type.CAT;
                break;
            case "mouse":
                this.type = Type.MOUSE;
                break;
            case "hole":
                this.type = Type.HOLE;
                break;
            case "sequence":
                this.type = Type.SEQ;
                break;
            case "move":
                this.type = Type.MOVE;
                break;
            case "clockwise":
                this.type = Type.CLOCKWISE;
                break;
            case "repeat":
                this.type = Type.REPEAT;
                break;
            case "general":
                this.type = Type.GENERAL;
            default:
                break;
        }
    }

    Type getType() {
        return this.type;
    }

}
