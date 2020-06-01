package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Label;
import java.util.Collections;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Arc;
import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data); //Getting the data
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData(); 
        ShortestPathSolution solution = null; //Declaration of output solution
        Graph graph = data.getGraph();
        List<Node> nodes = graph.getNodes(); //Nodes list we'll use
        final int nbNodes = graph.size();
        
        int index_origin = data.getOrigin().getId(); //Origin and destination Nodes
        int index_dest = data.getDestination().getId();
        notifyOriginProcessed(data.getOrigin()); //Proper package implementation
        
        //Initiation
        ArrayList<Label> labels = new ArrayList<Label>();
        BinaryHeap<Label> tas = new BinaryHeap<Label>(); //The Label BinaryHeap we'll be using
        
        for(int i=0; i<nbNodes; i++) {
        	labels.add(new Label(nodes.get(i))); //Initiation of all labels in the List
        }
        labels.get(index_origin).Cost = 0; //The origin has a Cost of 0
        tas.insert(labels.get(index_origin)); //And it's the first element in the Heap
        
        //Iterations
        while(!labels.get(index_dest).Mark && tas.size() != 0) { //As long as we haven't reached the end or still have Nodes
        	Label min = tas.deleteMin(); //We use deleteMin from BinaryHeap to get the Label with the lowest Cost
        	labels.get(min.Current_Node.getId()).Mark = true; //We mark that Label as true
        	
        	System.out.println("Current minLabel's Cost: "+min.getCost());
        	System.out.println("Current minLabel's amount of Successors: "+min.getCurrent_Node().getNumberOfSuccessors());
        	
        	int successor_Nodes = 0;
        	
        	int nbsuccessors = min.Current_Node.getNumberOfSuccessors();
        	List<Arc> successors = min.Current_Node.getSuccessors(); //All successor Arcs to the current minCost Node
        	
        	for(int i=0; i<nbsuccessors; i++) { //We look through all successors
        		if (!data.isAllowed(successors.get(i))) { //Proper package implementation
                    continue;
                }
        		int index_suiv = successors.get(i).getDestination().getId();
        		if(!labels.get(index_suiv).Mark) { //If a Label isn't marked, we update its Cost
        			
        			successor_Nodes++;
        			
        			double oldCost = labels.get(index_suiv).Cost;
        			double newCost = min.getCost() + data.getCost(successors.get(i));
        			
        			if (Double.isInfinite(oldCost) && Double.isFinite(newCost)) {
                        notifyNodeReached(successors.get(i).getDestination());
                    } //Checking if the Node has already been reached; if not, we send a notification
        			
        			if(oldCost > newCost) { //If the newCost is better, we update the Label's Cost
        				labels.get(index_suiv).Cost = newCost;
        				labels.get(index_suiv).Father = successors.get(i); //And its Father Node
        				if (Double.isFinite(oldCost)){
        					tas.remove(labels.get(index_suiv));
        				} //We use the remove method from BinaryHeap to get rid of the old Label
        				tas.insert(labels.get(index_suiv)); //And we insert the new Label with the updated Cost
        			}
        		}
        	}
			System.out.println("Number of non marked successors: "+successor_Nodes);
        }
        
        if(!labels.get(index_dest).Mark){ //Proper package implementation
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else{
            notifyDestinationReached(data.getDestination()); //Proper package implementation

            ArrayList<Arc> end_path = new ArrayList<>();
            Arc arc = labels.get(index_dest).Father;
            while (arc != null) {
                end_path.add(arc); //Add it to the final solution
                arc = labels.get(arc.getOrigin().getId()).Father; //Moving back along the route we made
            }
            
            System.out.println("Nombre d'arcs dans le plus court chemin : " + end_path.size());
            
            Collections.reverse(end_path); //Reverse it to get proper Path (seen in Bellman-Ford)
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, end_path)); //End solution
            
           
        System.out.println("Longueur chemin Path : " + solution.getPath().getLength() + ", Dijkstra : " + labels.get(index_dest).getCost());
        System.out.println("Durée chemin Path : " + solution.getPath().getMinimumTravelTime() + ", Dijkstra : " + labels.get(index_dest).getCost());
        }
        
        return solution;
    }
}
