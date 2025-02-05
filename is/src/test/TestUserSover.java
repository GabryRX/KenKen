package test;

import backtracking.*;

public class TestUserSover {
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
	public static void main(String[] args) {
		KenKenUserSolver kkus=new KenKenUserSolver(test1());
		kkus.risolvi();
	}
}
