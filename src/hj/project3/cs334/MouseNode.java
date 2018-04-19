package hj.project3.cs334;

/**
 * The MouseNode represents the
 * statement in the format:
 * mouse v i j d
 *
 * @author Helen Lee
 */
class MouseNode extends TNode {

    private String name;
    private int xCoord;
    private int yCoord;
    private String direction;

    MouseNode(String v, int i, int j, String d) {
        super("mouse");
        this.name = v;
        this.xCoord = i;
        this.yCoord = j;
        this.direction = d;
    }

    // Basic getter functions follow

    String getName() {
        return this.name;
    }

    int getxCoord() {
        return this.xCoord;
    }

    int getyCoord() {
        return this.yCoord;
    }

    String getDirection() {
        return this.direction;
    }
}
