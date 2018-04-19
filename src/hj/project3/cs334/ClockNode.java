package hj.project3.cs334;

/**
 * The ClockNode represents the
 * statement in the format:
 * clockwise v
 *
 * @author Helen Lee
 */
class ClockNode extends TNode {

    private String name;

    ClockNode(String v) {
        super("clockwise");
        this.name = v;
    }

    // Basic getter functions follow

    String getName() {
        return this.name;
    }
}
