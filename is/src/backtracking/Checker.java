package backtracking;

import java.util.*;

public class  Checker {
	private void Cheker() {}
	public static boolean controllaPos(int i,int j,int val,int[][] kk) {
		if(i>9 && j>9) throw new IndexOutOfBoundsException();
		return controllaRiga(i,val,kk) && controllaColonna(j,val,kk);
	}
	private static boolean controllaRiga(int i,int val,int[][] kk) {
		for(int j=0;j<kk[i].length;j++)
			if(kk[i][j]==val)
				return false;
		return true;
	}
	private static boolean controllaColonna(int j,int val,int[][] kk) {
		for(int i=0;i<kk.length;i++)
			if(kk[i][j]==val)
				return false;
		return true;
	}
	public static boolean controllaPos(int i,int j,int val,List<List<Integer>> kk) {
		if(i>9 && j>9) throw new IndexOutOfBoundsException();
		return controllaRiga(i,val,kk) && controllaColonna(j,val,kk);
	}
	private static boolean controllaRiga(int i,int val,List<List<Integer>> kk) {
		List<Integer> l=kk.get(i);
		for(int j=0;j<l.size();j++)
			if(l.get(j)==val)
				return false;
		return true;
	}
	private static boolean controllaColonna(int j,int val,List<List<Integer>> kk) {
		for(int i=0;i<kk.size();i++)
			if(kk.get(i).get(j)==val)
				return false;
		return true;
	}
	public static Boolean controllaTuttiCage(List<Cage> cages,KenKen kk) {
		for(Cage cg:cages) {
			//if(!cageOccupata(cg,kk))
				return false;
		}
		return true;
	}
	public static Boolean cageOccupata(Cage cg,KenKen kk,int curr) {
		List<Integer> res=new ArrayList<>();
		res.add(curr);
		boolean zero=false;
		for(Index c:cg.getCage()) {
			int i=c.getI();
			int j=c.getJ();
			int val=kk.getValue(i, j);
			if(val!=0) res.add(val);
		}
		if(res.size()==0) return true;
		if(res.size()!=cg.size()) zero=true;
		//System.out.println("LIST:"+res.size()+res+" CAGE:"+cg.size());
		return controllaCage(res,cg.getOp(),cg.getResult(),zero);
	}
	private static Boolean controllaCage(List<Integer> nums,Operation op,int result,boolean zero) {
		switch(op) {
		case Operation.SUM:{
			//System.out.println("SUM");
			int ret=0;
			for(Integer val:nums) {
				ret+=val;
			}
			//System.out.println("ret:"+ret +" result:"+result+" ret<=result:"+(ret<=result));
			if(zero)
				return ret<result;
			else{
				//System.out.println("Cage Piena");
				return ret==result;
				}
			}
		
		case Operation.MUL:{
			//System.out.println("MUL");
			int ret=1;
			for(Integer val:nums) {
				ret*=val;
			}
			if(zero)
				return ret<=result;
			else{
				//System.out.println("Cage Piena");
				return ret==result;
				}
			}
		
		case Operation.SUB:{
			//System.out.println("SUB");
			if(zero) { //System.out.println("Cage non Piena");
				return true;}
			Collections.sort(nums,Collections.reverseOrder());
			int ret=nums.get(0);
			for(int i=1;i<nums.size();i++) {
				int val=nums.get(i);
				ret-=val;
			}
			//System.out.println("ret:"+ret +" result:"+result+" ret==result:"+(ret==result));
			
			return ret==result;
			}
		
		case Operation.DIV:{
			//System.out.println("DIV");
			if(zero) {//System.out.println("Cage non Piena");
				return true;}
			Collections.sort(nums,Collections.reverseOrder());
			int ret=nums.get(0);
			for(int i=1;i<nums.size();i++) {
				int val=nums.get(i);
				ret/=val;
			}
			//System.out.println("ret:"+ret +" result:"+result+" ret==result:"+(ret==result));
			return ret==result;
			}
		
		default:return false;
		}
	}

	public static Cage trovaCage(int i,int j,List<Cage> cages) {
		for(Cage cg:cages)
			if(cg.getCage().contains(new Index(i,j)))
				return cg;
		throw new RuntimeException("Cage non trovato");
	}

}
