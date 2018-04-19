package hj.project3.cs334;

/**
 * The CatNode represents the
 * statement in the format:
 * cat v i j d
 *
 * @author Helen Lee
 */
class CatNode extends TNode {

    private String name;
    private int xCoord;
    private int yCoord;
    private String direction;

    CatNode(String v, int i, int j, String d) {
        super("cat");
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
