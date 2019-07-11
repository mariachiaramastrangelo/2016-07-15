package it.polito.tdp.flight.model;



public class Rotte {
	private int aId1;
	private int aId2;
	private double distanza;
	public Rotte(int aId1, int aId2, double distanza) {
		super();
		this.aId1 = aId1;
		this.aId2 = aId2;
		this.distanza = distanza;
	}
	public int getaId1() {
		return aId1;
	}
	public void setaId1(int aId1) {
		this.aId1 = aId1;
	}
	public int getaId2() {
		return aId2;
	}
	public void setaId2(int aId2) {
		this.aId2 = aId2;
	}
	public double getDistanza() {
		return distanza;
	}
	public void setDistanza(double distanza) {
		this.distanza = distanza;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aId1;
		result = prime * result + aId2;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rotte other = (Rotte) obj;
		if (aId1 != other.aId1)
			return false;
		if (aId2 != other.aId2)
			return false;
		return true;
	}
	
	
}
