package backtracking;

import java.util.*;

public class KenKenImp implements KenKen{
	private int[][] kk;
	private int cnt;
	private List<Index> occupati;
	private List<CageImp> cages;
	private int size;
	public KenKenImp(int size) {
		if(size<3 && size>6) throw new IllegalArgumentException();
		kk=new int[size][size];
		for(int i=0;i<kk.length;i++)
			for(int j=0;j<kk[0].length;j++)
				kk[i][j]=0;
		this.cnt=0;
		this.occupati=new ArrayList<>();
		this.cages=new ArrayList<>();
		this.size=size;
	}
	//Getter
	public int getCnt() {
		return cnt;
	}
	public Integer getValue(int i,int j) {
		return kk[i][j];
	}
	public int size() {
		return size;
	}
	public List<Index> getOccupati() {
		return occupati;
	}
	//Setter
	public void setValue(int i,int j,int v) {
		CageImp cg=(CageImp)Checker.trovaCage(i, j,new ArrayList<>(this.getCages()));
		if(v>0 && v<size && Checker.controllaPos(i,j,v,kk)) { //&& Checker.controllaCage(cg, this)
			kk[i][j]=v;
			occupati.add(new Index(i,j));
			cnt++;
			}
		else
			System.out.println("Non impostato "+i+"-"+j+" val:"+v);
	}
	public void set(int i,int j,int v) {
		kk[i][j]=v;
		if(v==0)
			cnt--;
		else 
			cnt++;
	}
	public void setCage(Cage cg) {
		this.cages.add((CageImp)cg);
	}
	//toString
	@Override
	public String toString() {
		StringBuilder ret=new StringBuilder(200);
		ret.append("[");
		for(int i=0;i< kk.length;i++)
			if(i==kk.length-1)
				ret.append(Arrays.toString(kk[i]));
			else
				ret.append(Arrays.toString(kk[i])+"\n");
		ret.append("]\n\n");
		ret.append(toCageString());
		return ret.toString();
	}
	private String toCageString() {
		StringBuilder ret=new StringBuilder(200);
		ret.append("[");
		String[][] cgs=new String[kk.length][kk.length];
		for(CageImp cg:cages) {
			Set<Index> st=cg.getCage();
			for(Index ix:st) {
				cgs[ix.getI()][ix.getJ()]=cg.toString();
			}
		}
		for(int i=0;i< cgs.length;i++)
			if(i==cgs.length-1)
				ret.append(Arrays.toString(cgs[i]));
			else
				ret.append(Arrays.toString(cgs[i])+"\n");
		ret.append("]");
		return ret.toString();
	}
	
	//Utility
	
	public List<CageImp> getCages() {
		return cages;
	}
	
	public boolean checkPos(int i, int j, int val) {
		return Checker.controllaPos(i, j, val, kk);
	}
	public boolean checkCage(Cage cg,int curr) {
		return Checker.cageOccupata(cg, this,curr);
	}
	public boolean checkCage(int i, int j,int curr) {
		Cage cg=Checker.trovaCage(i, j,new ArrayList<>(this.getCages()));
		return Checker.cageOccupata(cg, this,curr);
	}
	@Override
	public boolean checkAllCage() {
		List<Cage> cageInt=new ArrayList<Cage>(cages);
		return Checker.controllaTuttiCage(cageInt, this);
	}
	public boolean occupato(int i,int j) {
		return occupati.contains(new Index(i,j));
	}
	
	public class CageImp implements Cage {
		private Set<Index> cage;
		private Operation op;
		private int result;
		@SuppressWarnings("unused")
		private CageImp(){}
		public CageImp(Set<Index> cage,Operation op,int result){
			if(!correctCage(cage)) throw new IllegalArgumentException("Elementi non vicini");
			this.cage=cage;
			this.op=op;
			this.result=result;
		}
		//Vertical
		public CageImp(int i1, int i2, Operation op,int j, int result) {
			this.cage=new HashSet<>();
			for(int i=i1;i<=i2;i++) {
				this.cage.add(new Index(i,j));
			}
			this.op=op;
			this.result=result;
		}
		//Horizontal
		public CageImp(int i, Operation op, int j1, int j2, int result) {
			this.cage=new HashSet<>();
			for(int j=j1;j<=j2;j++) {
				this.cage.add(new Index(i,j));
			}
			this.op=op;
			this.result=result;
		}
		//L-Form
		public CageImp(int i1,int i2,int j,Operation op,int i,int j1, int j2,int result) {
			this.cage=new HashSet<>();
			for(int k=i1;k<=i2;k++) {
				this.cage.add(new Index(k,j));
			}//Vertical
			for(int k=j1;k<=j2;k++) {
				this.cage.add(new Index(i,k));
			}//Horizontal
			this.op=op;
			this.result=result;
		}
		//Cube
		public CageImp(int i1,int i2, Operation op, int j1, int j2, int result) {
			this.cage=new HashSet<>();
			for(int i=i1;i<=i2;i++) {
				for(int j=j1;j<=j2;j++) {
					this.cage.add(new Index(i,j));
					//System.out.println("i:"+i+" j:"+j+" result:"+result+op);
				}
			}
			this.op=op;
			this.result=result;
		}
		
		public Set<Index> getCage() {
			return new HashSet<>(cage);
		}
		public Operation getOp() {
			return op;
		}
		public int getResult() {
			return result;
		}
		public String toString() {
			return result+""+this.op;
		}
		public int size() {
			return cage.size();
		}
		private boolean correctCage(Set<Index> cage) {
			List<Index> cg=new ArrayList<>(cage);
			HashSet<Index> righe=new HashSet<>();
			HashSet<Index> colonne=new HashSet<>();
			for(int i=0;i<cg.size()-1;i++) {
				if(viciniRiga(cg.get(i).getJ(),cg.get(i+1).getJ())){
					righe.add(cg.get(i));
					righe.add(cg.get(i+1));
					} 
				if(viciniColonna(cg.get(i).getI(),cg.get(i+1).getI())) {
						colonne.add(cg.get(i));
						colonne.add(cg.get(i+1));
					}
			}
			righe.addAll(colonne);
			//Result
			HashSet<Index> ret=righe;
			return ret.size()==cage.size();
		}
		
		private boolean viciniColonna(int i1,int i2) {
			return i1==i2;
		}
		private boolean viciniRiga(int j1,int j2) {
			return j1==j2;
		}
		
	}//CageImp

	public CageImp createCage(Set<Index> cage, Operation op, int result) {
		return new CageImp(cage,op,result);
	}
	@Override
	public Cage createCage(int i1, int i2, Operation op,int j,int result) {
		return new CageImp(i1,i2,op,j,result);
	}
	@Override
	public Cage createCage(int i, Operation op,  int j1, int j2, int result) {
		return new CageImp(i,op,j1,j2,result);
	}
	@Override
	public Cage createCage(int i1, int i2, Operation op,int j1, int j2,int result) {
		return new CageImp(i1,i2,op,j1,j2,result);
	}
	@Override
	public Cage createCage(int i1, int i2, int j, Operation op, int i, int j1, int j2, int result) {
		return new CageImp(i1,i2,j,op,i,j1,j2,result);
	}
	
	


	
}
