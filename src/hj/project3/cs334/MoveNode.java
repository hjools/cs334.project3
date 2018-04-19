package hj.project3.cs334;

/**
 * The MoveNode represents the
 * statement in the format:
 * move v i
 *
 * @author Helen Lee
 */
class MoveNode extends TNode {

    private String name;
    private int distance;

    MoveNode(String v) {
        super("move");
        this.name = v;
        this.distance = 1;
    }
    MoveNode(String v, int i) {
        this(v);
        this.distance = i;
    }

    // Basic getter functions follow

    String getName() {
        return this.name;
    }

    int getDistance() {
        return this.distance;
    }
}
