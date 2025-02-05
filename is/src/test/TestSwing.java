package test;

import backtracking.*;
import plotter.KenKenSwingPlotter;

public class TestSwing {
	public static KenKen test1() {
		KenKenBuilder kkb=new KenKenBuilder(3);
		
		kkb=kkb.cage(0,1,Operation.SUM,0,3)
				.cage(2,Operation.DIV,0,1,3)
				.cage(0, 2, Operation.MUL,2,6)
				.cage(0,1,Operation.SUB,1,1);
		KenKenImp kk=(KenKenImp)kkb.build();
		System.out.println(kk);
		return kk;
	}
	public static KenKen test2() {
		KenKenBuilder kkb=new KenKenBuilder(6);
		
		kkb=kkb.cage(0, 1, Operation.SUM, 0, 11)
				.cage(3,4, Operation.MUL, 2, 6)
				.cage(0, 1, Operation.MUL, 3, 20)
				.cage(1, 2, Operation.DIV, 4, 3)
				.cage(4, 5, Operation.SUM, 5, 9)
				
				.cage(0, Operation.DIV, 1, 2, 2)
				.cage(1, Operation.SUB, 1, 2, 3)
				.cage(2, Operation.MUL, 2, 3, 6)
				.cage(3, Operation.MUL, 4, 5, 30)
				.cage(4, Operation.MUL, 0, 1, 6)
				.cage(5, Operation.SUM, 0, 2, 8)
				.cage(5, Operation.DIV, 3, 4, 2)
				
				.cage(2, 3, Operation.MUL, 0, 1, 240)
				
				.cage(0, 2, 5, Operation.MUL, 0, 4, 5, 6)
				.cage(3, 4, 3, Operation.SUM, 4, 3, 4, 7);
		KenKenImp kk=(KenKenImp)kkb.build();
		System.out.println(kk);
		return kk;
	}
	public static KenKen test3() {
		KenKenBuilder kkb=new KenKenBuilder(4);
		
		kkb=kkb.cage(0, Operation.SUB, 0, 1, 3)
				.cage(1, Operation.SUM, 0, 1, 7)
				.cage(2, Operation.SUB, 2, 3, 1)
				.cage(3, Operation.MUL, 1, 3, 12)
				
				.cage(0, 1, Operation.SUB, 2, 1)
				.cage(0, 1, Operation.DIV, 3, 2)
				
				.cage(2, 3, 0, Operation.SUM, 2, 0, 1, 5);
		KenKenImp kk=(KenKenImp)kkb.build();
		System.out.println(kk);
		return kk;
	}
	public static void main(String[] args) {
		
		KenKenSwingPlotter kks=new KenKenSwingPlotter(test3());
		kks.plot();
		
	}
}
