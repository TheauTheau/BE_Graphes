package org.insa.graphs.model;

/**
 * <p>
 * 	Class representing a label (each label is associated to a Node).
 * 		 Labels are used to determine a Node's MinCost.
 * </p>
 *
 */
public class Label implements Comparable<Label>{
	
	public Node Current_Node; //Labeled Node
	
	public boolean Mark; //True when MinCost of current node is known
	
	public double Cost; //Lowest cost from the origin to the top
	
	public Arc Father; //The father Node is at the end of the Arc
	
	public Label(Node nod){
		this.Current_Node = nod;
		this.Mark = false;
		this.Cost = Double.POSITIVE_INFINITY;
		this.Father = null;
	}
	
	public Label(Node nod, boolean mar, double co, Arc fa){
		this.Current_Node = nod;
		this.Mark = mar;
		this.Cost = co;
		this.Father = fa;
	}
	
	public Node getCurrent_Node() {
		return this.Current_Node;
	}
	
	public void Mark() {
		this.Mark = true;
	}
	
	public boolean isMarked() {
		if(this.Mark) {
			return true;
		}else{
			return false;
		}
	}
	
	public double getCost() {
		return this.Cost;
	}
	
	public void setCost(double co) {
		this.Cost = co;
	}
	
	public double getTotalCost() {
		return this.Cost;
	}
	
	public int compareTo(Label other) {
		return Double.compare(this.getTotalCost(),other.getTotalCost());
	}
		
}