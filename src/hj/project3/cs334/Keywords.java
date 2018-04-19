package hj.project3.cs334;

import java.util.HashSet;

/**
 * A class representing all the keywords
 * in the language MOUSEYCAT. Upon initialization,
 * the class must read in a dictionary file
 *
 * @author Helen Lee
 */
class Keywords {

    private HashSet<String> keywords;

    /**
     * Default constructor
     */
    Keywords() {
        keywords = new HashSet<>();
        populate();
    }


    /**
     * adding all the keywords to the set!!!
     */
    private void populate() {
        keywords.add("begin");
        keywords.add("halt");
        keywords.add("cat");
        keywords.add("mouse");
        keywords.add("clockwise");
        keywords.add("move");
        keywords.add("north");
        keywords.add("south");
        keywords.add("east");
        keywords.add("west");
        keywords.add("hole");
        keywords.add("repeat");
        keywords.add("size");
        keywords.add("end");
    }

    /**
     * Checks for membership in the defined keywords
     *
     * @param token     the string to be checked
     * @return          boolean indicating membership
     */
    public Boolean checkToken(String token) {

        return keywords.contains(token.toLowerCase());

    }

}
