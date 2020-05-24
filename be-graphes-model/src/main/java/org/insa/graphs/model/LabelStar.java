package org.insa.graphs.model;

/**
 * <p>
 * 	Pretty much the same class as Label, but this time the Cost
 * 		attribute represents the OG cost + the cost towards the
 * 		expected destination
 * </p>
 *
 */

public class LabelStar extends Label{
	
	public double DistanceToDestination; //The destination node we need to calculate the totalcost
	
	public LabelStar(Node nod, Node dest){
		super(nod);
		this.Mark = false;
		this.Cost = Double.POSITIVE_INFINITY;
		this.Father = null;
		this.DistanceToDestination = Current_Node.getPoint().distanceTo(dest.getPoint());
	}
	
	public LabelStar(Node nod, Node dest, boolean mar, double co, Arc fa){
		super(nod,mar,co,fa);
		this.DistanceToDestination = Current_Node.getPoint().distanceTo(dest.getPoint());
	}
	
	public double getTotalCost(){
		return this.Cost+this.DistanceToDestination;
	}
}