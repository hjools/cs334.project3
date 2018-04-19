package hj.project3.cs334;


/**
 * The SizeNode represents the
 * statement in the format:
 * size i j begin list halt
 *
 * Here, the TNode 'list' may be a
 * SeqNode or a single
 * statement node
 *
 * @author Helen Lee
 */
class SizeNode extends TNode {

    private int width;
    private int height;
    TNode list;

    SizeNode(int i, int j, TNode list) {
        super("size");
        this.width = i;
        this.height = j;
        this.list = list;
    }

    // Basic getter functions follow

    int getWidth() {
        return this.width;
    }

    int getHeight() {
        return this.height;
    }

    TNode getList() {
        return this.list;
    }

}
