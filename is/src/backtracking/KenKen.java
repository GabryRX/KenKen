package backtracking;

import java.io.*;
import java.util.*;

public interface KenKen extends Serializable{
	public int getCnt();
	public Integer getValue(int i,int j);
	public int size();
	public List<Cage> getCages();
	public void reset();
	public default void setValue(int i,int j,int v){
		set(i,j,v);
	}
	public void set(int i,int j,int v);
	public void setCage(Cage cg);
	//Creation Cage
	public Cage createCage(Set<Index> cage,Operation op,int result);
	public Cage createCage(int i1,int i2,Operation op,int j,int result);
	public Cage createCage(int i,Operation op,int j1,int j2,int result);
	public Cage createCage(int i1,int i2,Operation op,int result,int j1,int j2);
	public Cage createCage(int i1,int i2,int j,Operation op,int i,int j1,int j2,int result);
	//Checker
	public boolean checkPos(int i,int j,int val);
	public boolean checkCage(Cage cg,int curr);
	public boolean checkCage(int i,int j,int curr);
	public boolean checkCage(int i,int j);
	public boolean occupato(int i,int j);
}
