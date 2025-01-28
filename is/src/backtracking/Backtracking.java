package backtracking;
import java.util.Collection;

public abstract class Backtracking<P,S> {
	
	protected abstract boolean assegnabile( P p, S s );
	protected abstract void assegna( P ps, S s );
	protected abstract void deassegna( P ps, S s );
	
	protected abstract void scriviSoluzione();
	protected abstract boolean esisteSoluzione();//esisteSoluzione
	
	protected abstract P prossimoPuntoDiScelta( P p );//prossimoPuntoDiScelta
	protected abstract boolean esisteProssimoPuntoDiScelta( P p );//prossimoPuntoDiScelta
	

	protected abstract Collection<S> scelte( P p );
	
	protected boolean ultimaSoluzione() {
		return false; //cerca tutte le possibili soluzioni
	}//ultimaSoluzione
	
	//private List<P> ps;
	//private P primoPuntoDiScelta() {
	//	return ps.get(0);
	//}//primoPuntoDiScelta
	//factory
	//protected abstract List<P> puntiDiScelta();
	
	protected final void tentativo( P p ) {
		Collection<S> sa=scelte(p); //scelte ammissibili per p
		for( S s: sa) {
			if( assegnabile(p,s) ) {
				assegna(p,s);
				if( esisteSoluzione() )
					scriviSoluzione();
				if( ultimaSoluzione() ) 
					break;//in questo modo blocco l'intero backtracking
				if (esisteProssimoPuntoDiScelta(p)) {
					P prossimo = prossimoPuntoDiScelta(p);
					tentativo( prossimo );
				}
				deassegna(p,s);
			}
		}
	}//tentativo
	
	public final void risolvi(P p) {
		//ps=puntiDiScelta();
		tentativo( p );
	}//risolvi
	
}//Backtracking
