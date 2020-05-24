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
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        List<Node> nodes = graph.getNodes();
        final int nbNodes = graph.size();
        
        int index_origine = data.getOrigin().getId();
        int index_dest = data.getDestination().getId();
        notifyOriginProcessed(data.getOrigin());
        
        //Initiation
        ArrayList<Label> labels = new ArrayList<Label>();
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        for(int i=0; i<nbNodes; i++) {
        	labels.add(new Label(nodes.get(i)));
        }
        labels.get(index_origine).Cost = 0;
        tas.insert(labels.get(index_origine));
        
        //Iterations
        while(!labels.get(index_dest).Mark && tas.size() != 0) {
        	Label min = tas.deleteMin();
        	labels.get(min.Current_Node.getId()).Mark = true;
        	
        	//System.out.println("Coût du label marqué : " + min.getCost());
        	//System.out.println("Taille du tas : " + tas.size());
        	
        	int nbsuccessors = min.Current_Node.getNumberOfSuccessors();
        	List<Arc> successors = min.Current_Node.getSuccessors();
        	
        	for(int i=0; i<nbsuccessors; i++) {
        		if (!data.isAllowed(successors.get(i))) {
                    continue;
                }
        		
        		int index_suiv = successors.get(i).getDestination().getId();
        		if(!labels.get(index_suiv).Mark) {
        			double oldDistance = labels.get(index_suiv).Cost;
        			double newDistance = min.getCost() + data.getCost(successors.get(i));
        			
        			if (Double.isInfinite(oldDistance) && Double.isFinite(newDistance)) {
                        notifyNodeReached(successors.get(i).getDestination());
                    }
        			
        			if(oldDistance > newDistance) {
        				labels.get(index_suiv).Cost = newDistance;
        				labels.get(index_suiv).Father = successors.get(i);
        				if (Double.isFinite(oldDistance)){
        					tas.remove(labels.get(index_suiv));
        				}
        				tas.insert(labels.get(index_suiv));
        			}
        		}
        	}
        }
        
        if (!labels.get(index_dest).Mark){
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }else{

            notifyDestinationReached(data.getDestination());

            ArrayList<Arc> chemin = new ArrayList<>();
            Arc arc = labels.get(index_dest).Father;
            while (arc != null) {
                chemin.add(arc);
                arc = labels.get(arc.getOrigin().getId()).Father;
            }
            
            System.out.println("Nombre d'arcs dans le plus court chemin : " + chemin.size());
            
            Collections.reverse(chemin);
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, chemin));
            
           
        System.out.println("Longueur chemin Path : " + solution.getPath().getLength() + ", Dijkstra : " + labels.get(index_dest).getCost());
        System.out.println("Durée chemin Path : " + solution.getPath().getMinimumTravelTime() + ", Dijkstra : " + labels.get(index_dest).getCost());
        }
        
        return solution;
    }
}
