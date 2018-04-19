package hj.project3.cs334;

/**
 * The HoleNode represents the
 * statement in the format:
 * hole i j
 *
 * @author Helen Lee
 */
class HoleNode extends TNode {

    private int xCoord;
    private int yCoord;

    HoleNode(int i, int j) {
        super("hole");
        this.xCoord = i;
        this.yCoord = j;
    }

    // Basic getter functions follow

    int getxCoord() {
        return this.xCoord;
    }

    int getyCoord() {
        return this.yCoord;
    }
}
