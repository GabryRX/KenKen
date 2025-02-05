package plotter;

import backtracking.*;

public class KenKenTextPlotter implements KenKenPlotter{
	private static java.util.Scanner keyboard = new java.util.Scanner(System.in);
	KenKenImp kk;
	KenKenUserSolver kkus;
	public KenKenTextPlotter(KenKenImp kk,KenKenUserSolver kkus) {
		this.kk=kk;
		this.kkus=kkus;
	}
	private int[] plotIndex() {
		
		System.out.println("Inserisci una coppia di indici i j seguiti da invio");
		int i=keyboard.nextInt();
		int j=keyboard.nextInt();
		int[] ind=new int[] {i,j};
		return ind;
	}
	private int plotValue() {
		System.out.println("Inserisci un valore da inserire");
		int val=keyboard.nextInt();
		return val;
	}
	public int[] plotData() {
		int[] ind=plotIndex();
		int i=ind[0];
		int j=ind[1];
		while (i>kk.size() && i<0 && j>kk.size() && j<0) {
			System.out.println("Devi inserire tra 0 e la dimensione del KenKen");
			ind=plotIndex();
			i=ind[0];
			j=ind[1];
		}
		int val=plotValue();
		while(val>kk.size()) {
			System.out.println("Devi inserire un numero inferiore alla dimensione del KenKen");
			val=plotValue();
		}
		int[] ret=new int[] {i,j,val};
		return ret;
	}
	public void plotSolution() {
		System.out.println("Soluzione:");
		print();
	}
	public void plot() {
		boolean scelta=plotSceltaVerifica();
		while(kkus.verifica()) {
			int[] data=plotData();
			int i=data[0];
			int j=data[1];
			int val=data[2];
			if(val==0) kkus.remove(i,j);
			else kkus.insert(i,j,val);
			if(scelta) print();
		}
	}
	private void print() {
		System.out.println(kk.toString());
	}
	public boolean plotSceltaVerifica() {
		while(true) {
		System.out.println("Vuoi abilitare il controllo della verifica?S/n");
		String risposta=keyboard.nextLine();
		if(risposta.equalsIgnoreCase("S"))
			return true;
		else if(risposta.equalsIgnoreCase("n")) {
			return false;
			}
		}
		
	}
}
