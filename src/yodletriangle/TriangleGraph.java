package yodletriangle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 *
 * @author Clark
 */
public class TriangleGraph<V, E> extends SimpleDirectedWeightedGraph<V, E> implements WeightedGraph<V, E> {

    private V apexVertex;
    private ArrayList<V> lastLevel;
    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new triangle graph with the specified edge
     * factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public TriangleGraph(EdgeFactory<V, E> ef) {
        super(ef);
    }

    /**
     *
     * Creates a new triangle graph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public TriangleGraph(Class<? extends E> edgeClass) {
        this(new ClassBasedEdgeFactory<V, E>(edgeClass));
    }

    /**
     * 
     * @return the apex of the triangle ie the first number
     */
    public V getApex() {
        return apexVertex;
    }
    
    /**
     * 
     * @return all vertices in the last (bottom) level or base of the triangle
     */
    public ArrayList<V> getLastLevel() {
        return lastLevel;
    }
    
    /**
     * 
     * @param f a file of integers in a triangle format
     * @return a graph representing the triangle
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static TriangleGraph<GraphInteger, DefaultWeightedEdge> createTriangleGraph(File f) throws FileNotFoundException, IOException {
        
        //instantiate what is returned
        TriangleGraph<GraphInteger, DefaultWeightedEdge> g;
        g = new TriangleGraph<>(DefaultWeightedEdge.class);

        BufferedReader in;
        String text;
        StringTokenizer tokenizer;
        Integer tempInteger;
        GraphInteger tempGraphInteger = null;
        in = new BufferedReader(new FileReader(f));
        boolean isFirstLevel = true; //used to determine what is the apex
        ArrayList<GraphInteger> currentLevel = new ArrayList<>();
        ArrayList<GraphInteger> previousLevel;
        DefaultWeightedEdge tempEdge;

        int count = 0;
        
        //parse through the file and add every integer as a vertex in the graph
        while (in.ready()) {
            previousLevel = currentLevel;
            currentLevel = new ArrayList<>();
            text = in.readLine();
            tokenizer = new StringTokenizer(text, " ");
            while (tokenizer.hasMoreTokens()) {
                tempInteger = Integer.parseInt(tokenizer.nextToken());
                
                //every integer is IDed by when it was added to the vertex
                //this ensures that every integer is still unique so that a graph can have duplicate integers as its vertices
                tempGraphInteger = new GraphInteger(tempInteger, count++);
                currentLevel.add(tempGraphInteger);

                g.addVertex(tempGraphInteger);


            }

            //if its the first number being added, make a note that its the first node
            if (isFirstLevel) {
                g.apexVertex = tempGraphInteger;
                isFirstLevel = false;
            }

            //connect every vertex in one layer of the triangle to adjacent nodes in the layer after
            int levelIndex = 0;
            for (GraphInteger graphElement : previousLevel) {
                tempEdge = g.addEdge(graphElement, currentLevel.get(levelIndex));
                
                //because we are using the Bellman Ford Shortest Path algorithm, use the negative of the receiver node's value as the edge weight
                //Obviously the shortest path is the most negative!
                g.setEdgeWeight(tempEdge, -currentLevel.get(levelIndex).getValue());

                tempEdge = g.addEdge(graphElement, currentLevel.get(levelIndex + 1));
                g.setEdgeWeight(tempEdge, -currentLevel.get(levelIndex + 1).getValue());

                levelIndex++;
            }
        }

        //make a note of the bottom of the triangle
        g.lastLevel = currentLevel;


        return g;
    }
}
