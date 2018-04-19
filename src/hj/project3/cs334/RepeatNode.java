package hj.project3.cs334;

/**
 * The RepeatNode represents the
 * statement in the format:
 * repeat i stmts end
 *
 * @author Helen Lee
 */
class RepeatNode extends TNode {

    private int counter;
    private TNode stmts;

    RepeatNode(int i, TNode s) {
        super("repeat");
        this.counter = i;
        this.stmts = s;
    }

    // Basic getter functions follow

    int getCounter() {
        return this.counter;
    }

    TNode getStmts() {
        return this.stmts;
    }
}
