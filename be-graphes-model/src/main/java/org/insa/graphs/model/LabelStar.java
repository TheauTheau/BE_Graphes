package org.insa.graphs.model;

/**
 * <p>
 * 	Pretty much the same class as Label, but this time the Cost
 * 		attribute represents the OG cost + the cost towards the
 * 		expected destination
 * </p>
 *
 */

public class LabelStar extends Label implements Comparable<Label>{
	
	public Node Current_Node; //Labeled Node
	
	public boolean Mark; //True when MinCost of current node is known
	
	public double Cost; //It's different now, we'll implement it in A*
	
	public Arc Father; //The father Node is at the end of the Arc
	
	public Node Destination; //The destination node we need to calculate the totalcost
	
	public LabelStar(Node nod, Node dest){
		super(nod);
		this.Mark = false;
		this.Cost = Double.POSITIVE_INFINITY;
		this.Father = null;
		this.Destination = dest;
	}
	
	public double getTotalCost(){
		return this.Cost+this.Current_Node.getPoint().distanceTo(this.Destination.getPoint());
	}
}