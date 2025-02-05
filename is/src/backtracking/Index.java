package backtracking;

import java.io.*;

class Index implements Serializable{
	private static final long serialVersionUID = 156594752832762016L;
	private int i;
	private int j;
	public Index(int i,int j) {
		this.i=i;
		this.j=j;
	}
	public int getI() {
		return i;
	}
	public int getJ() {
		return j;
	}
	@Override
	public boolean equals(Object o) {
		if(o==this) return true;
		if(!(o instanceof Index)) return false;
		Index ind=(Index)o;
		return ind.i==i && ind.j==j;
	}
	@Override
	public String toString() {
		return "<"+i+","+j+">";
	}
	@Override
	public int hashCode() {
		int prime=31;
		if(i>=j)
			return prime*i+j;
		else
			return prime*j*i;
		
	}
}
