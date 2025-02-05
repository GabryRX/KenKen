package test;

import io.*;
import backtracking.*;

public class TestSaver {
	public static void test1() {
		KenKenFile kkf=KenKenFile.getKKF();
		KenKenBuilder kkb=new KenKenBuilder(3);
		kkb=kkb.cage(0,1,Operation.SUM,0,3)
				.cage(2,Operation.DIV,0,1,3)
				.cage(0, 2, Operation.MUL,2,6)
				.cage(0,1,Operation.SUB,1,1);
		KenKenImp kk=(KenKenImp)kkb.build();
		kkf.save("kentest.ken",kk);
	}
	public static void test2() {
		KenKenFile kkf=KenKenFile.getKKF();
		
		KenKen k=kkf.load("kentest.ken");
		System.out.println(k);
	}
	public static void main(String[] args) {
		test1();
		test2();
	}
}
