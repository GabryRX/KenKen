package backtracking;

import java.util.*;

public class KenKenBuilder {
	private KenKen kk;
	public KenKenBuilder(int size) {
		kk=new KenKenImp(size);
	}
	public KenKenBuilder cage(int i1,int i2,Operation op,int j,int result) {
		Cage cg=kk.createCage(i1,i2,op,j,result);
		kk.setCage(cg);
		return this;
	}
	public KenKenBuilder cage(int i,Operation op,int j1,int j2,int result) {
		Cage cg=kk.createCage(i,op,j1,j2,result);
		kk.setCage(cg);
		return this;
	}
	public KenKenBuilder cage(int i1,int i2,Operation op,int j1,int j2,int result) {
		Cage cg=kk.createCage(i1, i2, op,j1, j2,result);
		kk.setCage(cg);
		return this;
	}
	public KenKenBuilder cage(int i1, int i2, int j, Operation op, int i, int j1, int j2, int result) {
		Cage cg=kk.createCage(i1, i2, j, op, i, j1, j2, result);
		kk.setCage(cg);
		return this;
	}
	public KenKenBuilder cage(Cage cg) {
		kk.setCage(cg);
		return this;
	}
	public KenKenBuilder value(int i,int j,int k) {
		kk.setValue(i,j,k);
		return this;
	}
	public KenKen build() {
		return kk;
	}
	@Override
	public String toString() {
		return kk.toString();
	}
}
