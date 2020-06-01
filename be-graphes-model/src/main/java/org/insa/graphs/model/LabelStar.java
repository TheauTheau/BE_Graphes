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
	
	public double DistanceToDestination; // The extra distance that makes the LabelStar different
	
	public LabelStar(Node nod, Node dest){ // We implement LabelStar with a destination Node
		super(nod);
		this.Mark = false;
		this.Cost = Double.POSITIVE_INFINITY;
		this.Father = null;
		this.DistanceToDestination = Current_Node.getPoint().distanceTo(dest.getPoint());
	} // So we can implement the distanceTo method with the destination as an argument
	
	public LabelStar(Node nod, Node dest, boolean mar, double co, Arc fa){
		super(nod,mar,co,fa);
		this.DistanceToDestination = Current_Node.getPoint().distanceTo(dest.getPoint());
	}
	
	public double getTotalCost(){ // Pretty basic concept, this time the cost takes another input
		return this.Cost+this.DistanceToDestination;
	}
}