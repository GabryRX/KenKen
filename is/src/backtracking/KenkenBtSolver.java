package backtracking;

import java.util.*;


public class KenkenBtSolver extends Backtracking<Index,Integer> implements Solver {
	private KenKen kt;
	private List<KenKen> sols;
	private static boolean trovato=false;
	public KenkenBtSolver(KenKenImp kt) {
		sols=new ArrayList<>();
		this.kt=kt;
	}
	public KenkenBtSolver(KenKen kt) {
		sols=new ArrayList<>();
		this.kt=(KenKenImp)kt;
	}
	
	@Override
	public String toString() {
		return kt.toString();
	}
	@Override
	protected boolean assegnabile(Index p, Integer s) {
		int i=p.getI();
		int j=p.getJ();
		//System.out.println("Index:"+p+" Val: "+s);
		
		if(  kt.checkPos(i,j,s) && kt.checkCage(i,j,s)) {//
				return true;
			}
		
		return false;
	}
	@Override
	protected void assegna(Index ps, Integer s) {
		kt.set(ps.getI(), ps.getJ(), s);
		/*System.out.println("Assegnato");
		System.out.println(toString());*/
	}
	@Override
	protected void deassegna(Index ps, Integer s) {
		kt.set(ps.getI(), ps.getJ(), 0);
		//System.out.println("Deassegnato");
	}
	@Override
	protected void scriviSoluzione() {
		System.out.println("Soluzione:");
		System.out.println(toString());
		sols.add(new KenKenImp((KenKenImp)kt));
		
	}
	@Override
	protected boolean esisteSoluzione() {
		trovato=kt.size()*kt.size()==kt.getCnt();
		return trovato;
	}
	@Override
	protected boolean ultimaSoluzione() {
		return trovato;
	}
	
	@Override
	protected Index prossimoPuntoDiScelta(Index p) {
		int i=p.getI();
		int j=p.getJ();
		int s1=kt.size()-1;
		if(j==s1) {
			i++;
			j=0;
		}else
			j++;
		while(kt.occupato(i,j))
			if(j==s1) {
				i++;
				j=0;
			}else
				j++;
		//System.out.println("Prossimo punto di scelta:"+new Index(i,j));
		return new Index(i,j);
	}
	@Override
	protected boolean esisteProssimoPuntoDiScelta(Index p) {
		int i=p.getI();
		int j=p.getJ();
		int s1=kt.size()-1;
		//System.out.println("Esiste prossimo punto di scelta:"+p);
		if(i==s1 && j==s1)
				return false;
		return true;
	}
	@Override
	protected Collection<Integer> scelte(Index p) {
		int s1=kt.size()-1;
		ArrayList<Integer> ret=new ArrayList<>();
		if (p.getI()>s1 && p.getJ()>s1) return ret;
		for(int i=1;i<kt.size()+1;i++)
			ret.add(i);
		return ret;
	}
	public final void risolvi() {//Utilizzabile anche fuori dal package
		this.sols.clear();
		int i=0;
		int j=0;
		Index in=new Index(0,0);
		while(kt.occupato(i,j)) {
			int s1=kt.size()-1;
			if(j==s1) {
				i++;
				j=0;
			}else
				j++;
			in=new Index(i,j);
			}
		this.risolvi(in);
	}
	public List<KenKen> getSolution() {
		return sols;
	}
}
