/**
 * @author Clark Gredona
 */
package yodletriangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.jgrapht.graph.*;

public final class YodleTriangle {
    
    /**
     * The starting point for the demo.
     *
     * @param args ignored.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File f = new File("triangle.txt");

        TriangleGraph<GraphInteger, DefaultWeightedEdge> testGraph = TriangleGraph.createTriangleGraph(f);
        

        System.out.println("This is a representation of the graph:");
        System.out.println(testGraph);
        
        System.out.println();
        System.out.print("The length of the longest path from top to bottom is ");
        System.out.println(TriangleGraphAlg.getLongestPath(testGraph));;


        // note directed edges are printed as: (<v1>,<v2>)

    }
}
