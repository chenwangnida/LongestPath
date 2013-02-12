package yodletriangle;

import org.jgrapht.alg.BellmanFordShortestPath;

/**
 * Contains one method to calculate the longest path in the triangle
 * @author Clark
 */
public class TriangleGraphAlg {
    
    /**
     * 
     * @param g The graph to calculate the longest graph
     * @return the longest path in the graph
     */
    public static int getLongestPath(TriangleGraph g) {
        BellmanFordShortestPath BFSP;
        BFSP = new BellmanFordShortestPath(g, g.getApex());
        double min = 0;
        double temp;
        
        //iterate through every vertex in the bottom of the triangle and find the shortest path
        for (Object v : g.getLastLevel()) {
            temp = BFSP.getCost(v);
            if (min > temp) {
                min = temp;
            }
        }
        
        //the "shortest" path in this graph is the most negative number so take the negative of this to find the true longest path!
        //we also need to add back the first integer
        return (int) (-min + ((GraphInteger) g.getApex()).getValue());
    }
}
