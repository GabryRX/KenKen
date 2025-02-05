package backtracking;

import java.util.*;

import plotter.*;

public class KenKenUserSolver implements Solver{
	KenKen kk;
	private static java.util.Scanner keyboard = new java.util.Scanner(System.in);
	public KenKenUserSolver(KenKen kk) {
		this.kk=kk;
	}
	public void insert(int i,int j,int val ) {
		kk.set(i, j, val);
	}
	public void remove(int i,int j) {
		kk.set(i, j, 0);
		
	}
	public boolean verifica(int i,int j,int val) {
		return kk.checkPos(i, j, val) && kk.checkCage(i, j);
	}
	public boolean verifica() {
		for(int i=0;i<kk.size();i++) {
			for(int j=0;j<kk.size();j++) {
				if(kk.getValue(i, j)!=0) 
					if(!verifica(i,j,kk.getValue(i, j))) {
						/*System.out.println("CheckCage:"+kk.checkCage(i, j));
						System.out.println("i:"+i+" j:"+j);
						System.out.println(kk.getValue(i, j));*/
						return false;
						}
			}
		}
		return true;
	}
	//TextSolver
	public void risolvi() {
		KenKenTextPlotter kktp=new KenKenTextPlotter((KenKenImp)kk,this);
		kktp.plot();
	}
}
