package hj.project3.cs334;

/**
 * The GenNode represents the
 * statement in the format:
 * any terminal
 *
 * @author Helen Lee
 */
class GenNode extends TNode {

    private String value;

    GenNode(String v) {
        super("general");
        this.value = v;
    }

    String getValue() {
        return this.value;
    }

}
