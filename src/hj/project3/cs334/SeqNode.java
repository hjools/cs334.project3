package hj.project3.cs334;

/**
 * The SeqNode represents a
 * list of two statements or
 * syntax trees. The first part
 * should be executed before the second part.
 *
 * @author Helen Lee
 */
class SeqNode extends TNode {

    private TNode first;
    private TNode second;

    SeqNode(TNode one, TNode two) {
        super("sequence");
        this.first = one;
        this.second = two;
    }

    // Basic getter functions follow

    TNode getFirst() {
        return this.first;
    }

    TNode getSecond() {
        return this.second;
    }
}
